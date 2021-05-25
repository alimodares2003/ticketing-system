FROM openjdk:11-jdk-slim
VOLUME /tmp

EXPOSE 8080
ADD build/libs/ticketing-0.0.1-SNAPSHOT.jar ticketing-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ticketing-0.0.1-SNAPSHOT.jar"]
