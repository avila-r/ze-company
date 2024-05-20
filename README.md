> [!NOTE]
> Although the repository is named as 'Zé Company', the application was developed just for educational purposes to solve the challenge proposed [here](#https://github.com/ab-inbev-ze-company/ze-code-challenges/blob/master/backend.md) and is not intended for commercial use.

# 🌱 Zé Company, a RESTful API built-in Spring Boot.

'Zé Company' is a platform where you can insert partners and list them with non-GIS implementation.

## Features

* Spring-Boot Application
* Spring Data JPA for data persistence
* PostgreSQL as relational database
* JUnit & Mockito for unit testing

## Summary

- [Requirements](#requirements)
- [Testing](#testing)

## Requirements
The application needs a database that you can run as a container using **Docker Engine** (for Linux) or **Docker Desktop** (for MacOS & Windows)

## Testing
Make sure the Docker are locally configured, then you can up docker compose, that will config automatically your [database's Dockerfile](#https://github.com/avila-r/ze-company/blob/main/database/Dockerfile).

### Clone Repository
```bash
$ git clone https://github.com/avila-r/ze-company && cd ze-company
```

### Run Docker Compose

```bash
$ docker compose up
```

Then your local machine will build database image, run initial schema and host at port `5432`. Configure the port by changing in __docker-compose.yml__, then modify __application.properties__ at Spring application.

### Partner endpoints
At `/partner` endpoint, you'll be able to POST to insert partner and GET to list partners. You can also get partner by id at `/partner/{id}` endpoint.

1. The `address` field follows the `GeoJSON Point` format (https://en.wikipedia.org/wiki/GeoJSON);
2. The `coverageArea` field follows the `GeoJSON MultiPolygon` format (https://en.wikipedia.org/wiki/GeoJSON);
3. The `document` is a unique field;
4. The `id` is unique field;

**Insert partner**
```json
{
  "id": 1, 
  "tradingName": "Adega da Cerveja - Pinheiros",
  "ownerName": "Zé da Silva",
  "document": "1432132123891/0001",
  "coverageArea": { 
    "type": "MultiPolygon", 
    "coordinates": [
      [[[30, 20], [45, 40], [10, 40], [30, 20]]], 
      [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]
    ]
  },
  "address": { 
    "type": "Point",
    "coordinates": [-46.57421, -21.785741]
  }
}
```