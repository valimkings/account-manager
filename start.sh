#!/usr/bin/env bash

mvn clean install
docker-compose up -d
mvn spring-boot:run
