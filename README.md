# bibliocommons
Take home assignment

## Library availability system for ebooks

### A user should be able to checkout an ebook if it’s available.

### A user should be able to return their ebook that they have checked out.

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

`https://some-library-system/books/book-identifiers/10001`

#### Response body

```
200 OK
content-type: application/json 

{
  "books": [
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

`https://some-library-system/books/search?keywords=Coding+Principles`

#### Response body

```
200 OK
content-type: application/json 

{
  "books": [
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

`https://some-library-system/books/book-identifiers/70007`

#### Response body

```
404 Not found

```
