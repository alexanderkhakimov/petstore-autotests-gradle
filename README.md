# Проект автотестов для Petstore API

Простой проект автотестов для API сервиса [Petstore](https://petstore.swagger.io).

## Что в проекте
- Автотесты на Java 21 с использованием JUnit 5.
- Тесты покрывают основные методы API:
    - CRUD для сущности User (создание, получение, обновление, удаление)
    - CRUD для сущности Pet (создание, получение)
- Используются модели с Lombok Builder и централизованная конфигурация.
- Тестовые данные организованы в отдельные классы `TestData` и `PetTestData`.

## Как запустить

1. Склонировать репозиторий:
   - git clone <https://github.com/alexanderkhakimov/petstore-autotests-gradle.git>
   - cd petstore-autotests-gradle

2. Запустить все тесты через Gradle:
   ./gradlew clean test

3. Запустить тесты для конкретных сущностей:
- Для User:
  ```
  ./gradlew test --tests "com.petstore.tests.user.*"
  ```
- Для Pet:
  ```
  ./gradlew test --tests "com.petstore.tests.pet.*"
  ```
## Требования

- Java 21.
- Gradle (wrapper включен).

---

Проект готов к расширению и покрытию нового функционала API.