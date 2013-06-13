package com.phonegap.plugins.speech;

import java.util.List;

import org.apache.cordova.api.CallbackContext;
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
