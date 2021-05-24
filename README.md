# TbtkMap
![Java CI](https://github.com/eneskacan/TbtkMap/actions/workflows/java.yml/badge.svg)
![Node.js CI](https://github.com/eneskacan/TbtkMap/actions/workflows/node.yml/badge.svg)
![Deploy App to Azure](https://github.com/eneskacan/TbtkMap/actions/workflows/deploy.yml/badge.svg)

A simple web application that allows users to save locations on the world map. See a working demo [here](https://tbtkmap.azurewebsites.net/).

## Database Connection

1. #### Using Local Database
- PostgreSQL with PostGIS is need to be running on `localhost:5432`.
- Create a database named `tbtk` (username: `postgres` & password: `postgres`).
- Create required tables by using [`Table-Scripts.sql`](Database/Table-Scripts.sql).


## Installation

1. #### Using Docker
The easiest way to run this application is via Docker. To install docker, see [docs.docker.com](docs.docker.com). Once you have Docker installed and [working](https://docs.docker.com/get-started/#test-docker-installation), you can easily pull and start the Docker image by running the following from a command prompt or terminal.
```
docker pull eneskacan/tbtk-map
docker run -p 3333:8080 eneskacan/tbtk-map
```


## API Docs

See the [API documentation page](https://tbtkmap.azurewebsites.net/swagger-ui/#/).