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

package com.pozirk.payment.android
{
	import flash.events.Event;
	
	public class InAppPurchaseEvent extends Event
	{
		public static const PURCHASE_SUCCESS:String = "PURCHASE_SUCCESS";
 	 	public static const PURCHASE_ERROR:String = "PURCHASE_ERROR";
		public static const PURCHASE_ALREADY_OWNED:String = "PURCHASE_ALREADY_OWNED";
 	 	//public static const PURCHASE_REFUNDED:String = "PURCHASE_REFUNDED";
 	 	//public static const PURCHASE_USER_CANCELLED:String = "PURCHASE_USER_CANCELLED";
 	 	public static const INIT_ERROR:String = "INIT_ERROR";	//dispatched if the billing service will not work on this device
 	 	public static const INIT_SUCCESS:String = "INIT_SUCCESS";	//dispatched when the billing service is ready to use
 	 	public static const RESTORE_ERROR:String = "RESTORE_ERROR";
 	 	public static const RESTORE_SUCCESS:String = "RESTORE_SUCCESS";	//dispatched when transaction was successfully restored, so you can get purchase details
		public static const CONSUME_ERROR:String = "CONSUME_ERROR"; //dispatched when consumption fails
 	 	public static const CONSUME_SUCCESS:String = "CONSUME_SUCCESS";
		
		public var data:String;
		
		public function InAppPurchaseEvent(type:String, data:String = null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			this.data = data;
			super(type, bubbles, cancelable);
		}
	}
}