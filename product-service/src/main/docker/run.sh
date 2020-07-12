#!/bin/sh
echo "********************************************************"
echo "Waiting for the database server to start on port $DATABASE_PORT"
echo "********************************************************"
while ! `nc -z mysql $DATABASE_PORT`; do sleep 3; done
echo "******** Database Server has started "

echo "********************************************************"
echo "Starting Product Service                           "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
     -jar /usr/local/productservice/@project.build.finalName@.jar