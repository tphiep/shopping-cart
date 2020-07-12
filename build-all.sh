#!/bin/bash
mvn clean install -f ./common/pom.xml -DskipTests=true

mvn -f ./order-service/pom.xml clean package -DskipTests=true docker:build

mvn -f ./product-service/pom.xml clean package -DskipTests=true docker:build