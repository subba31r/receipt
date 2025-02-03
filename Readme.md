# Receipt Processor API

This project is a RESTful web service built with Java and Spring Boot that processes receipts and calculates points based on predefined rules.

---

## Features

- Accepts receipt data and generates a unique receipt ID.
- Retrieves points earned for a given receipt ID based on predefined rules.
- In-memory storage (No database required).
- Dockerized setup for easy deployment.

---

## Technologies Used

- Java 17
- Spring Boot
- Maven
- Docker

---

## API Endpoints

1. Process a Receipt
Endpoint: POST /receipts/process
Request Body (JSON):
```bash
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    { "shortDescription": "Mountain Dew 12PK", "price": "6.49" },
    { "shortDescription": "Emils Cheese Pizza", "price": "12.25" }
  ],
  "total": "35.35"
}
```

Response (JSON):
```bash
{ "id": "7fb1377b-b223-49d9-a31a-5a02701dd310" }
```

2. Get Points for a Receipt
Endpoint: GET /receipts/{id}/points
Response (JSON):
```bash
{ "points": 28 }
```

---

## Installation & Setup
1. Clone the Repository
```bash
git clone https://github.com/subba31r/receipt.git
cd receipt
```

2. Build the Application
```bash
mvn clean package
```

3. Run the Application
```bash
mvn spring-boot:run
```

The service will be available at http://localhost:8080
  
---

## Docker Setup

1. Build Docker Image
```bash
docker build -t receipt .
```

2. Run Docker Container
```bash
docker run -p 8080:8080 --name receipt-container receipt
```

- The API will now be accessible at http://localhost:8080

## Testing the API

You can use either cURL or Postman to test the endpoints
Process a Receipt (POST Request)
```bash
curl -X POST "http://localhost:8080/receipts/process" \
     -H "Content-Type: application/json" \
     -d '{"retailer": "Target", "purchaseDate": "2022-01-01", "purchaseTime": "13:01", "items": [{ "shortDescription": "Mountain Dew 12PK", "price": "6.49" }], "total": "35.35"}'
```

Get Points (GET Request)
```bash
curl -X GET "http://localhost:8080/receipts/{id}/points"
```
Replace {id} with the actual receipt ID returned from the POST request.
