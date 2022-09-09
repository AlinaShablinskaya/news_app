# News
A simple application, which has a functionality for news management system.

## Overview
- [Building](#building)
- [Profiles](#profiles)
- [Ports](#ports)
- [Links](#links)

## Building
### Steps
1. On the command line
```sh
https://github.com/AlinaShablinskaya/news_app.git
```
2. Build all modules 
```sh
./gradlew build
```
3. Start modules in docker
```sh
docker compose up
```
## Profiles
The location of the database can be changed by using the following three profiles:
- prod
- test

Profiles can be changed in application.yml
```sh
spring:
  profiles:
    active: prod
```

## Ports
Application | Port
:-----------|:----
News_config service | 8080 
News_app service    | 8888 

## Links
- News_config
    + [http://localhost:8888](http://localhost:8888)
- News_app
    + [http://localhost:8080/api](http://localhost:8080/api)
- Swagger
    + [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)