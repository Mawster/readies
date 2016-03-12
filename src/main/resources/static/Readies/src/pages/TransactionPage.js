/**
 * 
 * 
 */
'use strict';

import React, {
  StyleSheet,
  TouchableWithoutFeedback,
  Text,
  View,
  Image
} from 'react-native';

import AtmPage from './AtmPage.js';
import TimerMixin from 'react-timer-mixin';

var TransactionPage = React.createClass({
  mixins: [TimerMixin],
  getInitialState: function() {
      return({waiting: null});
  },
  _onPressButton: function(transactionId){ 
    fetch('https://readies.herokuapp.com/transaction/'+transactionId+'/submit', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
          "userId": this.props.user.id
      })
    })
    .then((response) => response.text())
    .then((responseText) => {
      this.setState({client: "waiting"})

      var time = this.setTimeout(() => { 
        this.props.navigator.popToTop();
      }, 2000);
    });
  },
  render: function() {
    var text = "";
    if(this.state.client == "waiting")
    {
      text = "Please wait for "+this.props.route.person.user.firstname;
    } else {
      text = "I gave the money";
    }
    return (
      <View style={styles.container}>
        <Text style={styles.year}>Look for that person</Text>
        <Image
          source={{uri: this.props.route.person.user.photoUrl}}
          style={styles.thumbnail}
        />
        <View>
          <Text style={styles.title}>{this.props.route.person.user.firstname + " " +this.props.route.person.user.lastname.charAt(0)+"."}</Text>
          <Text style={styles.year}>Veridu Trust: {this.props.route.person.user.trustScore*100}</Text>
        </View>
        <TouchableWithoutFeedback onPress={() => this._onPressButton(this.props.route.transactionId)} style={styles.rightContainer}>
          <View style={styles.button}>
            <Text style={styles.buttonText}>{text}</Text>
          </View>
        </TouchableWithoutFeedback>
      </View>
    );
  }
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: "transparent"
  },  
  thumbnail: {
    width: 150,
    height: 150,
    margin: 10,
  },
  button: {
      backgroundColor: 'rgba(103, 87, 114, 0.7)',
      padding: 10,
      margin: 10,
      borderWidth: 1,
      borderColor: '#333',
  },
  buttonText: {
    color: '#ffffff',
    marginLeft: 30,
    marginRight: 30
  },
});

module.exports = TransactionPage;