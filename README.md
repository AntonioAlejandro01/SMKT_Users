# SMKT_Users

![Build](https://github.com/AntonioAlejandro01/SMKT_Users/workflows/Build/badge.svg?branch=master)
![CI-Develop](https://github.com/AntonioAlejandro01/SMKT_Users/workflows/CI-Develop/badge.svg?branch=development)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=AntonioAlejandro01_SMKT_Users&metric=coverage)](https://sonarcloud.io/dashboard?id=AntonioAlejandro01_SMKT_Users)
## Description

This MS serve users in the database

![X](smktusers.png)

## Endpoints

### Users<sup>\*<sup>

- **/users GET** -> Get all users in DB<sup>1</sup>
- **/users POST** -> Create a new user.<sup>12</sup>
- **/users/{id} PUT** -> Update a user.<sup>13</sup>
- **/users/{id} DELETE** -> Delete a user<sup>4</sup>
- **/users/search?filter=filterValue&value=value GET** -> Search by filter. All fields are mandatory.<sup>1</sup>
  - Filter values:
    - id
    - username
    - email

### Roles

- **/roles GET** -> Get all roles
- **/roles/{name} GET** -> Get al role filter by her name

### Scopes

- **/scopes?roleId=value GET** -> Get scopes from Role that have the same roleId

### Aclarations

<sup>*</sup> The endpoint users need headers authorization except when you call a _/users/search_ and _App-Key_ is present in headers with the correct value.

<sup>1</sup>The Output change depends your scope.If your scope is minimun you only can see usernames. For a Admin scope you not can't see id and password(cyphered password) but if you have superadmin scope you can see all data for users.

<sup>2</sup>The user always will have USER role, if you can change , you should update.Only can create users admin and super admin scopes.

<sup>3</sup>The user that you can update have restrictions depends your scope.If your a normal user, you only can update your data.If your scope is admin you can update normal users and you. The superadmin scope can update all users in DB. **Only can exists one superadmin**

<sup>4</sup> The superadmin user can't be deleted. The admin users can be delete normal users. Normal users can't use this operation. In any case you can delete yourself.

## Usage as docker image

Docker image : `antonioalejandro01/smkt-users:latest`

Download image : ```docker pull antonioalejandro01/smkt-users:latest```

Run image

```bash
docker run 
-rm
--name smkt-users
-p 6060:6060 
-e port=6060 
-e datasource_url="jdbc:mysql://localhost:3306/SMKT_USERS" 
-e datasource_user="root" 
-e datasource_password="root_password"
-e eureka_url="http://localhost:8761/eureka"
antonioalejandro01/smkt-users:latest 
```

### Args

| Name | Description | Example |
| ---- | ----------- | ------- |
| port | The port that ther server is listening | 4060 |
| datasource_url | the url databse | jdbc:mysql://smkt-mysql:3306/SMKT_USERS |
| datasource_user | the databse user | root |
| datasource_password | the password for the user databse | root_password |
| eureka_url | the eureka URL to can connect | http: //smkt-eureka:8761/eureka |

## Others Micro-services

- [SMKT_Eureka](https://github.com/AntonioAlejandro01/SMKT_Eureka)
- [SMKT_Gateway](https://github.com/AntonioAlejandro01/SMKT_Gateway)
- [SMKT_Oauth](https://github.com/AntonioAlejandro01/SMKT_Oauth)
- [SMKT_Files](https://github.com/AntonioAlejandro01/SMKT_Files)
- [SMKT_Kitchen](https://github.com/AntonioAlejandro01/SMKT_Kitchen)

## Frontend

- [SMKTF_Kitchen](https://github.com/AntonioAlejandro01/SMKTF_Kitchen)
