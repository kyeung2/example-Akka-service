# example-Akka-service
A simple Akka service to compare with various backend technologies.

Blog post: [comparing execution model of various backend technologies](https://ioflyingnimbus.blogspot.com/2018/08/comparing-execution-model-of-various.html)

A single endpoint which retrieves data from Mongo

`curl -X GET http://localhost:8080/books`

**Stack:**
- Akka HTTP
- Akka Actor
- Scala Mongo Driver
- Circe (Json library)

### Setup Mongo

You can use the embedded Mongo buy uncommenting code in **EmbeddedMongo.scala** and dependency in the build.sbt. Also update **LibraryApp.scala**

`val repo: BookRepository = BookRepository(EmbeddedMongo.bookCollection)`

You can also easily setup a local Mongo running from a local [Docker](https://www.docker.com/docker-mac) image. Pull the [Mongo image](https://hub.docker.com/_/mongo/) and run remembering to use the **-p** option to the port. e.g:

`docker run -p 27017:27017 --name some-mongo mongo`

### Akka features
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
