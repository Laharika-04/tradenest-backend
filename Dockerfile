# ── Stage 1: Build ────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ── Stage 2: Run ──────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre

WORKDIR /app

RUN mkdir -p /tmp/tradenest-uploads

COPY --from=builder /app/target/tradenest-api-1.0.0.jar app.jar

EXPOSE 9000

CMD ["sh", "-c", "java \
  -Dspring.datasource.url=${SPRING_DATASOURCE_URL} \
  -Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME} \
  -Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD} \
  -Dspring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect \
  -jar app.jar \
  --server.port=${PORT:-9000} \
  --server.address=0.0.0.0"]