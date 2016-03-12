/**
 * Veridu React Component
 * 
 */
'use strict';
import React, {
  Component,
  StyleSheet,
  TouchableHighlight,
  Text,
  View,
  ActivityIndicatorIOS
} from 'react-native';

class DonorPage extends Component {

  constructor(props) {
      super(props);
  }
  _onPressButton(){ 
    console.log('press');
  }

  componentDidMount(){
    fetch('https://readies.herokuapp.com/transaction', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
          "userId": this.props.user.id,
          "amount": this.props.route.money,
          "trustScoreThreshold": 40
      })
    })
    
  }

  render() {
    return (
      <View style={styles.container}>
        <ActivityIndicatorIOS
          animating={true}
          style={{height: 80}}
          size="large"
        />
        <Text>{this.props.route.money}</Text>
        <Text>Looking for Donor</Text>
      </View>
    );
  }
}

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: "transparent"
  }
});

module.exports = DonorPage;