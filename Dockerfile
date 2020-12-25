FROM maven:3-openjdk-11 as build

WORKDIR /opt/build

COPY . .

RUN mvn clean compile install

RUN mv ./target/smkt-users-1.0.0.jar /app.jar

FROM openjdk:11

WORKDIR /opt/server

COPY --from=build /app.jar  ./app.jar

EXPOSE 4060

CMD [ "java", "-jar", "./app.jar" ]
