# bibliocommons
Take home assignment

## Library availability system for ebooks

## Part 1

### A user should be able to checkout an ebook if it’s available.

A `POST` HTTP request needs to be made if the user is trying to checkout the ebook if available.

>• The system should be successful if a user checks out an available ebook.<br/>
• The system should keep track of which copy is checked out to which user. The system should always assign the user the oldest available copy. It should mark the copy as checked out and update its timestamp at every checkout.<br/>

`POST /users/{user-id}/checkouts`

**Checkout an ebook 10001 at 1pm on Feb 1 for one week**

#### Request uri, header and payload 

```
POST /users/78965/checkouts HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    {
      "book": {
        "id": 10001,
        "checkoutDate": "01-02-2020T13:00:00.000"
      },
      "duration": {
        "days": 7
      }
    }
  ]
}
```

#### Response body

```
201 OK
content-type: application/json 

Response-body:
{
  "data":[
    {
      "checkoutId": "90001",
      "user": {
        "id": "78965",
        "currentCheckout", 1,
        "maxCheckout": 10,
        "quotaReached": false
      },
      "checkoutBooks": [
        {
          "id": 10001,
          "copyId": 10,
          "checkoutDate": "01-02-2020T13:00:00.000",
          "dueDate": "08-02-2020T13:00:00.000",
          "lastUpdatedDate": "01-01-2020T13:00:00.000",
          "status": "checked-out"
        }
      ]
    }
  ]
}

```

**Note:** By making 'checkouts' an array we can do multiple checkouts of books at one time.
Intention is being we can checkout multiple ebooks at once rather than calling/making each call of each book. Advantage here is reducing calls and processing all at once.

>• The system should return an error for a user or an ebook that doesn’t exist.<br/>

If user `77896` is not in the system

#### Request

`POST /users/77896/checkouts HTTP`

Ignoring the payload

#### Response

`404 NOT FOUND`

Since user is not in the system, return a 404.

If ebook 10005 is not in the system

#### Request uri, header and payload 

```
POST /users/78965/checkouts HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    {
      "book": {
        "id": 10005,
        "checkoutDate": "01-02-2020T13:00:00.000"
      },
      "duration": {
        "days": 7
      }
    }
  ]
}
```

#### Response

```
422 Unprocessable Entity
content-type: application/json 

Response-body:
{
  "message": "Unprocessable Entity",
  "errors": [
    {
      "resource": "data[0].book",
      "field": "id",
      "message": "book not found in the system",
      "code": "not_found"
    }
  ]
}

```

Give a 422 as unprocessible entity, since the bookIdentifier/ebook does not exist in the system.  


>• The system should return an error if the ebook is checked out to the user already.<br/>

#### Request uri, header and payload 

```
POST /users/78965/checkouts HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    {
      "book": {
        "id": 10001,
        "checkoutDate": "01-02-2020T13:00:00.000"
      },
      "duration": {
        "days": 7
      }
    }
  ]
}
```

#### Response body

```
422 Unprocessable Entity
content-type: application/json 

Response-body:
{
  "message": "Unprocessable Entity",
  "errors": [
    {
      "resource": "data[0].book",
      "field": "id",
      "message": "book is already assigned to user",
      "code": "assigned"
    }
  ]
}

```

If trying to assign the same ebook (being a resource), we raise a 422 to notify it is already assigned to the user.

>• The system should keep track of a user’s checkout quota. A user can only have a maximum of 10 checkouts. The system should return an error if the user is about to exceed their checkout quota.<br/>

**Reaching or reached checkouts quota**

#### Request uri, header and payload 

```
POST /users/78965/checkouts HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    {
      "book": {
        "id": 10001,
        "checkoutDate": "01-02-2020T13:00:00.000"
      },
      "duration": {
        "days": 7
      }
    }
  ]
}
```

#### Response body

```
201 OK
content-type: application/json 

Response-body:
{
  "data":[
    {
      "checkoutId": "90001",
      "user": {
        "id": "78965",
        "currentCheckout", 10,
        "maxCheckout": 10,
        "quotaReached": false
      },
      "checkoutBooks": [
        {
          "id": 10001,
          "copyId": 10,
          "checkoutDate": "01-02-2020T13:00:00.000",
          "dueDate": "08-02-2020T13:00:00.000",
          "lastUpdatedDate": "01-01-2020T13:00:00.000",
          "status": "checked-out"
        }
      ]
    }
  ]
}

```

Still allow to checkout ebooks, but trigger quotaReached to true. So consumer is aware of their quota being filled in.

**What if they reached reached max checkouts, checking out 11th ebook**

#### Request uri, header and payload 

```
POST /users/78965/checkouts HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    {
      "book": {
        "id": 10001,
        "checkoutDate": "01-02-2020T13:00:00.000"
      },
      "duration": {
        "days": 7
      }
    }
  ]
}
```

#### Response body

```
422 Unprocessable Entity
content-type: application/json 

Response-body:
{
  "message": "Unprocessable Entity",
  "errors": [
    {
      "resource": "data[0].book",
      "field": "id",
      "message": "Exceeded max checkout quota",
      "code": "quota_reached"
    }
  ]
}

```

Used 422 response code to notify exceeded max checkouts quota.


>• The system should return an error if the ebook is unavailable.<br/>

If ebook 10005 is unavailable

#### Request uri, header and payload 

```
POST /users/78965/checkouts HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    {
      "book": {
        "id": 10005,
        "checkoutDate": "01-02-2020T13:00:00.000"
      },
      "duration": {
        "days": 7
      }
    }
  ]
}
```

#### Response

