# Spring Boot JWT authentication with role base Authorization 

This project implements secure Jwt authentication using Rest Api backend using Spring boot .
It supports role-based access control with Admin and user

Users can register,login,view all users and delete user by accessing secured endpoints based on ther roles

## Technology Used

* Java 21
* Spring Boot 3.5
* jjwt for Jwt
* Spring Security
* Maven
* JPA for database connection (MySql)
* postman for testing api

## Database Schema
### User Table
| Column    | Type      | Description                  |
| --------- | --------- | ---------------------------- |
| id        | Long      | Unique identifier            |
| email     | String    | User email, unique, not null |
| password  | String    | Encrypted password           |
| createdAt | Timestamp | User creation timestamp      |

### Role Table
| Column | Type      | Description                          |
| ------ | --------- | ------------------------------------ |
| id     | Long      | Unique identifier                    |
| name   | Enum      | Role name: `ROLE_ADMIN`, `ROLE_USER` |

###user_roles Table (Join Table)
| Column   | Type      | Description               |
| -------- | --------- | ------------------------- |
| user\_id | Long      | Foreign key to User table |
| role\_id | Long      | Foreign key to Role table |

## Security Configuration
* `/auth/**` endpoints are public.

* `/users` endpoint requires ROLE_ADMIN.

* `/users/{id}` endpoint requires ROLE_ADMIN or the user with that ID.

* DELETE `/users/{id}` endpoint requires ROLE_ADMIN.

* JWT filter validates token on every request.

## How to Run

1.Clone the repository:

```
git clone <repository-url>
cd jwtSpring
```

2.Configure your database in `application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```
3.Before starting the app, insert roles into the `role` table (manual Sql):

```sql
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');
```


## API Usage with Postman
###Register User
* POST /auth/signup
```
{
  "email": "user@example.com",
  "password": "password123",
  "role": "ROLE_USER"
}
```
### Login User
* POST /auth/login
```
{
  "email": "user@example.com",
  "password": "password123"
}
```

### access all Users (Admin only)
* GET /users
* response will be json containing all users

### access logged in user using id 
* GET /users/{id}
* response will be json of said user id which is authentiacated


### Delete User (Admin only)
* DELETE /users/{id}
* at success response will be `204 no Content`

## Video Demo for Project
https://youtu.be/7fcL1570LNA

  
