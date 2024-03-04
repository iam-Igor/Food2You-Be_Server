# Food2You Back-End Server

The Food2You backend server is an efficient component of the Food2You application that you can find
here -> https://github.com/iam-Igor/food2you, responsible for handling various backend functionalities, including user
authentication, order processing, and communication with external APIs.

Developed using the Spring framework, this server ensures smooth and reliable operation, enabling seamless integration
with the frontend client.

# Key Features:

User Authentication and Authorization: The backend server provides secure endpoints for user registration, login, and
authentication, ensuring that only authorized users can access the application's features.

Order Management: With dedicated endpoints for order creation, retrieval, and status updates, the server facilitates
efficient order processing and management, enhancing the overall user experience.

Integration with External APIs: Leveraging the capabilities of Spring's RESTful API development, the server seamlessly
integrates with external services, such as image upload gateways.

Data Persistence and Storage: Utilizing Spring Data JPA and relational database management systems, the server ensures
reliable data storage and retrieval, supporting essential functionalities such as user profiles, order history, and
restaurant information.

Security Measures: The backend server implements robust security measures, including encryption, input validation, and
access control, to protect sensitive user data and prevent unauthorized access or malicious activities.

# Installation

1- Clone the repository:

bash $ git clone https://github.com/iam-Igor/Food2You-Be_Server.git

# Environment Variables Configuration

In your root folder create a file named env.properties and add it to .gitignore

1- Go on https://cloudinary.com and register, then you will be able to create and obtain an own API key, a unique
Cloudinary name and a unique Cloudinary secret

2- Add the values just created in the env.properties file, see example below:

DB_PASSWORD=yourbdpassword

DB_USERNAME=yourpostgresusername

DB_NAME=thenamechosenforthedatabase

CLOUDINARY_NAME=yourcloudinaryname

CLOUDINARY_API_KEY=yourcloudinaryapikey

CLOUDINARY_SECRET=yourcloudinarysecret

JWT_SECRET=thejwtsecretusedforhashingthepasswordsoftheusersTheLongerTheBetter

PORT_SERVER=yourlocalhostportserver

# Contributing

If you'd like to contribute to the development of the Food2You-Be_Server, follow these steps:

1- Fork the repository.

2- Create a new branch:
bash $ git checkout -b your-branch-name

3- Make your changes and commit them:
bash $ git commit -m 'Description of your changes'

4- Push your changes to your fork:
bash $ git push origin your-branch-name

5- Submit a pull request to the main repository.

6- Do not forget to star the project if you liked it :)

# API Reference

Before every endpoint you must add your localhost http address followed by the port configured in you
application.properties file.

Example: http://localhost:port/endpoint

## Authentication /auth

## Registration

```http
  POST auth/register
```

Here you can register with your informations

#### Body

```http
  {
    "name": "String",
    "surname": "String",
    "email": "String",
    "username": "String",
    "password": "String", (min. size 6 characters)
    "address": "String"
}
```

#### Response

```http
  {
  "id": number
  }
```

## Login

```http
  POST auth/login
```

Here you can login with email and password used in the registration process

#### Body

```http
  {
    "email": "String",
    "password": "String" 
}
```

#### Response

The token you will use to make all the api calls requesting Authentication

```http
  {
  "token": String
  }
```

# Users /users

## Get an user by its id

```http
  GET users/id
```

Here you can get an user by its id, this method is allowed only for users with ADMIN privileges

| Parameter | Type     | Description               |
|:----------|:---------|:--------------------------|
| `id`      | `number` | **Required**. The user id |

#### Response

```http
  {
    "id": number,
    "name": "String",
    "lastname": "String",
    "username": "String",
    "email": "String",
    "address": "String",
    "creditCard": {
        "id": number,
        "fullName": "String",
        "cardNumber": number,
        "cvv": number,
        "expiringDate": "String"
    },
    "avatarUrl": "String",
    "role": "ADMIN || USER",
    "authorities": [
        {
            "authority": "ADMIN || USER"
        }
    ],
    "accountNonExpired": boolean,
    "enabled": boolean
}
```