```
422 Unprocessable Entity
content-type: application/json 

Response-body:
{
  "message": "Unprocessable Entity",
  "errors": [
    {
      "resource": "data[0].book",
      "field": "id",
      "message": "book is unavailable",
      "code": "unavailable"
    }
  ]
}

```

Give a 422 as unprocessible entity, since the bookIdentifier/ebook is unavailable.

`Note:` There seems a lot of 422 being raised. I want the client to understand the root cause of failure. Clients can parse the error response and get to know the state and the error being raised.<br/>
The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity, and the syntax of the request entity (which is correct, if not 400 (Malformed request) is raised) but was unable to process the payload.


### A user should be able to return their ebook that they have checked out.


>• The system should be successful if a user returns an ebook that they have checked out.
• The system should mark the corresponding checked out copy as available and update its timestamp.
• The system should decrease a user’s checkout quota.

We can pretty much do a `PUT` call here, since it is being updated


`PUT /users/{user-id}/checkouts/{checkout-id}`

**Returns the ebook 10001 at 1pm on Feb 2**

#### Request uri, header and payload 

```
PUT /users/78965/checkouts/90001 HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    "book": {
      "id": 10001,
      "returnDate": "02-02-2020T13:00:00.000"
    }
  ]
}
```

#### Response body

```
201 OK
content-type: application/json 

Response-body:
{
  "data":[
    {
      "checkoutId": "90001",
      "user": {
        "id": "78965",
        "currentCheckout", 0,
        "maxCheckout": 10,
        "quotaReached": false
      },
      "checkoutBooks": [
        {
          "id": 10001,
          "copyId": 10,
          "checkoutDate": null,
          "dueDate": null,
          "lastUpdatedDate": "02-02-2020T13:00:00.000",
          "status": "available"
        }
      ]
    }
  ]
}
```

Updates the User quota and marks the ebook as available.

>• The system should return an error for a user or an ebook that doesn’t exist.

If user 78965 not found, return 404

#### Request 

`PUT /users/78965/checkouts/90001 HTTP`

#### Response

`404 NOT FOUND HTTP`

***
***
## Let to answer the above
***
***
***


>• The system should return an error if the user returns an ebook that they haven’t checked out.

#### Request uri, header and payload 

```
PUT /users/78965/checkouts/90001 HTTP

Headers:
Content-Type: application/json

Request-body:
{
  "data": [
    "book": {
      "id": 10001,
      "returnDate": "02-02-2020T13:00:00.000"
    }
  ]
}
```

#### Response

```
404 NOT FOUND HTTP
```

If there is no checkout for the ebook which checkout is not made for, we can simply put 404 since no relation/checkout was made on the ebook to the user.


### Retrieve the availability of an ebook.

Following through this:

>•The system should return the following required attributes successfully of an ebook: 
 <br/>o Total Copies
 <br/>o Available Copies
 <br/>o Ebook metadata url (this would be a url link to an API to retrieve ebook metadata) <br/>
• The system should return an error if the ebook doesn’t exist.

We need to design a retrieval service for an ebook. It will be a `GET` HTTP request.

Books will be the resource to find, either by book-identifier-number or by keywords. 

Let's design both requests to retrieve ebook(s)

1. Get ebook by book number.

`GET /books/book-identifiers/{book-identifier-number}`

**Search an ebook 10001**

#### Request

`GET /books/book-identifiers/10001 HTTP`

#### Response body

```
200 OK
content-type: application/json 

{
  "data": [
    "book": {
      "title": {
        "book-identifier": "10001",
        "name": "Coding Principles,
        "author": "Jim browning",
        "description": "Describing coding principles."
      },
      "inventory": {
        "total": 40,
        "available": 30,
        "reserved": 5,
        "loaned": 10
      },
      "_links":{
        "metadata":{
          "href":"https://some-library-system/books/book-identifiers/10001/metadata"
        }
      }
    }
  ]
}
```

From the above response we can see, an ebook with title and inventory information is retrieved. And reference to books metadata is also mentioned.


2. Get ebook by keywords.

`GET /books/search?keywords={keywords1}+{keywords2}+....`

**Search an ebook with Coding principles**

#### Request

`GET /books/search?keywords=Coding+Principles HTTP`

#### Response body

```
200 OK
content-type: application/json 

{
  "data": [
    "book": {
      "title": {
        "book-identifier": "10001",
        "name": "Coding Principles",
        "author": "Jim browning",
        "description": "Describing coding principles."
      },
      "inventory": {
        "total": 40,
        "available": 30,
        "reserved": 5,
        "loaned": 10
      },
      "_links":{
        "metadata":{
          "href":"https://some-library-system/books/book-identifiers/10001/metadata"
        }
      }
    },
    "book": {
      "title": {
        "book-identifier": "10023",
        "name": "Architecture and Coding Principles",
        "author": "George fleming",
        "description": "Standard architecture and coding."
      },
      "inventory": {
        "total": 20,
        "available": 20,
        "reserved": 1,
        "loaned": 0
      },
      "_links":{
        "metadata":{
          "href":"https://some-library-system/books/book-identifiers/10023/metadata"
        }
      }
    }
  ]
}
```

**Search an ebook 70007**

#### Request

`GET /books/book-identifiers/70007`

#### Response body

```
404 Not found
```

Instead of an error, return a not found and let the client handle it gracefully.

***

## Part 2

>• Code package and class layout <br/>
• Database entities and their relationships between each other <br/>
• Explanation of your design and why you chose to go with this design. Feel free to document any assumptions you have made. <br/>

### Class diagrams

![ClassflowDIagram](./images/ClassflowDIagram.PNG)

![ClassDiagram](./images/ClassDiagram.PNG)

### Entity-Relation diagrams

![ER](./images/ER.PNG)
