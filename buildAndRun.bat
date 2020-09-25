@echo off
call mvn clean package
call docker build -t com.challenge.movies.api/movies-api .
call docker rm -f movies-api
call docker run -d -p 9080:9080 -p 9443:9443 --name movies-api com.challenge.movies.api/movies-api