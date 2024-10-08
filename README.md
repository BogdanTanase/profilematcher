# Profile matcher

## Description
Hi, you are tasked by the business analyst of your team with creating a simple spring service
that will serve as a very simple user profile matcher.

The profile matcher service will have to do the following:

- Based on the unique client id it should extract the current player profile from the
  database of your choice
- Based on the current running campaigns, it should update the current player profile
  (mock an api service that will return the list of current running campaigns)

## The flow should be this:

1. The client will call the profile matcher API here: GET /get_client_config/{player_id}
2. The service will get the full profile from the database and then match the current profile of
   the player with the current campaign settings and will determine if the current player profile
   matches any of the campaign conditions (matchers)
3. If the requirements are met, then the current player profile will be updated (add the
   campaign name to the profile – active campaign) and returned to the user.

### Current campaign data:
```json
{
"game": "mygame",
"name":"mycampaign",
"priority": 10.5,
"matchers": {
"level": {
"min": 1,
"max": 3
},
"has": {
"country": ["US","RO","CA"],
"items": ["item_1"]
},
"does_not_have": {
"items": ["item_4"]
},
},
"start_date": "2022-01-25 00:00:00Z",
"end_date": "2022-02-25 00:00:00Z",

"enabled": true,
"last_updated": "2021-07-13 11:46:58Z"
}
```
### Player profile to be used:
```json
{
"player_id": "97983be2-98b7-11e7-90cf-082e5f28d836",
"credential": "apple_credential",
"created": "2021-01-10 13:37:17Z",
"modified": "2021-01-23 13:37:17Z",
"last_session": "2021-01-23 13:37:17Z",
"total_spent": 400,
"total_refund": 0,
"total_transactions": 5,
"last_purchase": "2021-01-22 13:37:17Z",
"active_campaigns": [],
"devices": [
{
"id": 1,
"model": "apple iphone 11",
"carrier": "vodafone",
"firmware": "123"
}
],
"level": 3,
"xp": 1000,
"total_playtime": 144,
"country": "CA",
"language": "fr",
"birthdate": "2000-01-10 13:37:17Z",
"gender": "male",
"inventory": {
"cash": 123,
"coins": 123,
"item_1": 1,
"item_34": 3,
"item_55": 2
},
"clan": {
"id": "123456",
"name": "Hello world clan"
},
"_customfield": "mycustom"
}
```

## !! Disclaimer !!
Most of the endpoints that where added are just for debugging purposes. 
I assumed that this project would be part of a bigger one where the profile is created and afterward it's populated with data
like clan and items. This means that the clan/items should be present and the full logic of adding them is done in another place.

The requested endpoint is /profile/get_client_config/{player_id} .
This is the reason why the main focus will be on it and unit tests where done only for the methods witch this route touches.
The rest of the implementation is just for fun since it's outside the scope of the task but needed for simulating the real project.

Postman requests: 

- create profile
```json
curl --location 'http://localhost:8080/profile/create_profile' \
--header 'Content-Type: application/json' \
--data '{
  "player_id": "97983be2-98b7-11e7-90cf-082e5f28d836",
  "credential": "apple_credential",
  "created": "2021-01-10 13:37:17Z",
  "modified": "2021-01-23 13:37:17Z",
  "last_session": "2021-01-23 13:37:17Z",
  "total_spent": 400,
  "total_refund": 0,
  "total_transactions": 5,
  "last_purchase": "2021-01-22 13:37:17Z",
  "active_campaigns": [],
  "devices": [
    {
      "id": 1,
      "model": "apple iphone 11",
      "carrier": "vodafone",
      "firmware": "123"
    }
  ],
  "level": 3,
  "xp": 1000,
  "total_playtime": 144,
  "country": "CA",
  "language": "fr",
  "birthdate": "2000-01-10 13:37:17Z",
  "gender": "male",
  "inventory": {
    "cash": 123,
    "coins": 123,
    "item_1": 1,
    "item_34": 3,
    "item_55": 2
  },
  "clan": {
    "id": "123456",
    "name": "Hello world clan"
  },
  "_customfield": "mycustom"
}'
```

- simple get profile
```json
curl --location 'http://localhost:8080/profile/get_profile/97983be2-98b7-11e7-90cf-082e5f28d836' \
--data ''
```
response
```json
{
    "playerId": "97983be2-98b7-11e7-90cf-082e5f28d836",
    "credential": "apple_credential",
    "created": "2021-01-10T13:37:17Z",
    "modified": "2021-01-23T13:37:17Z",
    "lastSession": "2021-01-23T13:37:17Z",
    "totalSpent": 400,
    "totalRefund": 0,
    "totalTransactions": 5,
    "lastPurchase": "2021-01-22T13:37:17Z",
    "activeCampaigns": [
        "mycampaign"
    ],
    "devices": [
        {
            "id": 1,
            "model": "apple iphone 11",
            "carrier": "vodafone",
            "firmware": "123"
        }
    ],
    "level": 3,
    "xp": 1000,
    "totalPlaytime": 144,
    "country": "CA",
    "language": "fr",
    "birthdate": "2000-01-10T13:37:17Z",
    "gender": "male",
    "inventory": {
        "coins": 123,
        "item_1": 1,
        "cash": 123,
        "item_55": 2,
        "item_34": 3
    },
    "clan": {
        "id": "123456",
        "name": "Hello world clan"
    },
    "_customField": null
}
```
- request for the requested endpoint
```json
curl --location 'http://localhost:8080/profile/get_client_config/97983be2-98b7-11e7-90cf-082e5f28d836'
```
response
```json
{
    "playerId": "97983be2-98b7-11e7-90cf-082e5f28d836",
    "credential": "apple_credential",
    "created": "2021-01-10T13:37:17Z",
    "modified": "2021-01-23T13:37:17Z",
    "lastSession": "2021-01-23T13:37:17Z",
    "totalSpent": 400,
    "totalRefund": 0,
    "totalTransactions": 5,
    "lastPurchase": "2021-01-22T13:37:17Z",
    "activeCampaigns": [
        "mycampaign"
    ],
    "devices": [
        {
            "id": 1,
            "model": "apple iphone 11",
            "carrier": "vodafone",
            "firmware": "123"
        }
    ],
    "level": 3,
    "xp": 1000,
    "totalPlaytime": 144,
    "country": "CA",
    "language": "fr",
    "birthdate": "2000-01-10T13:37:17Z",
    "gender": "male",
    "inventory": {
        "coins": 123,
        "item_1": 1,
        "cash": 123,
        "item_55": 2,
        "item_34": 3
    },
    "clan": {
        "id": "123456",
        "name": "Hello world clan"
    },
    "_customField": null
}
```

