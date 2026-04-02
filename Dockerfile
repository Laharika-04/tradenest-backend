FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 9000

CMD ["sh", "-c", "java -Dserver.port=$PORT -jar target/tradenest-api-1.0.0.jar"]