# Используйте образ OpenJDK
FROM openjdk:17-jdk

# Устанавливаем папку приложения
WORKDIR /app

# Копируем JAR файл в контейнер
COPY target/app-1.jar app.jar

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]
