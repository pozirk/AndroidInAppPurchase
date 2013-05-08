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

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class PurchaseFunction implements FREFunction
{
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1)
	{	
		try
		{
			if(Billing.getInstance().activity() == null) //don't start another activity, if there is one already
			{
				FREObject sku = arg1[0];
				FREObject type = arg1[1];
				FREObject payload = arg1[2];
				
				if(sku == null || sku.getAsString().length() == 0)
					Billing.getInstance()._ctx.dispatchStatusEventAsync("PURCHASE_ERROR", "Invalid product id.");
				else if(type == null || type.getAsString().length() == 0)
					Billing.getInstance()._ctx.dispatchStatusEventAsync("PURCHASE_ERROR", "Invalid purchase type.");
				else //everything's ok
				{
					Billing.getInstance().schedulePurchase(sku.getAsString(), type.getAsString(), (payload == null ? null : payload.getAsString()));
				
					Intent intent = new Intent(arg0.getActivity(), BillingActivity.class);
					arg0.getActivity().startActivity(intent);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}