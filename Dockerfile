FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cloud-gateway.jar
CMD ["java", "-jar", "/cloud-gateway.jar"]