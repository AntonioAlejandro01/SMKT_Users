# SMKT_Users

Service to manage products in SmartKitchen App

[![Build Dev](https://github.com/AntonioAlejandro01/SMKT_Users/actions/workflows/buildDevVersion.yml/badge.svg?branch=develop)](https://github.com/AntonioAlejandro01/SMKT_Users/actions/workflows/buildDevVersion.yml) [![Build Snapshot](https://github.com/AntonioAlejandro01/SMKT_Users/actions/workflows/BuildSnapshot.yml/badge.svg?branch=main)](https://github.com/AntonioAlejandro01/SMKT_Users/actions/workflows/buildDevVersion.yml) [![Build Release](https://github.com/AntonioAlejandro01/SMKT_Users/actions/workflows/BuildRelease.yml/badge.svg?branch=main)](https://github.com/AntonioAlejandro01/SMKT_Users/actions/workflows/buildDevVersion.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AntonioAlejandro01_SMKT_Users&metric=alert_status)](https://sonarcloud.io/dashboard?id=AntonioAlejandro01_SMKT_Users) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=AntonioAlejandro01_SMKT_Users&metric=coverage)](https://sonarcloud.io/dashboard?id=AntonioAlejandro01_SMKT_Users)

## Use With Docker

Use this Service with Docker as Docker container. The Repo have 3 types of images.

### Types

- **Stable**: this are the images that in her tag is a specific version ex.: `antonioalejandro01/smkt-users:vX.X.X`. the last tag version have latest tag.

```bash
    docker pull antonioalejandro01/smkt-users:v1.0.0
    # The last stable version
    docker pull antonioalejandro01/smkt-users:latest
```

- **Snapshot**: this are the images that in her tag is snapshot ex.: `antonioalejandro01/smkt-users:snapshot`

```bash
    docker pull antonioalejandro01/smkt-users:snapshot
```

- **Dev**: this image is only for developers and in her tag have dev `antonioalejandro01/smkt-users:dev`

```bash
    docker pull antonioalejandro01/smkt-users:dev
```

### Environment variables for Docker image

<table align="center" width="100%" style="margin:1em;">
<thead>
    <tr>
        <th>Name</th>
        <th>Default Value</th>
        <th>Description</th>
    </tr>
</thead>
<tbody>
    <tr>
        <td>PORT</td>
        <td>4060</td>
        <td>Micro service port</td>
    </tr>
    <tr>
        <td>EUREKA_URL</td>
        <td>http://smkt-eureka:8761/eureka</td>
        <td>The url where the smkt-eureka be</td>
    </tr>
    <tr>
        <td>SQL_LEVEL</td>
        <td>INFO</td>
        <td>Log level for all log relational for sql. <i>Recommend only change for development</i></td>
    </tr>
    <tr>
        <td>DATA_SOURCE_URL</td>
        <td>jdbc:mysql://smkt-mysql:3306/SMKT_USERS</td>
        <td>URL connection</td>
    </tr>
    <tr>
        <td>DATA_SOURCE_USER</td>
        <td>smkt</td>
        <td>User for database</td>
    </tr>
    <tr>
        <td>DATA_SOURCE_PASSWORD</td>
        <td>root</td>
        <td>Password for database</td>
    </tr>
    <tr>
        <td>DEFAULT_ROLE_ID</td>
        <td>3</td>
        <td>Role that new users will have it</td>
    </tr>
    <tr>
        <td>OAUTH_ID</td>
        <td>smkt-oauth</td>
        <td>Id that service <a>smkt-oauth</a> have it in <a href="http://github.com/antonioAlejandro01/SMKT_Eureka">smkt-eureka</a></td>
    </tr>
    <tr>
        <td>OAUTH_SEARCH_USER_SECRET_SHA256</td>
        <td>58c4581b7d7f9ab295ac3a273d15ad77af90d429f986dbfe82ca3241d9ef3dbb</td>
        <td>Password for endpoint that smkt-oauth use to verify user</td>
      </tr>
</tbody>
</table>

#### Docker command

```bash
    docker run -d -p4060:4060 -ePORT=4060 -eEUREKA_URL=http://127.0.0.1:8761/eureka -eDATA_SOURCE_URL=jdbc:mysql://localhost:3306/SMKT_USERS -eDATA_SOURCE_USER=smkt -eDATA_SOURCE_PASSWORD=root -t antonioalejandro01/smkt-users:latest
```

## Use in Docker Compose

```yaml
users:
  image: antonioalejandro01/smkt-users:latest
  container_name: smkt-users
  environment:
    PORT: 4060
    EUREKA_URL: http://127.0.0.1:8761/eureka
    DATA_SOURCE_URL: jdbc:mysql://smkt-mysql:3306/SMKT_USERS
    DATA_SOURCE_USER: smkt
    DATA_SOURCE_PASSWORD: Smkt@123
  expose:
    - "4060"
  ports:
    - "4060:4060"
mysqlDB:
  image: mysql:latest
  container_name: smkt-mysql
  restart: always
  environment:
    MYSQL_DATABASE: SMKT_USERS
    MYSQL_USER: smkt
    MYSQL_PASSWORD: Smkt@123
    MYSQL_ROOT_PASSWORD: password
```
