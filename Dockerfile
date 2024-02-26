FROM openjdk:17
MAINTAINER github.com/ruggeromontesi
COPY target/com.ruggero.bookstorage-1.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]