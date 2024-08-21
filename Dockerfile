FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ARG JAR_FILE=build/libs/login.jar
EXPOSE 7006
ENTRYPOINT ["java","-jar","/login.jar"]