## Delete an user

```http
  DELETE users/me
```

Here you can delete an user profile based on the token you provided in the headers of the request

```http
  Authentication: "Bearer yourtokenhere"
```

#### Response

```http
 void
```

## Get an user by the token provided in the request

```http
  GET users/me
```

Here you can get an user by its token provided in the headers of the request

#### Response

```http
  {
    "id": number,
    "name": "String",
    "lastname": "String",
    "username": "String",
    "email": "String",
    "address": "String",
    "creditCard": {
        "id": number,
        "fullName": "String",
        "cardNumber": number,
        "cvv": number,
        "expiringDate": "String"
    },
    "avatarUrl": "String",
    "role": "ADMIN || USER",
    "authorities": [
        {
            "authority": "ADMIN || USER"
        }
    ],
    "accountNonExpired": boolean,
    "enabled": boolean
}
```

## Upload profile picture

```http
  PATCH users/me/upload
```

Here you can upload an image and update the user profile avatar picture

#### Body

| Parameter | Type             | Description |
  |:----------|:-----------------|:------------|
| `image`   | `Multipart-file` | Image file  |

Token required

#### Response

```http
 String containing the image uploaded to Cloudinary server
```

## Update user informations

```http
  POST users/me
```

Here you can update all users informations

#### Body

```http
 {
    "name": "String",
    "surname": "String",
    "email": "String",
    "username": "String",
    "password": "String", (min. size 6 characters)
    "address": "String"
}
```

#### Response

```http
  {
    "id": number,
    "name": "String",
    "lastname": "String",
    "username": "String",
    "email": "String",
    "address": "String",
    "creditCard": {
        "id": number,
        "fullName": "String",
        "cardNumber": number,
        "cvv": number,
        "expiringDate": "String"
    },
    "avatarUrl": "String",
    "role": "ADMIN || USER",
    "authorities": [
        {
            "authority": "ADMIN || USER"
        }
    ],
    "accountNonExpired": boolean,
    "enabled": boolean
}
```

Token required

## Get all orders for an user

```http
  GET users/orders/me
```

Here you can get all orders for an user based on the token provided in the request

#### Response

```http
 {
        "id": number,
        "orderTime": "String",
        "orderStatus": "String",
        "paymentAccepted": boolean,
        "totalAmount": number,
        "user": {
          // user informations //
        },
        "productList": [
            {
                "id": number,
                "name": "String",
                "price": number,
                "ingredients": "String",
                "calories": number,
                "description": "String",
                "imageUrl": "String",
                "productType": "String"
            }
        ],
        "restaurant": {
            "id": number,
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String",
            "imageUrl": "String"
        },
        "userPosition": "String",
        "promoCodeUsed": boolean
    }
```

## Get payment info for an user

```http
  POST users/payment/get
```

Here you can get payment info for an user based on the token provided in the request

#### Response

```http
 {
    "id": number,
    "fullName": "String",
    "cardNumber": number,
    "cvv": number,
    "expiringDate": "String"
}
```

Token required

## Order /orders

## Get an order by its id

```http
  GET orders/id
```

Here you can get an order based on its id, only for users with ADMIN privileges

#### Response

```http
  {
        "id": number,
        "orderTime": "String",
        "orderStatus": "String",
        "paymentAccepted": boolean,
        "totalAmount": number,
        "user": {
          // user informations //
        },
        "productList": [
            {
                "id": number,
                "name": "String",
                "price": number,
                "ingredients": "String",
                "calories": number,
                "description": "String",
                "imageUrl": "String",
                "productType": "String"
            }
        ],
        "restaurant": {
            "id": number,
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String",
            "imageUrl": "String"
        },
        "userPosition": "String",
        "promoCodeUsed": boolean
    }
```

*Admin Token required

## Make a new order

```http
  POST orders/new
```

Here you can create a new order, the user is assigned based on the token provided in the request

