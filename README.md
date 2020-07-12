# shopping-cart
Sample micro services

# System system prerequisites
    1. Java 8
    2. Maven 3
    3. Docker (Docker Compose)
# Build
    1. Go to root directory of the project and run './build-all.sh'
    2. Start all services: './stall-all.sh'
    3. Stop all services: './stop-all.sh'
# Sample Request/Response
   1. Product Service:
      Request:
        curl -d '{"info" : {"name": "P1", "price": 100, "quantity": 3}}' -H "Content-Type: application/json" -X POST http://localhost:8082/products
      Response: 
        {"productId":1}
      
   2. Order Service:
      Request:
        curl -d '{"customerId" : 1, "items": [{"productId": 1, "amount": 1, "price": 100}, {"productId": 3, "price": 200, "amount": 1}]}' -H "Content-Type: application/json" -X POST http://localhost:8084/orders
      Response:
        {"orderId":2}
      Get Order by id:
      Request:
        curl -H "Content-Type: application\json" -X GET http://localhost:8084/orders/1
      Response:
        {"customerId":1,"itemLines":[{"productId":1,"amount":1,"price":100.00},{"productId":3,"amount":1,"price":200.00}],"orderId":1,"state":"APPROVED"}
      Request:
        curl -H "Content-Type: application\json" -X GET http://localhost:8084/orders/3
      Response:
        {"customerId":1,"itemLines":[{"productId":1,"amount":100,"price":100.00},{"productId":3,"amount":1,"price":200.00}],"orderId":3,"state":"REJECTED","rejectReason":"OUT_OF_STOCK"}
      
     