@echo off
SET PROJECT_HOME=%PWD%

call mvn clean package -DskipTests=true

call mvn -f .\order-service\pom.xml clean package -DskipTests=true docker:build

call mvn -f .\product-service\pom.xml clean package -DskipTests=true docker:build


