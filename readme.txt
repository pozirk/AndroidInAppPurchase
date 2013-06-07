# About
AndroidInAppPurchase is an Adobe AIR native extension (ANE) for Android to purchase virtual items.
It uses Google Play In-app Billing version 3 API.
Supported functionality:
- purchase of items;
- restoration of previously purchased items;
- consumption of items;
- subscriptions (not tested).

# Docs
Please, read docs and try ANE before asking any questions.
http://developer.android.com/google/play/billing/index.html
http://help.adobe.com/en_US/air/extensions/index.html


# Installation
Add "InAppPurchase.ane" and "air\InAppPurchase\bin\InAppPurchase.swc" to your AIR project

Add the following lines to your AIR Aplication-app.xml file inside <manifestAdditions> section:
<uses-permission android:name="com.android.vending.BILLING" />
<application android:enabled="true">                
      <activity android:name="com.pozirk.payment.BillingActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" android:background="#30000000" />
 </application>


# Examples
import com.pozirk.payment.android.InAppPurchase;
import com.pozirk.payment.android.InAppPurchaseEvent;
import com.pozirk.payment.android.InAppPurchaseDetails;


//> initialization of InAppPurchase
_iap:InAppPurchase = new InAppPurchase();

_iap.addEventListener(InAppPurchaseEvent.INIT_SUCCESS, onInitSuccess);
_iap.addEventListener(InAppPurchaseEvent.INIT_ERROR, onInitError);

_iap.init("YOUR_LICENSE_KEY_FOR_THE_APPLICATION");

...

protected function onInitSuccess(event:InAppPurchaseEvent):void
{
	//you can restore previously purchased items here
}

protected function onInitError(event:InAppPurchaseEvent):void
{
	trace(event.data); //trace error message
}
//<


//> making the purchase, _iap should be initialized first
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_SUCCESS, onPurchaseSuccess);
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_ALREADY_OWNED, onPurchaseSuccess);
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_ERROR, onPurchaseError);
_iap.purchase("my.product.id", InAppPurchaseDetails.TYPE_INAPP);

protected function onPurchaseSuccess(event:InAppPurchaseEvent):void
{
	trace(event.data); //product id
}

protected function onPurchaseError(event:InAppPurchaseEvent):void
{
	trace(event.data); //trace error message
}
//<


//> getting purchased products details, _iap should be initialized first
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);
_iap.restore(); //restoring both in-app items and subscriptions

...

protected function onRestoreSuccess(event:InAppPurchaseEvent):void
{
	var purchase:InAppPurchaseDetails = _iap.getPurchaseDetails("my.product.id"); //getting details of purchase: time, etc.
}

protected function onRestoreError(event:InAppPurchaseEvent):void
{
	trace(event.data); //trace error message
}
//<

//> getting available products details
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);

var items:Array<String> = ["my.product.id1", "my.product.id2", "my.product.id3"];
var subs:Array<String> = ["my.subs.id1", "my.subs.id2", "my.subs.id3"];
_iap.restore(items, subs); //restoring both in-app items and subscriptions

...

protected function onRestoreSuccess(event:InAppPurchaseEvent):void
{
	var skuDetails1:InAppSkuDetails = _iap.getSkuDetails("my.product.id1"); //getting details of product: time, etc.
	var skuDetails2:InAppSkuDetails = _iap.getSkuDetails("my.subs.id1"); //getting details of product: time, etc.
}

protected function onRestoreError(event:InAppPurchaseEvent):void
{
	trace(event.data); //trace error message
}
//<


//> consuming purchased item
//>> need to retrieve purchased items first
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);
_iap.restore();
//<<

...

protected function onRestoreSuccess(event:InAppPurchaseEvent):void
{
	_iap.addEventListener(InAppPurchaseEvent.CONSUME_SUCCESS, onConsumeSuccess);
	_iap.addEventListener(InAppPurchaseEvent.CONSUME_ERROR, onConsumeError);
	_iap.consume("my.product.id");
}
//<


# Game with in-app purchase
https://play.google.com/store/apps/details?id=air.com.pozirk.allinonesolitaire
Just click "star" character in main menu


# Testing
http://developer.android.com/google/play/billing/billing_testing.html


# Misc
ANE is build with AIR3.6, in order to rebuild for another version do the following:
- edit "air\extension.xml" and change 3.6 in very first line to any 3.X you need;
- edit "build.bat" and in the very last line change path from AIR3.7 SDK to any AIR3.X SDK you need;
- execute "build.bat" to repack the ANE.