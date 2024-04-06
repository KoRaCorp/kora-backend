FROM azul/zulu-openjdk:17

WORKDIR /app

COPY kora-api/build/libs/kora-api-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "-Dspring.profiles.active=dev", "./kora-api-0.0.1-SNAPSHOT.jar"]
