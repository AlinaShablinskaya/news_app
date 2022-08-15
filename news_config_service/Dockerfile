FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD /build/libs/news_config_service-0.0.1-SNAPSHOT.jar news_config_service.jar
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "news_config_service.jar"]