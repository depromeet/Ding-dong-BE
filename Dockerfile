FROM amazoncorretto:17
ARG JAR_FILE=Api/build/libs/Api.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]