FROM maven:3-openjdk-11 as build

WORKDIR /opt/build

COPY . .

RUN mvn clean compile install

RUN mv ./target/smkt-users-2.0.0.jar /app.jar

FROM openjdk:11

WORKDIR /opt/server

COPY --from=build /app.jar  ./app.jar

ARG port=4060
ARG datasource_url=jdbc:mysql://smkt-mysql:3306/SMKT_USERS?useSSL=false&allowPublicKeyRetrieval=true
ARG datasource_user=root
ARG datasource_password=root
ARG eureka_url=http://smkt-eureka:8761/eureka

ENV PORT ${port}
ENV DATASOURCE_URL ${datasource_url}
ENV DATASOURCE_USER ${datasource_user}
ENV DATASOURCE_PASSWORD ${datasource_password}
ENV EUREKA_URL ${eureka_url}

EXPOSE ${PORT}

CMD java -jar app.jar --spring.datasource.url="${DATASOURCE_URL}" --spring.datasource.username="${DATASOURCE_USER}" --spring.datasource.password="${DATASOURCE_PASSWORD}" --server.port="${PORT}" --eureka.client.service-url.defaultZone="${EUREKA_URL}"

