# Використовуємо базовий образ з Gradle та Java для компіляції
FROM gradle:8.3-jdk17 AS build

# Копіюємо вихідний код проєкту
COPY --chown=gradle:gradle . /home/gradle/project

# Переходимо до робочої директорії
WORKDIR /home/gradle/project

# Збираємо JAR-файл
RUN gradle build --no-daemon

# Використовуємо lightweight-образ для запуску
FROM openjdk:17-jdk-slim

# Копіюємо JAR-файл із етапу збирання
COPY --from=build /home/gradle/project/build/libs/application.jar app.jar

# Встановлюємо порт для Spring Boot
EXPOSE 8080

# Команда для запуску JAR-файлу
ENTRYPOINT ["java", "-jar", "/app.jar"]






# # Вказуємо базовий образ Java
# FROM openjdk:17-jdk-slim

# # Додаємо аргумент для версії JAR
# ARG JAR_FILE=build/libs/application.jar

# # Копіюємо JAR-файл в контейнер
# COPY ${JAR_FILE} app.jar

# # Встановлюємо порт, на якому працює Spring Boot
# EXPOSE 8080

# # Вказуємо команду для запуску JAR
# ENTRYPOINT ["java", "-jar", "/app.jar"]
