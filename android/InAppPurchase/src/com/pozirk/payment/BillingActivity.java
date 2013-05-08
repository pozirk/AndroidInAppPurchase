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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

//need this activity in order to handle onActivityResult properly
public class BillingActivity extends Activity
{	
	@Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
    // Pass on the activity result to the helper for handling
    if (!Billing.getInstance().handlePurchaseResult(requestCode, resultCode, data))
    {
        // not handled, so handle it ourselves (here's where you'd
        // perform any handling of activity results not related to in-app
        // billing...
        super.onActivityResult(requestCode, resultCode, data);
    }
  }
	
	@Override
  public void onCreate(Bundle savedInstanceState)
	{
    super.onCreate(savedInstanceState);
    
    //> make a nice spinning circle in the center of the screen
    ProgressBar progress = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
    
    RelativeLayout layout = new RelativeLayout(this);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.CENTER_IN_PARENT);
    layout.addView(progress, params);
    
    setContentView(layout);
    //<
    
    Billing.getInstance().purchase(this);
	}
	
	@Override
  public void onDestroy() //called (so Activity is destroyed) when app reopened from app drawer, what is suxxx
	{
		Billing.getInstance().endPurchase(this); //we were in the middle of purchase, reset it
    super.onDestroy();
  }
}
