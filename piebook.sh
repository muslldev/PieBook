#!/bin/bash

set -a
source .env
set +a
mvn clean package
sudo docker compose up -d
java -jar target/piebook-3.1.4.jar