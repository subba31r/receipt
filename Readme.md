mvn clean package #for building the jar file

docker build -t receipt .

docker run -p 8080:8080 --name receipt-container receipt
