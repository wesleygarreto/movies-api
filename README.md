# Project movies-api

This is an api to register movies and schedule exhibitions, is restfull, using Jakarta EE, Microprofile, Open Liberty, Flyway Migrations, Apache Derby database in memory.

Steps to run this project:

1. Start your Docker daemon
2. Execute `./buildAndRun.sh` (Linux/MacOs) or `buildAndRun.bat` (Windows)
3. Wait until Open Liberty is up- and running (e.g. use `docker logs -f CONTAINER_ID`)

To Run in Dev mode:

- mvn liberty:dev

When running the application, open the API documentation at: 

- http://localhost:9080/openapi/ui/
