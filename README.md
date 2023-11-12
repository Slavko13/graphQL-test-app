# Каталог Книг: GraphQL Сервис


Каталог Книг: GraphQL Сервис
Проект представляет собой сервис для управления каталогом книг, реализованный с использованием Spring Boot и GraphQL. Сервис позволяет выполнять операции добавления и получения информации о книгах и авторах.

## Технологии

- Java 17.0.5
- PostgreSQL
- Maven
- GraphQL
- Testcontainers (для интеграционных тестов)

## Задание

### Обязательная Часть

Необходимо реализовать сервис, принимающий и обрабатывающий GraphQL-запросы:

#### Мутации:

- `saveBook(title, authors)`: сохранение книги с указанным заголовком и авторами.
- `saveAuthor(name, books)`: сохранение автора с указанным именем и списком книг.

#### Запросы:

- `getBooksByAuthor(author)`: получение списка книг по автору.
- `getAllBooks()`: получение списка всех книг.
- `getAuthor(name)`: получение информации об авторе, включая список его книг.

#### Хранимые сущности:

- `Book (id, title, authors)`
- `Author (id, name, books)`

### Желательная Часть

- Написать DAO Integration Test с использованием Testcontainers.
- Docker Compose YAML-файл для запуска готового приложения.

## Инструкция по Запуску

1. Выполните сборку проекта:

    ```bash
    mvn clean package
    ```
2. Выполните сборку докер образов(для первого раза необязательный шаг):

   ```bash
    docker-compose build --no-cache
    ```

3. Запустите приложение с использованием Docker Compose:

    ```bash
    docker-compose up
    ```

4. Приложение теперь доступно по адресу `http://localhost:8080/graphql`.

## Инструкция по Запуску через IDE для Разработки

Для запуска проекта через IDE для разработки, выполните следующие шаги:

1. Запустите контейнеры с помощью Docker Compose:

    ```bash
    docker-compose up -f docker-compose-local.yml
    ```

2. Запустите приложение в режиме отладки через вашу IDE.
   
   - Запустите приложение, установив точку останова (breakpoint) там, где это необходимо.

Теперь приложение запущено и готово к отладке. Помните о том, что необходимо убедиться в наличии соответствующих зависимостей и конфигураций в вашем IDE для работы с проектом на Java 17.0.5 и Spring Boot.

## Тестирование

Для запуска интеграционных тестов с использованием Testcontainers:

```bash
mvn test
```
## Примеры GraphQL Запросов

### 1. Получение списка всех авторов

- **Описание:** Получение списка всех авторов с их идентификаторами и именами.
- **Запрос:**
  ```json
  {
    "getAllAuthors": "{ getAllAuthors { id name } }",
    "variables": {}
  }
  ```

### 2. Получение информации об авторе по имени

- **Описание:** Получение информации об авторе по его имени.
- **Запрос:**
  ```json
  {
      "query": "query GetAuthorByName($name: String!) { getAuthorByName(name: $name) { id name } }",
      "variables": {
        "name": "Имя Автора"
      }
  }
  ```

  ### 3. Получение информации об авторе по имени c опечаткой

- **Описание:** Получение информации об авторе по его имени. Например в таблице есть автор Bob Johnson. Его можно найти по "Bbo Jhnson"
- **Запрос:**
  ```json
  {
    "query": "query GetAuthorByFuzzyName($name: String!) { getAuthorByFuzzyName(name: $name) { id name } }",
    "variables": {
      "name": "Bbo Jhnson"
    }
  }
  ```

### 4. Получение списка всех книг

- **Описание:** Получение списка всех книг с их идентификаторами, заголовками и информацией об авторе.
- **Запрос:**
  ```json
  {
    "getAllBooks": "{ getAllBooks { id title author { id name } } }",
    "variables": {}
  }
  ```

### 5. Получение списка книг по идентификатору автора

- **Описание:** Получение списка книг по идентификатору автора.
- **Запрос:**
  ```json
  {
      "query": "query GetBooksByAuthorId($authorId: ID!) { getBooksByAuthorId(authorId: $authorId) { id title author { id name } } }",
      "variables": {
        "authorId": "1"
      }
  }
  ```

### 6. Сохранение нового автора

- **Описание:** Сохранение нового автора с указанным именем.
- **Запрос:**
  ```json
  {
      "query": "mutation SaveAuthor($author: AuthorInput!) { saveAuthor(author: $author) { id name } }",
      "variables": {
        "author": {
          "name": "Новый Автор"
        }
      }
  }
  ```

### 7. Сохранение новой книги

- **Описание:** Сохранение новой книги с указанным заголовком и идентификатором автора.
- **Запрос:**
  ```json
  {
      "query": "mutation SaveBook($book: BookInput!) { saveBook(book: $book) { id title author { id name } } }",
      "variables": {
        "book": {
          "title": "Новая Книга",
          "authorId": "1"
        }
      }
  }
  ```

Примечание: Параметр variables обязателен для передачи переменных. В случае отсутствия переменных, его можно оставить пустым (например, "variables": {}).
Замените значения переменных (например, "Имя Автора", "1", "Новый Автор") на актуальные данные для ваших тестовых сценариев.
