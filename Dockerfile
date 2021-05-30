FROM maven:3-openjdk-11 as build

WORKDIR /opt/build

COPY . .

RUN mvn clean compile install

RUN mv ./target/smkt-users.jar /app.jar

FROM openjdk:11

WORKDIR /opt/server

COPY --from=build /app.jar  ./app.jar

ARG port=4060
ARG datasource_url=jdbc:mysql://smkt-mysql:3306/SMKT_USERS?useSSL=false&allowPublicKeyRetrieval=true
ARG datasource_user=root
ARG datasource_password=root
ARG eureka_url=http://127.0.0.1:8761/eureka
ARG sql_level=INFO
ARG default_role_id=3
ARG oauth_id=smkt-oauth
ARG oauth_search_user_secret_sha256=58c4581b7d7f9ab295ac3a273d15ad77af90d429f986dbfe82ca3241d9ef3dbb

ENV PORT ${port}
ENV DATASOURCE_URL ${datasource_url}
ENV DATASOURCE_USER ${datasource_user}
ENV DATASOURCE_PASSWORD ${datasource_password}
ENV EUREKA_URL ${eureka_url}
ENV SQL_LEVEL ${sql_level}
ENV DEFAULT_ROLE_ID ${default_role_id}
ENV OAUTH_ID ${oauth_id}
ENV OAUTH_SEARCH_USER_SECRET_SHA256 ${oauth_search_user_secret_sha256}

EXPOSE ${PORT}

CMD java -jar app.jar  --spring.datasource.url="${DATASOURCE_URL}"  --spring.datasource.username="${DATASOURCE_USER}"  --spring.datasource.password="${DATASOURCE_PASSWORD}"  --server.port="${PORT}"  --eureka.client.service-url.defaultZone="${EUREKA_URL}" --logging.level.org.hibernate.SQL=${SQL_LEVEL} --default.params.roles.id=${DEFAULT_ROLE_ID} --oauth.secret=${OAUTH_SECRET} --oauth.app-key-secret=${OAUTH_SEARCH_USER_SECRET_SHA256}  --spring.jpa.hibernate.ddl-auto="create"

