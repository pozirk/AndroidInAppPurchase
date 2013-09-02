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
Add "InAppPurchase.ane" and "air\InAppPurchase\bin\InAppPurchase.swc" to your AIR project.<br />
Add the following lines to your AIR Aplication-app.xml file inside &lt;manifestAdditions&gt; section:<br />
&lt;uses-permission android:name="com.android.vending.BILLING" /&gt;<br />
&lt;application android:enabled="true"&gt;<br />
	&lt;activity android:name="com.pozirk.payment.BillingActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" android:background="#30000000" /&gt;<br />
&lt;/application&gt;<br />


# Examples
import com.pozirk.payment.android.InAppPurchase;<br />
import com.pozirk.payment.android.InAppPurchaseEvent;<br />
import com.pozirk.payment.android.InAppPurchaseDetails;<br />


//&gt; initialization of InAppPurchase<br />
_iap:InAppPurchase = new InAppPurchase();<br />
<br />
_iap.addEventListener(InAppPurchaseEvent.INIT_SUCCESS, onInitSuccess);<br />
_iap.addEventListener(InAppPurchaseEvent.INIT_ERROR, onInitError);<br />
<br />
_iap.init("YOUR_LICENSE_KEY_FOR_THE_APPLICATION");<br />
<br />
...<br />
<br />
protected function onInitSuccess(event:InAppPurchaseEvent):void<br />
{<br />
	//you can restore previously purchased items here<br />
}<br />
<br />
protected function onInitError(event:InAppPurchaseEvent):void<br />
{<br />
	trace(event.data); //trace error message<br />
}<br />
//&lt;<br />
<br />
<br />
//&gt; making the purchase, _iap should be initialized first<br />
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_SUCCESS, onPurchaseSuccess);<br />
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_ALREADY_OWNED, onPurchaseSuccess);<br />
_iap.addEventListener(InAppPurchaseEvent.PURCHASE_ERROR, onPurchaseError);<br />
_iap.purchase("my.product.id", InAppPurchaseDetails.TYPE_INAPP);<br />
<br />
protected function onPurchaseSuccess(event:InAppPurchaseEvent):void<br />
{<br />
	trace(event.data); //product id<br />
}<br />
<br />
protected function onPurchaseError(event:InAppPurchaseEvent):void<br />
{<br />
	trace(event.data); //trace error message<br />
}<br />
//&lt;<br />
<br />
<br />
//&gt; getting purchased product details, _iap should be initialized first<br />
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);<br />
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);<br />
_iap.restore(); //restoring purchased in-app items and subscriptions<br />
<br />
...<br />
<br />
protected function onRestoreSuccess(event:InAppPurchaseEvent):void<br />
{<br />
	//getting details of purchase: time, etc.<br />
	var purchase:InAppPurchaseDetails = _iap.getPurchaseDetails("my.product.id");<br />
}<br />
<br />
protected function onRestoreError(event:InAppPurchaseEvent):void<br />
{<br />
	trace(event.data); //trace error message<br />
}<br />
//&lt;<br />
<br />
//&gt; getting purchased and not purchased product details<br />
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);<br />
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);<br />
<br />
var items:Array&lt;String&gt; = ["my.product.id1", "my.product.id2", "my.product.id3"];<br />
var subs:Array&lt;String&gt; = ["my.subs.id1", "my.subs.id2", "my.subs.id3"];<br />
_iap.restore(items, subs); //restoring purchased + not purchased in-app items and subscriptions<br />
<br />
...<br />
<br />
protected function onRestoreSuccess(event:InAppPurchaseEvent):void<br />
{<br />
	//getting details of product: time, etc.<br />
	var skuDetails1:InAppSkuDetails = _iap.getSkuDetails("my.product.id1");<br />
<br />
	//getting details of product: time, etc.<br />
	var skuDetails2:InAppSkuDetails = _iap.getSkuDetails("my.subs.id1");<br />
<br />
	//getting details of purchase: time, etc.<br />
	var purchase:InAppPurchaseDetails = _iap.getPurchaseDetails("my.purchased.product.id");<br />
}<br />
<br />
protected function onRestoreError(event:InAppPurchaseEvent):void<br />
{<br />
	trace(event.data); //trace error message<br />
}<br />
//&lt;<br />
<br />
<br />
//&gt; consuming purchased item<br />
//&gt;&gt; need to retrieve purchased items first<br />
_iap.addEventListener(InAppPurchaseEvent.RESTORE_SUCCESS, onRestoreSuccess);<br />
_iap.addEventListener(InAppPurchaseEvent.RESTORE_ERROR, onRestoreError);<br />
_iap.restore();<br />
//&lt;&lt;<br />
<br />
...<br />
<br />
protected function onRestoreSuccess(event:InAppPurchaseEvent):void<br />
{<br />
	_iap.addEventListener(InAppPurchaseEvent.CONSUME_SUCCESS, onConsumeSuccess);<br />
	_iap.addEventListener(InAppPurchaseEvent.CONSUME_ERROR, onConsumeError);<br />
	_iap.consume("my.product.id");<br />
}<br />
//&lt;<br />


# Testing
http://developer.android.com/google/play/billing/billing_testing.html


# Misc
ANE is build with AIR3.6, in order to rebuild for another version do the following:<br />
- edit "air\extension.xml" and change 3.6 in very first line to any 3.X you need;<br />
- edit "build.bat" and in the very last line change path from AIR3.7 SDK to any AIR3.X SDK you need;<br />
- execute "build.bat" to repack the ANE.<br />