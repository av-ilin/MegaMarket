FROM openjdk:17.0.3
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} market.jar
ENTRYPOINT ["java","-jar","/market.jar"]