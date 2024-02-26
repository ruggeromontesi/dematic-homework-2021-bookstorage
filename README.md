# bookstorage
Project for the interview with Dematic in March 2021

# how to set up and run
 * Pull the code typing in command line:
     git clone https://github.com/ruggeromontesi/dematic-homework-2021-bookstorage.git

-Build the code typing : 

mvn package

-Run the application: 

     * java -jar ./target com.ruggero.bookstorage-1.0.1.jar
     
     OR
     
     * mvn spring-boot:run

# How to create a book:
curl -X 'POST'  'http://localhost:8080/books/create' -H "Content-Type: application/json"   -d '{
 "title": "titleA",
 "author": "autorA",
 "barcode" : "10001",
 "quantity" : 5,
 "price": 40.5
 }'

 # How to retrieve a book by barcode
 curl -X 'GET' 'http://localhost:8080/books/10001'

 # How to dockerize
 https://www.baeldung.com/dockerizing-spring-boot-application
 https://www.baeldung.com/java-dockerize-app

 * docker build --tag=<YOUR_TAG>:latest .
 * docker run -p<PORT_NOT_CONTAINERIZED>:<PORT_CONTAINRERIZED>:latest

 * docker stop <CONTAINER_NAME>
 *docker start <CONTAINER_NAME>

 
