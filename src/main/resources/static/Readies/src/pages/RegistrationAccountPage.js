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

import AtmPage from './AtmPage.js';

class RegistrationAccountPage extends Component {

  constructor(props) {
    super(props);

    this.state = {
      iban: null,
      bic: null
    }

    this._saveAccount = this._saveAccount.bind(this);
  }
  _saveAccount()
  {
    console.log('Save Account');
    var account = {iban: this.state.iban, bic: this.state.bic};
    console.log(account);
    this.props.updateUser('account', account);
    this.props.navigator.push({
        name: 'AtmPage',
        component: AtmPage
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text>IBAN</Text>
        <TextInput
          style={styles.input}
          value={this.state.iban}
          onChangeText={(text) => this.setState({iban: text})}
        />
        <Text>BIC </Text>
        <TextInput
          style={styles.input}
          value={this.state.bic}
          onChangeText={(text) => this.setState({bic: text})}
        />
        <TouchableWithoutFeedback onPress={this._saveAccount}>
          <View style={styles.button}>
            <Text style={styles.buttonText}>Save</Text>
          </View>
        </TouchableWithoutFeedback>
      </View>
    );
  }
}

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'transparent'
  },
  input: {
    textAlign: 'center',
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    width: 200,
    marginLeft: 60,
    marginBottom: 20
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
  }
});

module.exports = RegistrationAccountPage;