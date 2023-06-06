# Word Counter Library

This project helps to understand how we can add and count the words using parallel processing.

## Prerequisites

- Java 8 or above version
- Maven 3.* above

## Developer Guide

- [WordCountController](https://github.com/vijaynarayanan-it/wordcounter/blob/main/src/main/java/org/synechron/wordcounter/controller/WordCountController.java)
-- This class has two Rest services for adding and counting the given word(s).

- [WordCountServiceImpl](https://github.com/vijaynarayanan-it/wordcounter/blob/main/src/main/java/org/synechron/wordcounter/service/impl/WordCountServiceImpl.java)
-- This class has the business logic for adding and counting the given words using parallelism. ${parallel.thread.count} value is retrieved from VM argument while running the application.

- [AddAndCountWordService](https://github.com/vijaynarayanan-it/wordcounter/blob/main/src/main/java/org/synechron/wordcounter/service/impl/AddAndCountWordService.java)
-- This class provides a thread-safe implementation using ConcurrentHashMap and BlockingQueue. Also it helps to decide how many parallel threads needs to process the given data.

- [WordUtils](https://github.com/vijaynarayanan-it/wordcounter/blob/main/src/main/java/org/synechron/wordcounter/utils/WordUtils.java)
-- This class has static implementations and also mock implementation for Translator API.

## How to run the applications

* There are four applications we need to run one by one.

- **wordcounter-discovery-server**
  --- This is the micro-service discovery server(Eureka Server) will run on http://localhost:8761/

- **wordcounter-api-gateway-server**
  --- API Gateway server which also performs Load Balancing for the registered micro-services. This will run on http://localhost:8765/

- **wordcounter-producer**
  --- Contains the business logic for storing and adding the given word. This service will run on http://localhost:8081/ (Of course we can change the port number while runtime using VM Argument). We need to provide parallel count value using -Dparallel.thread.count VM argument.

- **wordcounter-consumer**
--- Contains the REST services for connecting to producer service (Using Feign Client Implementation - [WordCounterProducerProxy](https://github.com/vijaynarayanan-it/wordcounter/blob/main/wordcounter-consumer/src/main/java/org/synechron/wordcounter/clientproxy/WordCounterProducerProxy.java)) for performing addition and calculating the word count. This service will run on http://localhost:8082/ (Of course we can change the port number while runtime using VM Arguments)

- The microservice routing logic is defined in [application.yml](https://github.com/vijaynarayanan-it/wordcounter/blob/main/wordcounter-api-gateway-server/src/main/resources/application.yml)

## To test the test cases use below URLs in your postman application:

- Test case 1: To add a word (We are connecting to consumer service through spring cloud API Gateway - http://localhost:8765)

```sh
http://localhost:8765/wordcounter-consumer/
```

- Sample input:
```sh
[
        "Apple", "Java", "apple"
]
```

- Test case 2: To get the count of the given word

```sh
http://localhost:8765/wordcounter-consumer/apple
```

- Sample output:

```sh
2
```