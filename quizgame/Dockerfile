# Вказуємо базовий образ Java
FROM openjdk:17-jdk-slim

# Додаємо аргумент для версії JAR
ARG JAR_FILE=build/libs/application.jar

# Копіюємо JAR-файл в контейнер
COPY ${JAR_FILE} app.jar

# Встановлюємо порт, на якому працює Spring Boot
EXPOSE 8080

# Вказуємо команду для запуску JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]