#### Body

```http
 {
    "productIds": [array of ids of the products chosen (numbers)],
    "restaurantId": number,
    "userAddress": "String",
    "isPromoCodePresent": boolean (auto generated by the code itself) 
    }
```

#### Response

```http
  {
        "id": number,
        "orderTime": "String",
        "orderStatus": "String",
        "paymentAccepted": boolean,
        "totalAmount": number,
        "user": {
          // user informations //
        },
        "productList": [
            {
                "id": number,
                "name": "String",
                "price": number,
                "ingredients": "String",
                "calories": number,
                "description": "String",
                "imageUrl": "String",
                "productType": "String"
            }
        ],
        "restaurant": {
            "id": number,
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String",
            "imageUrl": "String"
        },
        "userPosition": "String",
        "promoCodeUsed": boolean
    }
```

*Token required

## Set an order delivered

```http
  PATCH orders/deliver/id
```

Here you can update the status of an order to "DELIVERED"

| Parameter | Type     | Description                |
|:----------|:---------|:---------------------------|
| `id`      | `number` | **Required**. The order id |

*Token required

## Get an order based on the user token

```http
  GET orders/ptint?order_id=ID
```

Here you can get an order based on its it and the token provided in the request, useful for print an order with no admin
privileges

| Parameter  | Type     | Description                |
|:-----------|:---------|:---------------------------|
| `order_id` | `number` | **Required**. The order id |

*Token required

## Product /products

## Get a list of all products

```http
  GET /products
```

Here you can get a list of all products available in the database

| Parameter | Type     | Description                                    |
|:----------|:---------|:-----------------------------------------------|
| `page`    | `number` | The page number you want to access             |
| `size`    | `number` | The size of the list you want to get           |
| `order`   | `String` | The "order by" parameter of the page requested |

*The parameters shown above are not required, by default the page is 0, the size is 10 and the order is by id

#### Response

```http
  {
    "content": [
        {
            "id": number,
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "imageUrl": "String",
            "productType": "String"
        }
    ],
    "pageable": {
        "pageNumber": number,
        "pageSize": number,
        "sort": {
            "empty": boolean,
            "sorted": boolean,
            "unsorted": boolean
        },
        "offset": number,
        "paged": boolean,
        "unpaged": boolean
    },
    "last": boolean,
    "totalPages": number,
    "totalElements": number,
    "size": number,
    "number": number,
    "first": boolean,
    "sort": {
        "empty": boolean,
        "sorted": boolean,
        "unsorted": boolean
    },
    "numberOfElements": number,
    "empty": boolean
}
```

## Get a list of products for a specific restaurant

```http
  GET /products/id
```

Here you can get a list of all products available in the database

| Parameter | Type     | Description                               |
|:----------|:---------|:------------------------------------------|
| `id`      | `number` | **Required**. The restaurant id requested |

*The parameters shown above are not required, by default the page is 0, the size is 10 and the order is by id

#### Response

```http
  [
    {
           "id": number,
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "imageUrl": "String",
            "productType": "String"

            }, 

            {
            ...other products
            }

    ]
```

## Get a list of products by name or part of it

```http
  GET /products/search
```

Here you can get a list of products based on the name or part of it provided in the url

| Parameter | Type     | Description                                           |
|:----------|:---------|:------------------------------------------------------|
| `page`    | `number` | The page number you want to access                    |
| `size`    | `number` | The size of the list you want to get                  |
| `order`   | `String` | The "order by" parameter of the page requested        |
| `name`    | `String` | **Required**. The name of the product (or part of it) |

*The parameters shown above are not required, by default the page is 0, the size is 10 and the order is by id.

#### Response

