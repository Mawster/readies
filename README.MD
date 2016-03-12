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
    "email":"Mario.luef@catalysts.cc",
    "photoUrl": "https://scontent-ams3-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/10246592_1031032256955974_3392747299802861027_n.jpg?oh=189227d2c7bb9f9b0304718352728035&oe=57510A77",
    "trustScore": 0.43
}
```
Produces: application/json - example:
```json
{
  "userId": "8a9ad0325369516e0153695a515f0003"
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
  "userId": "8a9ad0325369516e015369518a320000"
}
```

##### /location
Method: GET

Produces: application/json - example:
```json
[
  {
    "userId": "8a9ad0325369516e0153695cb86e0005",
    "longitude": 51.5311279296875,
    "latitude": -0.12360399961471558,
    "timeMillis": 1457761603700
  },
  {
    "userId": "8a9ad0325369516e0153695a515f0003",
    "longitude": 51.5311279296875,
    "latitude": -0.12360399961471558,
    "timeMillis": 1457761560249
  }
]
```

##### /transaction
Method: POST

Consumes: application/json - example:
```json
{
    "userId":"8a9ad0325369516e0153695cb86e0005",
    "amount": 125.23
}
```
Produces: application/json - example:
```json
{
  "message": "Transaction with id: 55e9a0f1-852d-4ca2-8467-70b54c035c37 created!"
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

