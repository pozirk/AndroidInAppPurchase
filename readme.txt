# About
AndroidInAppPurchase is an Adobe AIR native extension (ANE) for Android to purchase virtual items.
It uses Google Play In-app Billing version 3 API.
Supported functionality:
- purchase of items;
- restoration of previously purchased items;
- consumption of items.

Subscriptions are not supported.


# Installation
Add "InAppPurchase.ane" and "as3\InAppPurchase\bin\InAppPurchase.swc" to your AIR project

Add the following lines to your AIR Aplication-app.xml file inside <manifestAdditions> section:
<uses-permission android:name="com.android.vending.BILLING" />
<application android:enabled="true">                
      <activity android:name="com.pozirk.payment.BillingActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" android:background="#30000000" />
 </application>


# Usage examples
import com.pozirk.payment.android.InAppPurchase;
import com.pozirk.payment.android.InAppPurchaseEvent;
import com.pozirk.payment.android.InAppPurchaseDetails;


//> initialization of InAppPurchase
_iap:InAppPurchase = new InAppPurchase();

_iap.addEventListener(InAppPurchaseEvent.INIT_SUCCESS, onInitSuccess);
_iap.addEventListener(InAppPurchaseEvent.INIT_ERROR, onInitError);
_iap.addEventListener(InAppPurchaseEvent.CONSUME_SUCCESS, onConsumeSuccess);
_iap.addEventListener(InAppPurchaseEvent.CONSUME_ERROR, onConsumeError);

_iap.init("YOUR_LICENSE_KEY_FOR_THE_APPLICATION");
//<


//> making the purchase
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_SUCCESS, onPurchaseSuccess);
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_ALREADY_OWNED, onPurchaseSuccess);
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_ERROR, onPurchaseError);
_iap.purchase("my.product.id");
//<


//> getting already purchased products
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);
_iap.restore();

...

protected function onRestoreSuccess(event:InAppPurchaseEvent):void
{
	var purchase:InAppPurchaseDetails = _iap.getDetails("my.product.id");
}
//<


//> consuming purchased item
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);
_iap.restore();

...

protected function onRestoreSuccess(event:InAppPurchaseEvent):void
{
	_iap.addEventListener(InAppPurchaseEvent.CONSUME_SUCCESS, onConsumeSuccess);
	_iap.addEventListener(InAppPurchaseEvent.CONSUME_ERROR, onConsumeError);
	_iap.consume("my.product.id");
}
//<