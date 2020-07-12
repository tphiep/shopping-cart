# shopping-cart
Sample micro services

# System prerequisites
    1. Java 8
    2. Maven 3
    3. Docker (Docker Compose)
# Build
    1. Go to root directory of the project and run './build-all.sh'
    2. Start all services: './stall-all.sh'
    3. Stop all services: './stop-all.sh'
# System 
  >![Preview](https://raw.githubusercontent.com/tphiep/shopping-cart/master/design.png)
# Schema
   ####Product
   >![Preview](https://raw.githubusercontent.com/tphiep/shopping-cart/master/product_schema.jpg)
   ####Order
   >![Preview](https://raw.githubusercontent.com/tphiep/shopping-cart/master/order_schema.jpg)
# Sample Request/Response
   1. Product Service:
      #####Create Product:
      ```shell script
        curl -d '{"info" : {"name": "P1", "price": 100, "quantity": 3}}' -H "Content-Type: application/json" -X POST http://localhost:8082/products
        {"productId":1}
      
        curl -d '{"info" : {"name": "P2", "price": 300, "quantity": 10}}' -H "Content-Type: application/json" -X POST http://localhost:8082/products
        {"productId":3}
      ```
   2. Order Service:
      #####Create Order
      ```shell script
        curl -d '{"customerId" : 1, "items": [{"productId": 1, "amount": 1, "price": 100}, {"productId": 3, "price": 200, "amount": 1}]}' -H "Content-Type: application/json" -X POST http://localhost:8084/orders
        {"orderId":1}
      
        curl -d '{"customerId" : 1, "items": [{"productId": 1, "amount": 100, "price": 100}, {"productId": 3, "price": 200, "amount": 1}]}' -H "Content-Type: application/json" -X POST http://localhost:8084/orders
        {"orderId":3}
      ```
      ######Get Order Status
      ```shell script
          curl -H "Content-Type: application\json" -X GET http://localhost:8084/orders/1
          {"customerId":1,"itemLines":[{"productId":1,"amount":1,"price":100.00},{"productId":3,"amount":1,"price":200.00}],"orderId":1,"state":"APPROVED"}
          
          curl -H "Content-Type: application\json" -X GET http://localhost:8084/orders/3
          {"customerId":1,"itemLines":[{"productId":1,"amount":100,"price":100.00},{"productId":3,"amount":1,"price":200.00}],"orderId":3,"state":"REJECTED","rejectReason":"OUT_OF_STOCK"}
      ```      
     