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
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;

	public class InAppPurchase extends EventDispatcher
	{
		public static const TYPE_INAPP:String = "inapp"; //regular in-app items
		public static const TYPE_SUBS:String = "subs"; //subscription
		
		protected var _ctx:ExtensionContext;
		
		public function InAppPurchase()
		{
			_ctx = ExtensionContext.createExtensionContext("com.pozirk.AndroidInAppPurchase", null);
			if(_ctx != null)
				_ctx.addEventListener(StatusEvent.STATUS, onStatus);
			else
				trace('_ctx is null.');
		}
		
		/**
		 * Initiate Android In-App Billing, wait for INIT_SUCCESS before doing any other actions.
		 * @param	base64EncodedPublicKey license key for you app
		 */
		public function init(base64EncodedPublicKey:String):void
		{
			_ctx.call("init", base64EncodedPublicKey);
		}
		
		/**
		 * Begin purchase process
		 * @param	sku product id
		 * @param	type type of purchase, regular item (TYPE_INAPP) or subscription (TYPE_SUBS)
		 * @param	payload extra data to save with the purchase
		 */
		public function purchase(sku:String, type:String, payload:String = null):void
		{
			_ctx.call("purchase", sku, type, payload);
		}
		
		/**
		 * Restore the list of previously made purchases
		 * @param	type type of purchase, regular item (TYPE_INAPP) or subscription (TYPE_SUBS)
		 */
		public function restore(type:String):void
		{
			_ctx.call("restore", type);
		}
		
		/**
		 * Get details about the purchased product, including payload
		 * @param	sku product id
		 * @return purchase details
		 */
		public function getDetails(sku:String):InAppPurchaseDetails
		{
			return _ctx.call("getDetails", sku) as InAppPurchaseDetails;
		}
		
		/**
		 * Consume previously purchase product
		 * @param	sku product id
		 */
		public function consume(sku:String):void
		{
			_ctx.call("consume", sku);
		}
		
		protected function onStatus(event:StatusEvent):void
		{
			var e:InAppPurchaseEvent = null;
			//trace(event.code+event.level);
			switch(event.code)
			{
				case "PURCHASE_SUCCESS":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.PURCHASE_SUCCESS, event.level);
					break;
				}
				
				case "PURCHASE_ERROR":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.PURCHASE_ERROR, event.level);
					break;
				}
				
				case "PURCHASE_ALREADY_OWNED":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.PURCHASE_ALREADY_OWNED, event.level);
					break;
				}
				
				/*case "PURCHASE_REFUNDED":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.PURCHASE_REFUNDED);
					break;
				}
				
				case "PURCHASE_USER_CANCELLED":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.PURCHASE_USER_CANCELLED);
					break;
				}*/
				
				case "RESTORE_SUCCESS":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.RESTORE_SUCCESS);
					break;
				}
				
				case "RESTORE_ERROR":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.RESTORE_ERROR, event.level);
					break;
				}
				
				case "CONSUME_SUCCESS":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.CONSUME_SUCCESS, event.level);
					break;
				}
				
				case "CONSUME_ERROR":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.CONSUME_ERROR, event.level);
					break;
				}
				
				case "INIT_SUCCESS":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.INIT_SUCCESS);
					break;
				}
				
				case "INIT_ERROR":
				{
					e = new InAppPurchaseEvent(InAppPurchaseEvent.INIT_ERROR, event.level);
					break;
				}
			}
			
			if(e != null)
				this.dispatchEvent(e);
		}
		
		public function dispose():void
		{
			_ctx.dispose();
		}
	}
}