```http
  {
    "content": [
        {
            "id": number,
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "imageUrl": "String",
            "productType": "String"
        },

          {
            ...other products
            }
    ],
    "pageable": {
        "pageNumber": number,
        "pageSize": number,
        "sort": {
            "empty": boolean,
            "sorted": boolean,
            "unsorted": boolean
        },
        "offset": number,
        "paged": boolean,
        "unpaged": boolean
    },
    "last": boolean,
    "totalPages": number,
    "totalElements": number,
    "size": number,
    "number": number,
    "first": boolean,
    "sort": {
        "empty": boolean,
        "sorted": boolean,
        "unsorted": boolean
    },
    "numberOfElements": number,
    "empty": boolean
}
```

# Restaurant /restaurants

## Get a list of all restaurants

```http
  GET /restaurants
```

Here you can get a list of all restaurants available in the database

#### Response

```http
  [
    {
        "id": number,
        "name": "String",
        "streetAddress": "String",
        "city": "String",
        "longitude": number,
        "latitude": number,
        "summary": "String",
        "imageUrl": "String"
    },
      {
   ...other restaurants
}
    ]
```

## Get a restaurant by its id

```http
  GET /restaurants/id
```

Here you can get a restaurant based on the id provided in the url

#### Response

```http
  
    {
        "id": number,
        "name": "String",
        "streetAddress": "String",
        "city": "String",
        "longitude": number,
        "latitude": number,
        "summary": "String",
        "imageUrl": "String"
    }
    
```

## Get a list of restaurants by city

```http
  GET /restaurants/city/cityName
```

Here you can get a list of restaurants based on the city name provided in the url

| Parameter | Type     | Description                                     |
|:----------|:---------|:------------------------------------------------|
| `city`    | `String` | **Required**. The city chosen for getting datas |

#### Response

```http
  
  [
    {
        "id": number,
        "name": "String",
        "streetAddress": "String",
        "city": "String",
        "longitude": number,
        "latitude": number,
        "summary": "String",
        "imageUrl": "String"
    },
      {
   ...other restaurants
}
    ]
    
```

## Get a list of products for a restaurant

```http
  GET /restaurants/id/products
```

Here you can get a list of products based on the id of the restaurant provided in the url

| Parameter | Type     | Description                            |
|:----------|:---------|:---------------------------------------|
| `id`      | `number` | **Required**. The id of the restaurant |

#### Response

```http
  [
    {
           "id": number,
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "imageUrl": "String",
            "productType": "String"

            }, 

            {
            ...other products
            }

    ]
    
```

## Get a list of restaurants by city name and summary

```http
  GET /restaurants/search
```

Here you can get a list of products based on city name and summary provided in the url

| Parameter | Type     | Description                                 |
|:----------|:---------|:--------------------------------------------|
| `city`    | `String` | **Required**. The city of chosen            |
| `summary` | `String` | **Required**. The summary of the restaurant |

*The summary can only be on of the following:

PIZZA, FAST_FOOD, SUSHI, PASTA, KEBAB

Either can be on lower case too

#### Response

```http
  [
    {
           "id": number,
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "imageUrl": "String",
            "productType": "String"

            }, 

            {
            ...other products
            }

    ]
    
```

# Payment /payment

## Add new credit card for an user

```http
  POST /payment/new
```

Here you can add a new credit card for an user based on the token provided in the request

#### Body

```http
  
    {
        "fullName": number,
        "cardNumber": "number",
        "cvv": "number" (max value is 3 characters),
        "expiringDate": "String"
    }
    
```

#### Response

```http
   {
        "id": number,
        "fullName": "String",
        "cardNumber": number,
        "cvv": number,
        "expiringDate": "String"
    }
```

*Token required

## Delete a credit card for an user

```http
  DELETE /payment/id
```

Here you can delete a credit card for an user based on the token provided in the request

| Parameter | Type     | Description                                       |
|:----------|:---------|:--------------------------------------------------|
| `id`      | `number` | **Required**. The id of the credit card to delete |

#### Response

```http
  void
```

*Token required

# Review /reviews

## Add new review for an user

```http
  POST /reviews/new
```

Here you can add a new review for an user based on the token provided in the request

#### Body

```http
  
    {
        "message": String,
        "rating": "number"
    }
    
```

