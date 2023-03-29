# let - api

This is the api for the let application. It is made with spring boot, mysql and java.

### Pre-requisites
- Java 17
- Mysql 8.0.26
- Maven 3.8.1

### Before you run:
Copy the application.properties.dev file to application.properties and fill in the missing values, or copy over the
mysql applicaiton.properties file and fill in the missing values. 

You will need certificates for the JWTs. To obtain these do the following:

```shell
cd src/main/resources/certs
# create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

### How to build
```shell
mvn clean spring-boot:run
```

### How to test
```shell
mvn clean test
```
