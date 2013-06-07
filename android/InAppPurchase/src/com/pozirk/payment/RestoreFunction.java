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

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREArray;

public class RestoreFunction implements FREFunction
{
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1)
	{
		try
		{
			FREArray freItems = (FREArray)arg1[0];
			FREArray freSubs = (FREArray)arg1[1];
			
			Billing billing = Billing.getInstance();
			
			List<String> items = null;
			List<String> subs = null;
			FREObject freObj;
			long len, i;
			
			if(freItems != null && freItems.getLength() > 0)
			{
				items = new ArrayList<String>();
				len = freItems.getLength();
				for(i = 0; i < len; i++)
				{
					freObj = freItems.getObjectAt(i);
					if(freObj != null)
						items.add(freObj.getAsString());
				}
			}
			
			if(freSubs != null && freSubs.getLength() > 0)
			{
				subs = new ArrayList<String>();
				len = freSubs.getLength();
				for(i = 0; i < len; i++)
				{
					freObj = freSubs.getObjectAt(i);
					if(freObj != null)
						items.add(freObj.getAsString());
				}
			}
			
			billing.restore(items, subs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		
		return null;
	}
}