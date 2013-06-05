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


import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.android.payment.utils.Purchase;

public class GetPurchaseDetailsFunction implements FREFunction
{
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1)
	{	
		try
		{
			FREObject sku = arg1[0];
			FREObject res = FREObject.newObject("com.pozirk.payment.android.InAppPurchaseDetails", null);
			
			Purchase purchase = Billing.getInstance().getPurchaseDetails(sku.getAsString());
			
			if(purchase != null)
			{
				res.setProperty("_type", FREObject.newObject(purchase.getItemType()));
				res.setProperty("_orderId", FREObject.newObject(purchase.getOrderId()));
				res.setProperty("_packageName", FREObject.newObject(purchase.getPackageName()));
				res.setProperty("_sku", FREObject.newObject(purchase.getSku()));
				res.setProperty("_time", FREObject.newObject(purchase.getPurchaseTime()));
				res.setProperty("_purchaseState", FREObject.newObject(purchase.getPurchaseState()));
				res.setProperty("_payload", FREObject.newObject(purchase.getDeveloperPayload()));
				res.setProperty("_token", FREObject.newObject(purchase.getToken()));
				res.setProperty("_json", FREObject.newObject(purchase.getOriginalJson()));
				res.setProperty("_signature", FREObject.newObject(purchase.getSignature()));
				
				return res;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
