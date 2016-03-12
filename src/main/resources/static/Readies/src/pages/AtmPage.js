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

import DonorPage from './DonorPage.js';
import MapPage from './MapPage.js';

class AtmPage extends Component {

  constructor(props) {
      super(props);
      this.state = {
        numbers: [ [1,2,3], [4,5,6], [7,8,9] ],
        text: ''
      };

      this._onPressButton = this._onPressButton.bind(this);
      this._lookForDonor = this._lookForDonor.bind(this);
      this._gotoMap = this._gotoMap.bind(this);
  }

  _lookForDonor(){ 
    console.log('press');
    this.props.navigator.push({
        name: 'DonorPage',
        component: DonorPage,
        money: this.state.text
    });
  }

  _gotoMap(){ 
    this.props.navigator.push({
        name: 'MapPage',
        component: MapPage
    });
  }
  _onPressButton(e){ 
    this.setState({text: this.state.text + e});
    console.log(e);
  }
  componentDidMount(){
    this.props.setRoute(this.props.route);
    this.props.setNavigator(this.props.navigator);
  }
  render() {
    var self = this;

    //Create Number field
    var numbers = this.state.numbers.map(function(number) {
        return (
          <View key={number[0]+number[1]+number[2]} style={styles.rightContainer}>
            <TouchableOpacity key={number[0]} onPress={() => self._onPressButton(number[0])}>
              <Text style={styles.number}>{number[0]}</Text>
            </TouchableOpacity>
            <TouchableOpacity key={number[1]} onPress={() => self._onPressButton(number[1])}>
              <Text style={styles.number}>{number[1]}</Text>
            </TouchableOpacity>
            <TouchableOpacity key={number[2]} onPress={() => self._onPressButton(number[2])}>
              <Text style={styles.number}>{number[2]}</Text>
            </TouchableOpacity>
          </View>
        );
      });

    return (
      <View style={styles.container}>
        <TextInput
          style={styles.input}
          editable={false}
          value={this.state.text + "‎ £"}
        />
        {numbers}
        <View key="00" style={styles.rightContainer}>
            <TouchableOpacity key="0" onPress={() => self._onPressButton(0)}>
              <Text style={styles.number}>0</Text>
            </TouchableOpacity>
        </View>
        <TouchableWithoutFeedback onPress={this._lookForDonor}>
          <View style={styles.button}>
            <Text style={styles.buttonText}>get some</Text>
          </View>
        </TouchableWithoutFeedback>
        <TouchableWithoutFeedback onPress={this._gotoMap}>
          <View>
            <Text style={styles.earnSome}>earn some</Text>
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
  rightContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  number: {
      width: 50,
      height: 50,
      margin: 10,
      textAlign: "center",
      fontSize: 20,
      lineHeight: 35,
      borderRadius: 25,
      borderWidth: 1,
      borderColor: '#333',
      textShadowColor: '#fff',
      textShadowRadius: 10,
      textShadowOffset: { width: 5, height: 5}
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
  earnSome: {
    color: "rgba(103, 87, 114, 0.7)"
  }
  });

module.exports = AtmPage;