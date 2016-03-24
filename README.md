Android SpeechRecognizer plugin for Cordova/Phonegap
===================================
This plugin provides access to the speech recognition feature. This plugin will recognize commands, phrases, etc as spoken by the user.

![Screenshot speak now](/screenshots/speaknow.png "Speak now")

If you find this plugin useful, please donate via BitCoin to support it:  
17JK27E4vbzPrJbBAtvjUVN3LrFcATtRA1

Requirements
-------------
Android 2.2 (API level 8) is required  
Compatible with Cordova 3.0.

android:launchMode CANNOT be "singleInstance"

It can be eventually "singleTask" (or "standard", "singleTop")

Support
---------------------
For free community support, please use the issue tracker.  
To get professional non-free support for the plugin, please contact me at gcharhon(at)smartmobilesoftware.com.

Installation for cordova>=3.0.0 (tested 3.1.0, 3.4.0, 6.0.0)
------------------------------------------------------------
```bash
cordova create buz
cd buz
cordova platform add android
cordova plugin add https://github.com/poiuytrez/SpeechRecognizer
```
  

Usage
-------

#### Start recognition
Show the recognition dialog and get the recognized sentences

    SpeechRecognizer.startRecognize(success, error, maxMatches, promptString, language);
parameters
* success : The success callback. It provides a json array with all possible matches. Example: "[hello world,low world,hello walls]".
* error : The error callback.
* maxMaches : Maximum of returned possibles sentences matches.
* promptString : String shown below the Google logo and the microphone icon. Instruct the user of what to. Example: "Speak now"
* language : Language used by the speech recognition engine. Example: "en-US".

#### Supported languages
Get the list of supported languages codes

    SpeechRecognizer.getSupportedLanguages(success, error);
parameters
* success : The success callback. It provides a json array of all the recognized language codes. Example: "[en-US,fr-FR,de-DE]".
* error : The error callback.

Full example
----------------
```html
<!DOCTYPE html>
<html>
    <head>
        <title>Speech Recognition plugin demo</title>
        <script type="text/javascript" src="cordova.js"></script>
    </head>
    <body>

        <script type="text/javascript">

            function onDeviceReady(){
                console.log("Device is ready");
            }

            function recognizeSpeech() {
                var maxMatches = 5;
                var promptString = "Speak now";	// optional
                var language = "en-US";						// optional
                window.plugins.speechrecognizer.startRecognize(function(result){
                    alert(result);
                }, function(errorMessage){
                    console.log("Error message: " + errorMessage);
                }, maxMatches, promptString, language);
            }

            // Show the list of the supported languages
            function getSupportedLanguages() {
                window.plugins.speechrecognizer.getSupportedLanguages(function(languages){
                    // display the json array
                    alert(languages);
                }, function(error){
                    alert("Could not retrieve the supported languages : " + error);
                });
            }

            // Check to see if a recognition activity is present
            function checkSpeechRecognition() {
                window.plugins.speechrecognizer.checkSpeechRecognition(function(){
                    alert('Speech Recogition is present! :D');
                }, function(){
                    alert('Speech Recogition not found! :(');
                });
            }

            document.addEventListener("deviceready", onDeviceReady, true);
        </script>

        <button onclick="recognizeSpeech();">Start recognition</button>
        <button onclick="getSupportedLanguages();">Get Supported Languages</button>
        <button onclick="checkSpeechRecognition();">Check Speech Recognition</button>
    </body>
</html>
```

License
----------------

The MIT License

Copyright (c) 2011-2013  
Colin Turner (github.com/koolspin)  
Guillaume Charhon (github.com/poiuytrez)  

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
