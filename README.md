Android SpeechRecognizer plugin for Cordova/Phonegap
===================================
This plugin provides access to the speech recognition feature. This plugin will recognize commands, phrases, etc as spoken by the user.

![Screenshot speak now](/screenshots/speaknow.png "Speak now")

Requirements
-------------
Android 2.2 (API level 8) is required  
Tested on Cordova 2.8, Android 2.2.1 and Android 4.2.2

Support
---------------------
For free community support, please use the issue tracker.  
To get professional non-free support for the plugin, please contact me at gcharhon(at)smartmobilesoftware.com.

Installation 
-------------
* Create a 'com/phonegap/plugins/speech' folder under 'src' and add [LanguageDetailsChecker.java](LanguageDetailsChecker.java) and [SpeechRecognizer.java](SpeechRecognizer.java) to it.
* Add [SpeechRecognizer.js](SpeechRecognizer.js) in your www folder.  
* Add in your index.html  
`<script type="text/javascript" charset="utf-8" src="SpeechRecognizer.js"></script>`  
* In res/xml/config.xml, add 
```xml
<feature name="SpeechRecognizer">  
      <param name="android-package" value="com.phonegap.plugins.speech.SpeechRecognizer"/>  
</feature> 
```    

 

### Example
```html
<!DOCTYPE HTML>
<html>
  <head>
    <title>PhoneGap</title>
  <script type="text/javascript" charset="utf-8" src="phonegap.js"></script> 
  <script type="text/javascript" charset="utf-8" src="speechrecognizer.js"></script>
  <script type="text/javascript" charset="utf-8">
     function onLoad(){
          document.addEventListener("deviceready", onDeviceReady, true);
     }
     function onDeviceReady()
	{
	    window.plugins.speechrecognizer.init(speechInitOk, speechInitFail);
	    // etc.
	}

	function speechInitOk() {
		alert("we are good");
		supportedLanguages();
		recognizeSpeech();
	}
	function speechInitFail(m) {
		alert(m);
	}

	// Show the list of the supported languages
	function supportedLanguages() {
		window.plugins.speechrecognizer.getSupportedLanguages(function(languages){
				// display the json array
				alert(languages);
			}, function(error){
				alert("Could not retrieve the supported languages");
		});
	}

	function recognizeSpeech() {
	    var requestCode = 1234;
	    var maxMatches = 5;
	    var promptString = "Please say a command";	// optional
		var language = "en-US";						// optional
	    window.plugins.speechrecognizer.startRecognize(speechOk, speechFail, requestCode, maxMatches, promptString, language);
	}
	
	function speechOk(result) {
	    var respObj, requestCode, matches;
	    if (result) {
	        respObj = JSON.parse(result);
	        if (respObj) {
	            var matches = respObj.speechMatches.speechMatch;
	            
	            for (x in matches) {
	                alert("possible match: " + matches[x]);
	                // regex comes in handy for dealing with these match strings
	            }
	        }        
	    }
	}
	
	function speechFail(message) {
	    console.log("speechFail: " + message);
	}
  </script>
  </head>
  <body onload="onLoad();">
       <h1>Welcome to PhoneGap</h1>
       <h2>Edit assets/www/index.html</h2>
  </body>
</html>
```

## License

The MIT License

Copyright (c) 2011-2013  
Colin Turner (github.com/koolspin)  
Guillaume Charhon (github.com/poiuytrez)  

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
