FROM maven:3-openjdk-11 as Builder

WORKDIR /build

COPY pom.xml .

RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r target/

COPY src ./src

RUN mvn clean package  -Dmaven.test.skip

RUN mv ./target/smkt-users.jar /app.jar

####### JAVA 11 jre STAGE ########
FROM openjdk:11-jre-slim

WORKDIR /opt/server

COPY --from=Builder /app.jar  ./app.jar

##### Default enviroment variables #####
ENV PORT=4060
ENV DATASOURCE_URL=jdbc:mysql://smkt-mysql:3306/SMKT_USERS
ENV DATASOURCE_USER=root
ENV DATASOURCE_PASSWORD=password
ENV EUREKA_URL=http://127.0.0.1:8761/eureka
ENV SQL_LEVEL=INFO
ENV DEFAULT_ROLE_ID=3
ENV OAUTH_ID=smkt-oauth
ENV OAUTH_SEARCH_USER_SECRET_SHA256=58c4581b7d7f9ab295ac3a273d15ad77af90d429f986dbfe82ca3241d9ef3dbb
#########################################

EXPOSE ${PORT}

CMD java -jar app.jar  --spring.datasource.url="${DATASOURCE_URL}"  --spring.datasource.username="${DATASOURCE_USER}"  --spring.datasource.password="${DATASOURCE_PASSWORD}"  --server.port="${PORT}"  --eureka.client.service-url.defaultZone="${EUREKA_URL}" --logging.level.org.hibernate.SQL=${SQL_LEVEL} --default.params.roles.id=${DEFAULT_ROLE_ID} --oauth.secret=${OAUTH_SECRET} --oauth.app-key-secret=${OAUTH_SEARCH_USER_SECRET_SHA256}  --spring.jpa.hibernate.ddl-auto="create"

