/**
 * Veridu React Component
 * 
 */
'use strict';
import React, {
  Component,
  StyleSheet,
  TouchableOpacity,
  TouchableWithoutFeedback,
  Text,
  TextInput,
  View
} from 'react-native';

import VeriduGateway from '../components/VeriduGateway.js';
import AtmPage from './AtmPage.js';
import RegistrationAccountPage from './RegistrationAccountPage.js';

class RegistrationPage extends Component {

  constructor(props) {
      super(props);
      this._handleUser = this._handleUser.bind(this);
  }
  //register User from Vidu
  _handleUser(user)
  {
    this.setState({user: JSON.parse(user)});

    //fetch the Data up for registration
    fetch('https://readies.herokuapp.com/authentication/registration', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: user
    })
    .then((response) => response.text())
    .then((responseText) => {
      var respo = JSON.parse(responseText);

      //Create User Object
      var user = this.state.user;
      user.id = respo.userId;
      this.props.updateUser('user', user);
      this.props.navigator.push({
          name: 'AccountPage',
          component: RegistrationAccountPage
      });
    })
    .catch((error) => {
      console.warn(error);
    });
  }
  render() {
    return (
      <View style={styles.container}>
        <VeriduGateway callback={this._handleUser} gateway="https://gateway.veridu.com/1.1/widget?key=0a2b8718e0afbc3cbabf8e62550a3141ef0952ad&template=payments"/>
      </View>
    );
  }
}

var styles = StyleSheet.create({
  container: {
    flex: 1,
    width: 320,
    height: 480
  }
  });

module.exports = RegistrationPage;