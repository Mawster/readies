# Readies

The readies app brings cash back to life.

### Version
1.0.0

## Interfaces
There are several interfaces for registering a new user, obtain the userId for allready registered users, create new transaction, accept/decline them and handling the payment process.

##### /authentication/registration
Method: POST

Consumes: application/json - example:
```json
{
    "firstname":"Mario",
    "lastname":"Luef",
    "email":"Mario.luef1@catalysts.cc",
    "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
    "trustScore": 0.8
}
```
Produces: application/json - example:
```json
{
  "userId": "8a9ad0325369d178015369d1ad290000"
}
```

##### /authentication/login
Method: POST

Consumes: application/json - example:
```json
{
"email":"Mario.luef@catalysts.cc"
}
```
Produces: application/json - example:
```json
{
  "userId": "8a9ad0325369516e015369518a320000"
}
```

##### /location
Method: POST

Consumes: application/json - example:
```json
{
    "userId":"4028ac81536821d401536821ef400000",
    "longitude": 51.531127,
    "latitude": -0.123604
}
```
Produces: application/json - example:
```json
{
  "availableTransactions": [
    {
      "transactionId": "3c2709e0-4682-4ec1-b4eb-c353e653d9fc",
      "startTimeMillis": 0,
      "debitor": {
        "user": {
          "id": "8a9ad0325369d178015369d1ad290000",
          "firstname": "Mario",
          "lastname": "Luef",
          "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
          "trustScroe": 0.8
        },
        "longitude": -0.12960399687290192,
        "latitude": 51.52512741088867,
        "timeMillis": 1457769344834
      },
      "amount": 95.55,
      "creditor": null,
      "debitorSubmitted": false,
      "creditorSubmitted": false
    },
    {
      "transactionId": "bcac8717-dd43-4df1-8878-63c9d597055a",
      "startTimeMillis": 0,
      "debitor": {
        "user": {
          "id": "8a9ad0325369d178015369d1ad290000",
          "firstname": "Mario",
          "lastname": "Luef",
          "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
          "trustScroe": 0.8
        },
        "longitude": -0.12960399687290192,
        "latitude": 51.52512741088867,
        "timeMillis": 1457769344834
      },
      "amount": 95.55,
      "creditor": null,
      "debitorSubmitted": false,
      "creditorSubmitted": false
    },
    {
      "transactionId": "da86beda-e4c0-4051-b556-da2e1434915e",
      "startTimeMillis": 0,
      "debitor": {
        "user": {
          "id": "8a9ad0325369d178015369d1ad290000",
          "firstname": "Mario",
          "lastname": "Luef",
          "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
          "trustScroe": 0.8
        },
        "longitude": -0.12960399687290192,
        "latitude": 51.52512741088867,
        "timeMillis": 1457769344834
      },
      "amount": 95.55,
      "creditor": null,
      "debitorSubmitted": false,
      "creditorSubmitted": false
    }
  ],
  "onGoingTransactions": [
    {
      "transactionId": "d24f5b8d-0ab3-412d-8d8f-bdf1b0aa1543",
      "startTimeMillis": 0,
      "debitor": {
        "user": {
          "id": "8a9ad0325369d178015369d1ad290000",
          "firstname": "Mario",
          "lastname": "Luef",
          "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
          "trustScroe": 0.8
        },
        "longitude": -0.12960399687290192,
        "latitude": 51.52512741088867,
        "timeMillis": 1457769344834
      },
      "amount": 95.55,
      "creditor": {
        "user": {
          "id": "8a9ad0325369d178015369d26e910003",
          "firstname": "Mario",
          "lastname": "Luef",
          "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
          "trustScroe": 0.8
        },
        "longitude": -0.12960399687290192,
        "latitude": 51.52512741088867,
        "timeMillis": 1457769479934
      },
      "debitorSubmitted": false,
      "creditorSubmitted": false
    }
  ]
}
```

##### /location
Method: GET

Produces: application/json - example:
```json
[
  {
    "user": {
      "id": "8a9ad0325369d178015369d1ad290000",
      "firstname": "Mario",
      "lastname": "Luef",
      "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
      "trustScroe": 0.8
    },
    "longitude": -0.12960399687290192,
    "latitude": 51.52512741088867,
    "timeMillis": 1457769344834
  },
  {
    "user": {
      "id": "8a9ad0325369d178015369d255640002",
      "firstname": "Mario",
      "lastname": "Luef",
      "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
      "trustScroe": 0.8
    },
    "longitude": -0.12960399687290192,
    "latitude": 51.52512741088867,
    "timeMillis": 1457769349609
  },
  {
    "user": {
      "id": "8a9ad0325369d178015369d23cd10001",
      "firstname": "Mario",
      "lastname": "Luef",
      "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
      "trustScroe": 0.8
    },
    "longitude": -0.12960399687290192,
    "latitude": 51.52512741088867,
    "timeMillis": 1457769347562
  },
  {
    "user": {
      "id": "8a9ad0325369d178015369d26e910003",
      "firstname": "Mario",
      "lastname": "Luef",
      "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
      "trustScroe": 0.8
    },
    "longitude": -0.12960399687290192,
    "latitude": 51.52512741088867,
    "timeMillis": 1457769351328
  }
]
```

##### /transaction
Method: POST

Consumes: application/json - example:
```json
{
    "userId":"8a9ad0325369d178015369d1ad290000",
    "amount": 95.55,
    "trustScoreThreshold": 60
}
```
Produces: application/json - example:
```json
{
  "message": "Transaction with id: d24f5b8d-0ab3-412d-8d8f-bdf1b0aa1543 created!"
}
```

##### /transaction/{transactionId}/accept
Method: POST

Consumes: application/json - example:
```json
{
    "userId":"8a9ad0325369516e0153695cb86e0005",
}
```
Produces: application/json - example:
```json
{
  "message": "Transaction accepted!"
}
```

##### /transaction/{transactionId}/decline
Method: POST

Consumes: application/json - example:
```json
{
    "userId":"8a9ad0325369516e0153695cb86e0005",
}
```
Produces: HttpStatus.OK

##### /transaction/{transactionId}/submit
Method: POST

Consumes: application/json - example:
```json
{
    "userId":"8a9ad0325369516e0153695cb86e0005",
}
```
Produces: application/json - example:
```json
{
  "message": "The Transaction is completed!"
}
```