#### Response

```http
   {
        "id": number,
        "message": String,
        "rating": "number",
        "username":"String"
    }
```

*Token required

## Get all reviews

```http
  GET /reviews/all
```

Here you can get all reviews available in the database

#### Response

```http
[
    {
        "id": number,
        "message": String,
        "rating": "number",
        "username":"String"
    },

    {
    ...other reviews
    }
]
  
```

## Get a single review by its id

```http
  GET /reviews/id
```

Here you can get a single review based on its id

| Parameter | Type     | Description                        |
|:----------|:---------|:-----------------------------------|
| `id`      | `number` | **Required**. The id of the review |

#### Response

```http

    {
        "id": number,
        "message": String,
        "rating": "number",
        "username":"String"
    }

```

# Backoffice /admin

*All the following methods will require a token with ADMIN privileges

## Add new restaurant

```http
  POST /admin/restaurant/new
```

Here you can add a new restaurant

#### Body

```http
  
    {
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String"   
    }
    
```

#### Response

```http
        {
            "id": number,
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String"  
        }
```

*Admin Token required

## Add products in a restaurant

```http
  PATCH /admin/restaurant/add
```

Here you can add products for a restaurant

#### Body

```http
  
    {
            "productIds": [array of ids of produts already created in the database],
            "restaurantId": "number",
    }
    
```

#### Response

```http
        {
            "id": number,
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String"  
        }
```

*Admin Token required

## Update restaurant informations

```http
  PATCH /admin/restaurant/update/id
```

Here you can update restaurant informations based on the id provided in the url

| Parameter | Type     | Description                                   |
|:----------|:---------|:----------------------------------------------|
| `id`      | `number` | **Required**. The id of restaurants to update |

#### Body

```http
  
      {
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String"   
    }
    
```

#### Response

```http
        {
            "id": number,
            "name": "String",
            "streetAddress": "String",
            "city": "String",
            "longitude": number,
            "latitude": number,
            "summary": "String"  
        }
```

*Admin Token required

## Upload restaurant image

```http
  PATCH /admin/restaurant/upload/id
```

Here you can upload an image for a restaurant based on the id provided in the url

#### Body

| Parameter | Type             | Description |
  |:----------|:-----------------|:------------|
| `image`   | `Multipart-file` | Image file  |

#### Response

```http
       String containing the image uploaded to Cloudinary server
```

*Admin Token required

## Delete a restaurant

```http
  DELETE /admin/restaurant/id
```

Here you can delete a restaurant based on the id provided in the url

| Parameter | Type     | Description                                   |
|:----------|:---------|:----------------------------------------------|
| `id`      | `number` | **Required**. The id of restaurants to delete |

#### Response

```http
 void
```

*Admin Token required

## Add new products

```http
  POST /admin/products/new
```

Here you can add a new product and automatically assign it to a restaurants already persisted in the database based on
the id provided in the body of the request

#### Body

```http
  
      {
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "productType": "String",
            "restaurantId": number
    }
    
```

#### Response

```http

{
            "id": number
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "productType": "String",
            "restaurantId": number
    }
```

*Admin Token required

## Upload product image

```http
  PATCH /admin/product/upload/id
```

Here you can upload an image for a product based on the id provided in the url

#### Body

| Parameter | Type             | Description |
  |:----------|:-----------------|:------------|
| `image`   | `Multipart-file` | Image file  |

#### Response

```http
       String containing the image uploaded to Cloudinary server
```

*Admin Token required

## Update product informations

```http
  PATCH /admin/products/update/id
```

Here you can update product informations based on the id provided in the url

| Parameter | Type     | Description                               |
|:----------|:---------|:------------------------------------------|
| `id`      | `number` | **Required**. The id of product to update |

#### Body

```http
  
      {
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "productType": "String",
            "restaurantId": number
    }
    
```

#### Response

```http
        {
            "id": number,
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "productType": "String",
            "restaurantId": number
        }
```

