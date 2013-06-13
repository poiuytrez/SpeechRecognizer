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
package com.phonegap.plugins.speech;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.CallbackContext;
//import org.apache.cordova.api.PluginResult.Status;

import android.util.Log;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

/**
 * Style and such borrowed from the TTS and PhoneListener plugins
 */
public class SpeechRecognizer extends CordovaPlugin {
    private static final String LOG_TAG = SpeechRecognizer.class.getSimpleName();
    public static final String NOT_PRESENT_MESSAGE = "Speech recognition is not present or enabled";

    private CallbackContext callbackContext;
    private LanguageDetailsChecker languageDetailsChecker;

    //@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
		Boolean isValidAction = true;

    	this.callbackContext= callbackContext;

		// Action selector
    	if ("startRecognize".equals(action)) {
            // recognize speech
            startSpeechRecognitionActivity(args);     
        } else if ("getSupportedLanguages".equals(action)) {
        	getSupportedLanguages();
        } else {
            // Invalid action
        	this.callbackContext.error("Unknown action: " + action);
        	isValidAction = false;
        }
    	
        return isValidAction;

    }

    // Get the list of supported languages
    private void getSupportedLanguages() {
    	if (languageDetailsChecker == null){
    		languageDetailsChecker = new LanguageDetailsChecker(callbackContext);
    	}
    	// Create and launch get languages intent
    	Intent detailsIntent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
    	cordova.getActivity().sendOrderedBroadcast(detailsIntent, null, languageDetailsChecker, null, Activity.RESULT_OK, null, null);
		
	}

	/**
     * Fire an intent to start the speech recognition activity.
     *
     * @param args Argument array with the following string args: [req code][number of matches][prompt string]
     */
    private void startSpeechRecognitionActivity(JSONArray args) {
        int reqCode = 42;   //Hitchhiker?
        int maxMatches = 0;
        String prompt = "";
        String language = "";

        try {
            if (args.length() > 0) {
                // Request code - passed back to the caller on a successful operation
                String temp = args.getString(0);
                reqCode = Integer.parseInt(temp);
            }
            if (args.length() > 1) {
                // Maximum number of matches, 0 means the recognizer decides
                String temp = args.getString(1);
                maxMatches = Integer.parseInt(temp);
            }
            if (args.length() > 2) {
                // Optional text prompt
                prompt = args.getString(2);
            }
            if (args.length() > 3){
            	// Optional language specified
            	language = args.getString(3);
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, String.format("startSpeechRecognitionActivity exception: %s", e.toString()));
        }

        // Create the intent and set parameters
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if(!language.equals("")){
        	intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        }
        if (maxMatches > 0)
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, maxMatches);
        if (!prompt.equals(""))
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, prompt);
        cordova.startActivityForResult(this, intent, reqCode);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            float[] confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

            if (confidence != null) {
                Log.d(LOG_TAG, "confidence length "+ confidence.length);
                Iterator<String> iterator = matches.iterator();
                int i = 0;
                while(iterator.hasNext()) {
                    Log.d(LOG_TAG, "Match = " + iterator.next() + " confidence = " + confidence[i]);
                    i++;
                }
            } else {
                Log.d(LOG_TAG, "No confidence" +
                        "");
            }

            returnSpeechResults(requestCode, matches);
        }
        else {
            // Failure - Let the caller know
            this.callbackContext.error(Integer.toString(resultCode));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void returnSpeechResults(int requestCode, ArrayList<String> matches) {
        boolean firstValue = true;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"speechMatches\": {");
        sb.append("\"requestCode\": ");
        sb.append(Integer.toString(requestCode));
        sb.append(", \"speechMatch\": [");

        Iterator<String> iterator = matches.iterator();
        while(iterator.hasNext()) {
            String match = iterator.next();

            if (firstValue == false)
                sb.append(", ");
            firstValue = false;
            sb.append(JSONObject.quote(match));
        }
        sb.append("]}}");

        this.callbackContext.success(sb.toString());
    }
    
}