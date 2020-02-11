FROM gradle:6.1.1-jdk8 as builder

MAINTAINER Jackson Paiva
RUN mkdir /webflux
WORKDIR /webflux
COPY . .
RUN gradle build --info
RUN ls /webflux/build/libs
VOLUME /root/.gradle


FROM openjdk:8-alpine
MAINTAINER Jackson Paiva
RUN apk update && apk add bash
RUN mkdir -p /opt/webflux
WORKDIR /opt/webflux
COPY --from=builder /webflux/build/libs/webflux-api-0.0.1-SNAPSHOT.jar .
RUN ls
CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"./webflux-api-0.0.1-SNAPSHOT.jar"]
