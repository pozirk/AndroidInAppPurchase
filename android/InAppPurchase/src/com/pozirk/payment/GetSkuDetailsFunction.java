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
import com.android.payment.utils.SkuDetails;

public class GetSkuDetailsFunction implements FREFunction
{
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1)
	{	
		try
		{
			FREObject sku = arg1[0];
			FREObject res = FREObject.newObject("com.pozirk.payment.android.InAppSkuDetails", null);
			
			SkuDetails skuDetails = Billing.getInstance().getSKuDetails(sku.getAsString());
			
			if(skuDetails != null)
			{
				res.setProperty("_sku", FREObject.newObject(skuDetails.getSku()));
				res.setProperty("_type", FREObject.newObject(skuDetails.getType()));
				res.setProperty("_price", FREObject.newObject(skuDetails.getPrice()));
				res.setProperty("_title", FREObject.newObject(skuDetails.getTitle()));
				res.setProperty("_descr", FREObject.newObject(skuDetails.getDescription()));
				
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
