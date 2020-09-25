#!/bin/sh
mvn clean package && docker build -t com.challenge.movies.api/movies-api .
docker rm -f movies-api || true && docker run -d -p 9080:9080 -p 9443:9443 --name movies-api com.challenge.movies.api/movies-api