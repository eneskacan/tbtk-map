# TbtkMap
![Deploy App to Azure](https://github.com/eneskacan/TbtkMap/actions/workflows/deploy.yml/badge.svg)

A simple web application that allows users to save locations on the world map. See a working demo [here](https://tbtkmap.azurewebsites.net/).

## Database Connection

1. #### Using Local Database
- PostgreSQL with PostGIS is need to be running on `localhost:5432`.
- Create a database named `tbtk` (username: `postgres` & password: `postgres`).
- Create required tables by using [`Table-Scripts.sql`](Database/Table-Scripts.sql).