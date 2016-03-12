/**
 * Created by Mario on 12.03.2016.
 */
/**
 * Veridu React Component
 * @author: florian bauer for the hackathon realexfintechparty
 * @todo: testing, testing, testing
 */
'use strict';

import React, {
    Component,
    StyleSheet,
    WebView
} from 'react-native';

// Integrating a Webview Component, that includes a Bridge for Communication
// https://github.com/alinz/react-native-webview-bridge
import WebViewBridge from 'react-native-webview-bridge';

// Veridu Gateway as React Component
//
// @params/props:
// callback: (function) handles the user object
// gateway: (string) url of veridu payment gateway
//
class VeriduGateway extends Component {
    render() {

        const injectScript = `
	  function webViewBridgeReady(cb) {
	    //checks whether WebViewBirdge exists in global scope.
	    if (window.WebViewBridge) {
	      cb(window.WebViewBridge);
	      return;
	    }

	    function handler() {
	      //remove the handler from listener since we don't need it anymore
	      window.removeEventListener('WebViewBridge', handler, false);
	      //pass the WebViewBridge object to the callback
	      cb(window.WebViewBridge);
	    }

	    //if WebViewBridge doesn't exist in global scope attach itself to window
	    //event system. Once the code is being injected by extension, the handler will
	    //be called.
	    window.addEventListener('WebViewBridge', handler, false);
	  }

	  webViewBridgeReady(function (webViewBridge) {
		var timeout = setInterval(function(){

			//check if we have set a name
			if(window.stats.user.name.value) {

				//split the name
				var names = window.stats.user.name.value.split(' ');

				//prepare the string to give back to the native environment
				var string = JSON.stringify({
					firstname: names[0],
					lastname: names[1],
					trustScore: window.stats.user.name.score,
					email: window.stats.user.email.value,
					photoUrl: window.stats.user.picture
				});

				//Send the response
				webViewBridge.send(string);
				window.clearTimeout(timeout);
			}
		}, 3000);
	  });
	`;

        return (
            <WebViewBridge
                ref="webviewbridge"
                automaticallyAdjustContentInsets={false}
                style={styles.webView}
                javaScriptEnabled={true}
                decelerationRate="normal"
                startInLoadingState={true}
                scalesPageToFit={true}
                source={{uri: this.props.gateway}}
                onBridgeMessage={this.props.callback}
                injectedJavaScript={injectScript}>
            </WebViewBridge>
        );
    }
}

var styles = StyleSheet.create({
    webView: {
        flex: 1,
        backgroundColor: 'transparent',
        flexDirection: 'row',
        alignItems: 'stretch'
    }
});

module.exports = VeriduGateway;