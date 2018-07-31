# example-Akka-service
creating a simple Scala/Akka service to compare with different frameworks




## The example service
A simple  with one endpoint GET /books. TODO not added Mongo persistence layer yet

`curl -X GET http://localhost:8080/books`



# project
built using SBT templates:
`sbt -Dsbt.version=0.13.15 new https://github.com/akka/akka-http-scala-seed.g8`


# resources

[Json library Circe](https://github.com/circe/circe)


