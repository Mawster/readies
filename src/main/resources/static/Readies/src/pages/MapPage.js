'use strict';

import React, {
  Component,
  StyleSheet,
  Image,
  MapView,
  View,
  ListView,
  TouchableWithoutFeedback,
  Text,
  findNodeHandle
} from 'react-native';

import TransactionPage from './TransactionPage.js';

class MapViewExample  extends Component {

  constructor(props) {
    super(props);

    this.state =  {
      isFirstLoad: true,
      mapRegion: undefined,
      mapRegionInput: undefined,
      annotations: [],
      dataSource: new ListView.DataSource({
        rowHasChanged: (row1, row2) => row1 !== row2,
      }),
    };

    this._getAnnotations = this._getAnnotations.bind(this);
    this._onRegionChange = this._onRegionChange.bind(this);
    this._onRegionChangeComplete = this._onRegionChangeComplete.bind(this);
    this.renderPerson = this.renderPerson.bind(this);
  }

  componentDidMount() {
    console.log("Map is mounted");
    var annotations = this.props.availableTransactions.map(function(annotations){
      return (
        {
          latitude: annotations.debitor.latitude,
          longitude: annotations.debitor.longitude
        }
      );

    });
    this.setState({
      dataSource: this.state.dataSource.cloneWithRows(this.props.availableTransactions),
      annotations: annotations
    });
  }

  componentWillReceiveProps(nextProps) {
      this.setState({
        dataSource: this.state.dataSource.cloneWithRows(this.props.availableTransactions),
      });
    }

  render() {
    
    return (
      <View style={styles.container}>
        <Text style={{textAlign: "center"}}>Give some ready money to</Text>
        <ListView
          dataSource={this.state.dataSource}
          renderRow={this.renderPerson}
          style={styles.listView}
        />
        <MapView
          style={styles.map}
          onRegionChange={this._onRegionChange}
          onRegionChangeComplete={this._onRegionChangeComplete}
          annotations={this.state.annotations}
        />
      </View>
    );
  }

  _getAnnotations(region) {
    return [{
      longitude: region.longitude,
      latitude: region.latitude,
      title: 'You Are Here',
    }];
  }

  _accept(identifier) {
    fetch('https://readies.herokuapp.com//transaction/'+identifier.transactionId+'/accept', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        userId: this.props.user.id
      })
    })
    .then((response) => response.text())
    .then((responseText) => {

      var resp = JSON.parse(responseText);

      this.props.navigator.push({
          name: 'TransactionPage',
          component: TransactionPage,
          transactionId: identifier.transactionId,
          person: identifier.debitor,
      });
      console.log(resp);
    });
  }

  _onRegionChange(region) {
    this.setState({
      mapRegionInput: region,
    });
  }

  _onRegionChangeComplete(region) {
    if (this.state.isFirstLoad) {
      this.setState({
        mapRegionInput: region,
        annotations: this._getAnnotations(region),
        isFirstLoad: false,
      });
    }
  }
  renderPerson(person) {
    return (
      <View style={styles.container2}>
        <Image
          source={{uri: person.debitor.user.photoUrl}}
          style={styles.thumbnail}
        />
        <View style={styles.rightContainer}>
          <Text style={styles.title}>{person.amount} £</Text>
          <Text style={styles.year}>{person.debitor.user.firstname + " " +person.debitor.user.lastname.charAt(0)+"."}</Text>
          <Text style={styles.year}>Veridu Trust: {person.debitor.user.trustScore*100}</Text>
        </View>
        <TouchableWithoutFeedback onPress={() => this._accept(person)} style={styles.rightContainer}>
          <View style={styles.button}>
            <Text style={styles.buttonText}>Earn {person.amount * 0.02}£</Text>
          </View>
        </TouchableWithoutFeedback>
      </View>
    );
  }
}



var styles = StyleSheet.create({
  map: {
    height: 150
  },
  container: {
    paddingTop: 40,
  },
  container2: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  centerContainer: {
    flex: 1,
  },
  rightContainer: {
    flex: 1,
  },
  title: {
    fontSize: 20,
    marginBottom: 8,
    textAlign: 'center',
  },
  year: {
    textAlign: 'center',
  },
  thumbnail: {
    width: 53,
    height: 81,
  },
  listView: {
    paddingTop: 20,
    backgroundColor: "rgba(255, 255, 255, 0.2)",
    height: 300
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

module.exports = MapViewExample;