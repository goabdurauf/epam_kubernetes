FROM openjdk:11

MAINTAINER Richard Chesterwood "contact@virtualpairprogrammers.com"

WORKDIR /usr/local/bin/

COPY  build/libs/user_service-0.0.1-SNAPSHOT.jar webapp.jar

CMD ["java", "-jar", "webapp.jar"]