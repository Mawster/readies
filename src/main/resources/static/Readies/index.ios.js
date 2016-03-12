/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  Navigator,
  View,
  Image,
  ScrollView,
  AsyncStorage
} from 'react-native';

import AtmPage from './src/pages/AtmPage.js';
import MapPage from './src/pages/MapPage.js';
import RegistrationPage from './src/pages/RegistrationPage.js';
import RegistrationAccountPage from './src/pages/RegistrationAccountPage.js';

import TimerMixin from 'react-timer-mixin';

var watch;

var Readies = React.createClass({
  mixins: [TimerMixin],
  getInitialState: function() {
    return({
      lat: null,
      lon: null,
      user: {
        id: null,
        firstname: null,
        lastname: null,
        email: null,
        photo: null
      },
      account: {
        iban: null,
        bic: null
      },
      initPage: AtmPage,
      route: {},
      navigator: {},
      availableTransactions: [],
      ongoingTransactions: []
    });
  },
  async _loadInitialState() {
    //var user = await AsyncStorage.clear();
    try {
      var user = await AsyncStorage.getItem('@AsyncStorageExample:user');
      if (user !== null){
        this.setState({user: JSON.parse(user)});
        console.log(user);
      } else {
        console.log('Initialized with no selection on disk.');
      }

      var account = await AsyncStorage.getItem('@AsyncStorageExample:account');
      if (account !== null){
        this.setState({account: JSON.parse(account)}, function(){
          this._trackUser();
        });
      } else {
        console.log('Initialized with no selection on disk.');
        this._trackUser();
      }
    } catch (error) {
      console.log('AsyncStorage error: ' + error.message);
    }
  },
  async _updateUser(type, content) {

    console.log("update user with type "+ type);
    console.log("update content with content "+ content);

    if(type == "user") {
      this.setState({user: content});
    }else if(type == "account") {
      this.setState({account: content});
    }

    try {
      await AsyncStorage.setItem('@AsyncStorageExample:'+type, JSON.stringify(content));
    } catch (error) {
      console.log(error);
    }
  },
  _trackUser: function(){
    console.log(navigator.geolocation);

    if(!this.state.user.id) {
      if(!this.state.user.id && !this.state.account.bic) {
        console.log('User ID and Account set');
        this.state.navigator.push({
            name: 'RegistrationPage',
            component: RegistrationPage
        });
      } else if(!this.state.account.bic) {
        this.state.navigator.push({
            name: 'RegistrationAccountPage',
            component: RegistrationAccountPage
        });
      }
    } else {
      console.log(navigator.geolocation);
      //Track GPS
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.setState({lat: position.coords.latitude, lon: position.coords.longitude});
        },
        (error) => {
          console.log(error.message);
        },
        {enableHighAccuracy: true, timeout: 20000, maximumAge: 1000}
      );

      //Track User
      navigator.geolocation.watchPosition((position) => {
        this.setState({lat: position.coords.latitude, lon: position.coords.longitude});
      },
      (error) => {
          console.log(error.message);

          navigator.geolocation.clearWatch(watch);
        });

      //Upload Position
      var time = this.setInterval(
        () => { 

          //user check

          //Update Position
          fetch('https://readies.herokuapp.com/location', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              userId: this.state.user.id,
              "latitude": 51.531305,
              "longitude": -0.122639
            })
          })
          .then((response) => response.text())
          .then((responseText) => {

            //Get Location Result
            var resp = JSON.parse(responseText);

            console.log(resp);

            //
            //Event Handler
            //
            if(this.state.route.name == "AtmPage"){
              console.log(resp.availableTransactions);

              //if there are results available, redirect to the overview page
              if(resp.availableTransactions.length > 0){
                this.setState({
                  availableTransactions: resp.availableTransactions
                });
              }
            }
            //If you are waiting for an offer, check the ongoingTransactions
            else if ((this.state.route.name == "DonorPage")){

              console.log(resp.onGoingTransactions);
            }
          });
        },
        5000
      );
    }
  },
  setRoute: function(route){
    this.setState({route: route});
  },
  setNavigator: function(navigator){
    this.setState({navigator: navigator});
  },
  componentDidMount: function() {
    this._loadInitialState().done();
  },
  componentWillUnmount: function() {
    navigator.geolocation.clearWatch(watch);
  },
  render: function() {
    var user = this.state.user;
    var account = this.state.account;
    var availableTransactions = this.state.availableTransactions;
    var ongoingTransactions = this.state.ongoingTransactions;
    var updateUser = this._updateUser;
    var setRoute = this.setRoute;
    var setNavigator = this.setNavigator;

    return (
      <View style={styles.navigatorContainer}>
        <Image source={require('./src/img/bg.png')} style={styles.background} />
        <Navigator
            initialRoute={{name: 'AtmPage', component: this.state.initPage}}
            sceneStyle={styles.container}
            configureScene={() => {
                return Navigator.SceneConfigs.FloatFromRight;
            }}
            renderScene={(route, navigator) => {
                if (route.component) {
                    return React.createElement(route.component, { navigator, route, user, account, updateUser, setRoute, setNavigator, availableTransactions, ongoingTransactions});
                }
            }}
         />
      </View>
    );
  }
});

var styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'transparent'
    },
    background: {
        position: 'absolute',
        left: 0,
        top: 0,
        width: 320,
        height: 480
    },
    navigatorContainer: {
        flex: 1,
        backgroundColor: 'transparent'
    }
});

AppRegistry.registerComponent('Readies', () => Readies);
