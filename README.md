# example-Akka-service
creating a simple Scala/Akka service to compare with different frameworks

A simple  with one endpoint GET /books. 

`curl -X GET http://localhost:8080/books`

###  stack
- Akka HTTP
- Akka Actors
- Scala Mongo Driver
- Fongo (in-memory Mongo)
- Circe (Json library)


###  Akka features
* actor model:
    - Actors react to immutable messages
    - Actors process messages sequentially, no locks
    - message sending: 
        - ! means “fire-and-forget”, e.g. send a message asynchronously and return immediately. Also known as tell.
        - ? sends a message asynchronously and returns a Future representing a possible reply. Also known as ask.
* notes:
    - routing infrastructure/default dispatcher needs to be guarded from blocking calls. Need to use a "bulkheading" dispatcher/thread pool for those.
    - is a toolkit not a framework like Spring Boot, lower level tools. Many opportunities to get things wrong.
    - It's a little odd we are forced to use an "ask" in Akka HTTP, not clear if using Actors with Akka HTTP is a good thing.

### resources
- [Json Akka support](https://github.com/hseeberger/akka-http-json)
- [Bulkheading explanation](https://stackoverflow.com/questions/34641861/akka-http-blocking-in-a-future-blocks-the-server)