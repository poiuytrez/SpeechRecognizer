/**
 * The MIT License
 *
 *	Copyright (c) 2011-2013
 *	Colin Turner (github.com/koolspin)  
 *	Guillaume Charhon (github.com/poiuytrez)  
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */

package org.apache.cordova.speechrecognizer;

import java.util.List;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

public class LanguageDetailsChecker extends BroadcastReceiver
{
    private List<String> supportedLanguages;
    private CallbackContext callbackContext;

    public LanguageDetailsChecker(CallbackContext callbackContext) {
    	super();
    	this.callbackContext = callbackContext;
    }
    
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle results = getResultExtras(true);
        
		// get the list of supported languages
        if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES))
        {            
            // Convert the map to json
            supportedLanguages = results.getStringArrayList(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
            JSONArray jsonLanguages = new JSONArray(supportedLanguages);
            callbackContext.success(jsonLanguages);
        } else {
        	callbackContext.error("Could not retrieve the list of supported languages");
        }
	}
	
	public List<String> getSupportedLanguages(){
		return supportedLanguages;
	}
}
