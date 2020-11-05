FROM openjdk:11

WORKDIR /opt/server

COPY ./target/smkt-users-1.0.0.jar ./app.jar

EXPOSE 4060

CMD [ "java", "-jar", "./app.jar" ]