*Admin Token required

## Delete a product

```http
  DELETE /admin/products/id
```

Here you can delete a product based on the id provided in the url

| Parameter | Type     | Description                               |
|:----------|:---------|:------------------------------------------|
| `id`      | `number` | **Required**. The id of product to delete |

#### Response

```http
 void
```

*Admin Token required

## Get a list of all orders

```http
  GET /admin/orders
```

Here you can get a list of all orders available on the database

| Parameter | Type     | Description                                    |
|:----------|:---------|:-----------------------------------------------|
| `page`    | `number` | The page number you want to access             |
| `size`    | `number` | The size of the list you want to get           |
| `order`   | `String` | The "order by" parameter of the page requested |

*The parameters shown above are not required, by default the page is 0, the size is 10 and the order is by id

#### Response

```http
 {
    "content": [
        {
            "id": number,
            "orderTime": "String",
            "orderStatus": "String",
            "paymentAccepted": boolean,
            "totalAmount": number,
            "user": {
                ...user informations
            },
            "productList": [
    {
            "id": number,
            "name": "String",
            "price": number,
            "ingredients": "String",
            "calories": number,
            "description": "String",
            "productType": "String",
           
    } , {
    
    ...other products if presents
    }
        ],
            "restaurant": 
            {
              ...restaurants informations
            },
            "userPosition": "String",
            "promoCodeUsed": boolean
        }, 

        {
        ...other orders
        }

        ],

         "pageable": {
        "pageNumber": number,
        "pageSize": number,
        "sort": {
            "empty": boolean,
            "sorted": boolean,
            "unsorted": boolean
        },
        "offset": number,
        "paged": boolean,
        "unpaged": boolean
    },
    "last": boolean,
    "totalPages": number,
    "totalElements": number,
    "size": number,
    "number": number,
    "first": boolean,
    "sort": {
        "empty": boolean,
        "sorted": boolean,
        "unsorted": boolean
    },
    "numberOfElements": number,
    "empty": boolean

 }       
```

*Admin Token required

## Get all users

```http
  GET /admin/users
```

Here you can get a list of users registered on the database

| Parameter | Type     | Description                                    |
|:----------|:---------|:-----------------------------------------------|
| `page`    | `number` | The page number you want to access             |
| `size`    | `number` | The size of the list you want to get           |
| `order`   | `String` | The "order by" parameter of the page requested |

*The parameters shown above are not required, by default the page is 0, the size is 10 and the order is by id

#### Response

```http
       {
    "content": [
        {
            "id": number,
            "name": "String",
            "lastname": "String",
            "username": "String",
            "email": "String",
            "address": "String",
            "creditCard": {
                "id": number,
                "fullName": "String",
                "cardNumber": number,
                "cvv": number,
                "expiringDate": "String"
            },
            "avatarUrl": "String,
            "role": "String",
            "authorities": [
                {
                    "authority": "String"
                }
            ],
            "accountNonExpired": boolean,
            "enabled": boolean
        },
        {
           ...other users
        },
      
    ],
    "pageable": {
        "pageNumber": number,
        "pageSize": number,
        "sort": {
            "empty": boolean,
            "sorted": boolean,
            "unsorted": boolean
        },
        "offset": number,
        "paged": boolean,
        "unpaged": boolean
    },
    "last": boolean,
    "totalPages": number,
    "totalElements": number,
    "size": number,
    "number": number,
    "first": boolean,
    "sort": {
        "empty": boolean,
        "sorted": boolean,
        "unsorted": boolean
    },
    "numberOfElements": number,
    "empty": boolean
}
```

*Admin Token required

# Errors

The entire API uses the following error codes:

- `400 Bad Request`: The request was malformed or missing required parameters.
- `401 Unauthorized`: The token provided is no longer valid.
- `403 Forbidden`: The page requested is not accessible due to token role not valid.
- `404 Not Found`: The requested resource was not found.
- `500 Internal Server Error`: An unexpected error occurred on the server.