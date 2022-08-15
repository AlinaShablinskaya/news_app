FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD /build/libs/news_app_service-0.0.1-SNAPSHOT.jar news_app_service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/prod/./urandom", "-jar", "news_app_service.jar"]
