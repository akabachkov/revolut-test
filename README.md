# Sample money transfer application

This is a demo project as a first interview step.

### What was used
- **NO Spring framework** Guice instead
- H2 database
- Jetty Container
- Jersey JAX-RS implementation

###Requirements
- Java 8 Runtime 

### How to run
```sh
gradlew clean build run
```

Application will start a jetty server, H2 database. Next API are available:

- http://localhost:8080/account
- http://localhost:8080/transaction

### Available Services

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| PUT | /account/| create new account | 
| GET | /account/<id> | get account detais | 
| POST | /account/<id> | update account | 
| POST | /transaction/ | perform transaction between accounts | 

### Http Status
- 200 OK: In case of success
- 400 Bad Request: in case of business errors 
- 500 Internal Server Error: unexpected error 

### Sample JSON for User and Account
##### Account : 
```sh
{
    "id": 2,
    "holder": "anton kabachkov",
    "balance": 100
} 
```

#### Transaction:
```sh
{
	"creditAccountId":2,
	"debitAccountId":1,
	"amount":-99
}
```