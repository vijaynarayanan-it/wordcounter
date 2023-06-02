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

## To test the test cases use below URLs in your postman application:

- Test case 1: To add a word

```sh
http://localhost:8080/api/v1/wordcounter/
```

- Sample input:
```sh
[
        "Apple", "Java", "apple"
]
```

- Test case 2: To get the count of the given word

```sh
http://localhost:8080/api/v1/wordcounter/apple
```

- Sample output:

```sh
2
```