@echo off
SET PROJECT_HOME=%PWD%

call mvn -f .\common\pom.xml clean install -DskipTests=true

call mvn -f .\order-service\pom.xml clean package -DskipTests=true docker:build

call mvn -f .\product-service\pom.xml clean package -DskipTests=true docker:build


