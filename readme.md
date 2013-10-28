# About
AndroidInAppPurchase is an Adobe AIR native extension (ANE) for Android to purchase virtual items.<br />
It uses Google Play In-app Billing version 3 API.<br />
Supported functionality:<br />
- purchase of items;<br />
- restoration of previously purchased items;<br />
- consumption of items;<br />
- subscriptions (not tested).<br />

# Docs
Please, read docs and try ANE before asking any questions.<br />
http://developer.android.com/google/play/billing/index.html<br />
http://help.adobe.com/en_US/air/extensions/index.html<br />


# Installation
Extension ID: com.pozirk.AndroidInAppPurchase
Add "InAppPurchase.ane" and "air\InAppPurchase\bin\InAppPurchase.swc" to your AIR project.<br />
Add the following lines to your AIR Aplication-app.xml file inside &lt;manifestAdditions&gt; section:<br />
<br />
&lt;uses-permission android:name="com.android.vending.BILLING" /&gt;<br />
&lt;application android:enabled="true"&gt;<br />
	&lt;activity android:name="com.pozirk.payment.BillingActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" android:background="#30000000" /&gt;<br />
&lt;/application&gt;<br />


# Examples
```actionscript
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


//> getting purchased product details, _iap should be initialized first
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);
_iap.restore(); //restoring purchased in-app items and subscriptions

...

protected function onRestoreSuccess(event:InAppPurchaseEvent):void
{
	//getting details of purchase: time, etc.
	var purchase:InAppPurchaseDetails = _iap.getPurchaseDetails("my.product.id");
}

protected function onRestoreError(event:InAppPurchaseEvent):void
{
	trace(event.data); //trace error message
}
//<

//> getting purchased and not purchased product details
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);

var items:Array<String> = ["my.product.id1", "my.product.id2", "my.product.id3"];
var subs:Array<String> = ["my.subs.id1", "my.subs.id2", "my.subs.id3"];
_iap.restore(items, subs); //restoring purchased + not purchased in-app items and subscriptions

...

protected function onRestoreSuccess(event:InAppPurchaseEvent):void
{
	//getting details of product: time, etc.
	var skuDetails1:InAppSkuDetails = _iap.getSkuDetails("my.product.id1");

	//getting details of product: time, etc.
	var skuDetails2:InAppSkuDetails = _iap.getSkuDetails("my.subs.id1");

	//getting details of purchase: time, etc.
	var purchase:InAppPurchaseDetails = _iap.getPurchaseDetails("my.purchased.product.id");
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
```


# Testing
http://developer.android.com/google/play/billing/billing_testing.html


# Misc
ANE is build with AIR3.6, in order to rebuild for another version do the following:<br />
- edit "air\extension.xml" and change 3.6 in very first line to any 3.X you need;<br />
- edit "build.bat" and in the very last line change path from AIR3.X SDK to any AIR3.X SDK you need;<br />
- execute "build.bat" to repack the ANE.<br />