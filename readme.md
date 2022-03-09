# Проект для портфолио

#### Задумка: приложение для поиска и резервирования ресторанов, кафе или мест общественного питания. Владельцы смогут заугрузить свои заведения, а посетилтели смогут найти кафе и т.п. для себя.

## Сборка и запуск приложения (Linux)
```
$ ./gradlew assemble && docker-compose -f docker-compose.yml up -d
```
По адресу [https://localhost:8080/v3/api-docs](https://localhost:8080/v3/api-docs) доступна документация в JSON формате для импорта Postman, Insomnia или т.п.

По адресу [https://localhost:8080/swagger-ui/index.html](https://localhost:8080/swagger-ui/index.html) доступно описание API приложения. (Подробно описан пока только CateringController)

Если хотите запускать вне Docker-контейнеров, то нужно создать базуизменить файл *src/main/resources/application.yml*
```
url: ${SPRING_DATASOURCE_URL} -> url: "своя базу данных"
username: ${SPRING_DATASOURCE_USERNAME} -> username: "свой пользователь"
password: ${SPRING_DATASOURCE_PASSWORD} -> password: "свой пароль к пользователю"
```