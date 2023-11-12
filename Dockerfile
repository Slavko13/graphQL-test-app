# Используйте образ OpenJDK
FROM openjdk:17-jdk

# Устанавливаем папку приложения
WORKDIR /app

# Аргумент сборки для принудительной инвалидации кэша
ARG CACHEBUST=1

# Копируем JAR файл в контейнер
COPY target/app-1.jar app.jar

# Команда для запуска приложения
CMD ["java", "-jar", "-Dspring.profiles.active=final", "app.jar"]
