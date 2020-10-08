# SPRING BOOT MONGO PRODUCT TEST

### Description
Simple SpringBoot application which manages products and orders

### Build guide

- To build: ``mvn package``
- To run: ``java -jar productapp-0.0.1-SNAPSHOT.jar``

### Requirements
- Mongodb (configurable in ``src/main/resources/application.properties``)
- Port 8080 available (configurable in ``src/main/resources/application.properties``)

### Supported Operations:

The api spec is available at [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

* Create a new product
* Retrieve a list of all products
* Update a product
* Delete a product
* Placing an order
* Retrieving all orders (optionally using dates)

### What's missing:
* Error handling
* Fields validation
* Extensive test-suite
