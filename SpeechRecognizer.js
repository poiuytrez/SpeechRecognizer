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

/**
 * constructor
 */
function SpeechRecognizer() {
}

/**
 * Recognize speech and return a list of matches
 *
 * @param successCallback
 * @param errorCallback
 * @param reqCode User-defined integer request code which will be returned when recognition is complete
 * @param maxMatches The maximum number of matches to return. 0 means the service decides how many to return.
 * @param promptString An optional string to prompt the user during recognition
 */
SpeechRecognizer.prototype.startRecognize = function(successCallback, errorCallback, maxMatches, promptString, language) {
    return cordova.exec(successCallback, errorCallback, "SpeechRecognizer", "startRecognize", [maxMatches, promptString, language]);
};

/**
 * Get the list of the supported languages in IETF BCP 47 format
 * 
 * @param successCallback
 * @param errorCallback
 *
 * Returns an array of codes in the success callback
 */
SpeechRecognizer.prototype.getSupportedLanguages = function(successCallback, errorCallback) {
    return cordova.exec(successCallback, errorCallback, "SpeechRecognizer", "getSupportedLanguages", []);
};

/**
 * Get the list of the supported languages in IETF BCP 47 format
 *
 * @param successCallback
 * @param errorCallback
 *
 * Return a String message with the result
 */
SpeechRecognizer.prototype.checkSpeechRecognition = function(successCallback, errorCallback) {
    return cordova.exec(successCallback, errorCallback, "SpeechRecognizer", "checkSpeechRecognition", []);
};

/**
 * Export
 */
module.exports = new SpeechRecognizer();

