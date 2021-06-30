# spring-rabbitmq-event-module

# Running Locally
The only dependencies for running this example are:

- [Docker Compose][https://www.docker.com/)
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [httpie](https://github.com/httpie/httpie)

```sh
$ git clone https://github.com/mozammal/spring-rabbitmq-event-module.git
$ cd spring-rabbitmq-event-module
$ mvn clean package
$ mvn jib:dockerBuild
$ docker-compose up -d 
```

You can test the application by using the following command:

```sh
$ http  POST localhost:8080/events/welcome-email/ userId=123 firstname=mozammal lastname=hossain email=mozammal@gmail.com
```

If you want to tear-down the application, use the command written below:

```sh
$ docker-compose down
```
