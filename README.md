# Solvia TestCase-1

## Technologies used:
* Spring Boot 3.1.2
* Spring Data JPA (Hibernate 6 is the default JPA implementation)
* MySQL 8
* Java 17
* Maven 3
* JUnit 5
* Spring Test using TestRestTemplate
* Docker, [Testcontainers](https://testcontainers.com/) (for Spring integration tests using a MySQL container)

## How to run it

execute this commad to start database
docker run --name c1 -p 3306:3306 -e MYSQL_USER=testcase1 -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=mydb -d mysql:8.1

## Added Features
cache
asenkron
docker
mocking
Spring security
validation


