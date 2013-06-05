/* Copyright (c) 2013 Pozirk Games
 * http://www.pozirk.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pozirk.payment;

import java.util.List;

import com.android.payment.utils.IabHelper;
import com.android.payment.utils.IabResult;
import com.android.payment.utils.Inventory;
import com.android.payment.utils.Purchase;
import com.android.payment.utils.SkuDetails;

import android.app.Activity;
import android.content.Intent;

public class Billing
{
	static final int RC_REQUEST = 20003; //[update: what this for?]
	
	protected static Billing _instance = null;

	IabHelper _helper;
	ExtensionContext _ctx;
	String _sku, _type, _payload;
	Activity _act;
	Inventory _inventory;
	
	public void schedulePurchase(String sku, String type, String payload)
	{
		_sku = sku;
		_type = type;
		_payload = payload;
	}
	
	public void purchase(Activity act)
	{
		if(_helper.isAsyncInProgress() == false) //to prevent starting another async operation
		{
			_act = act;
			_helper.launchPurchaseFlow(act, _sku, _type, RC_REQUEST, _onPurchase, _payload);
		}
	}
	
	public Activity activity() {return _act;}
	
	public void endPurchase(Activity act)
	{
		if(act == _act) //I guess :), that Activities are created and destroyed asynchronously, so don't let to null wrong Activity
		{
			_act = null;
			_helper.flagEndAsync();
		}
	}
	
	public boolean handlePurchaseResult(int requestCode, int resultCode, Intent data)
	{
		return _helper.handleActivityResult(requestCode, resultCode, data);
	}
	
	IabHelper.OnIabPurchaseFinishedListener _onPurchase = new IabHelper.OnIabPurchaseFinishedListener()
	{
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) 
		{
			if(result.isFailure())
			{
				//process some specific errors differently
				if(result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED)
					_ctx.dispatchStatusEventAsync("PURCHASE_ALREADY_OWNED", _sku);
				/*else if(result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED)
					_ctx.dispatchStatusEventAsync("PURCHASE_USER_CANCELLED", _sku);*/
				else
					_ctx.dispatchStatusEventAsync("PURCHASE_ERROR", result.getMessage());
			}      
			else if(purchase.getSku().equals(_sku)) //[update: not good, need to fix that]
				_ctx.dispatchStatusEventAsync("PURCHASE_SUCCESS", purchase.getSku());
			else
				_ctx.dispatchStatusEventAsync("PURCHASE_ERROR", purchase.getSku());
			
			Billing.getInstance()._act.finish();
			_act = null;
		}
	};
	
	//now query both regular items and subscriptions
	public void restore(List<String> items, List<String> subs)
	{
		_helper.queryInventoryAsync(true, items, subs, _onRestore);
	}
	
	IabHelper.QueryInventoryFinishedListener _onRestore = new IabHelper.QueryInventoryFinishedListener()
	{
		public void onQueryInventoryFinished(IabResult result, Inventory inventory)
		{
			if (result.isFailure())
				_ctx.dispatchStatusEventAsync("RESTORE_ERROR", result.getMessage());
			else
			{
				Billing.getInstance()._inventory = inventory;
				_ctx.dispatchStatusEventAsync("RESTORE_SUCCESS", "ku-ku");
			}
		}
	};
	
	public void consume(String sku)
	{
		if(_inventory != null)
		{
			Purchase purchase = _inventory.getPurchase(sku);
			if(purchase != null)
				_helper.consumeAsync(purchase, _onConsumeFinished);
			else
				_ctx.dispatchStatusEventAsync("CONSUME_ERROR", "Purchase not found.");
		}
		else
			_ctx.dispatchStatusEventAsync("CONSUME_ERROR", "Can't consume a product, restore transactions first.");
	}
	
	IabHelper.OnConsumeFinishedListener _onConsumeFinished = new IabHelper.OnConsumeFinishedListener()
	{
		public void onConsumeFinished(Purchase purchase, IabResult result)
		{
			if(result.isSuccess())
				_ctx.dispatchStatusEventAsync("CONSUME_SUCCESS", purchase.getSku());
			else
				_ctx.dispatchStatusEventAsync("CONSUME_ERROR", result.getMessage());
		}
	};
		
	public Purchase getPurchaseDetails(String sku)
	{
		if(_inventory != null)
			return _inventory.getPurchase(sku);
		
		return null;
	}
	
	public SkuDetails getSKuDetails(String sku)
	{
		if(_inventory != null)
			return _inventory.getSkuDetails(sku);
		
		return null;
	}
	
	public static Billing getInstance()
	{
		if(_instance == null)
			_instance = new Billing();
			
		return _instance;
	}
	
	public void init(Activity act, ExtensionContext ctx, String base64EncodedPublicKey)
	{
		_ctx = ctx;
		_helper = new IabHelper(act, base64EncodedPublicKey);

		//_helper.enableDebugLogging(true);
		
		_helper.startSetup(new IabHelper.OnIabSetupFinishedListener()
		{
			public void onIabSetupFinished(IabResult result)
			{
				if(!result.isSuccess())
					_ctx.dispatchStatusEventAsync("INIT_ERROR", result.getMessage());
				else
					_ctx.dispatchStatusEventAsync("INIT_SUCCESS", "ku-ku");
			}
		});
	}
	
	public void dispose()
	{
		if(_helper != null)
			_helper.dispose();
		_helper = null;
	}
	
	protected Billing() {}
}