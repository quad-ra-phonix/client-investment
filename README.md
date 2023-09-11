# client-investment
Client Investment assessment
a REST API that allows for creating, updating and searching for a client, products and withdrawal process

## Tech Stack
```
gradle
java 11
springboot
postgresql
```

## Usage

```
using gradle build to compile to jar, then run jar.
You can import gralde project to IDE.

# database setup
start postgres database.
flyway scripts will create the schema and insert initial data at application startup.

# Application Security
Rest api endpoints are secured with basic authentication
- user: user
- password: password

# To view the swagger documentation
while project is running, open on browser: http://localhost:8080/swagger-ui/index.html#

# Postman scripts
I have included the postman scripts inside the 'postmanScript' folder. Import to local instance of postman and run the scripts.

```
