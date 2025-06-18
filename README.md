# 📚 UI + API тестовый фреймворк для BookStore (DemoQA)

> Проект разработан в рамках обучения на курсе QA Guru "Автоматизация на Java" и предназначен для автоматизированного тестирования функциональности BookStore на сайте [demoqa.com](https://demoqa.com/).

## 🧩 Основные особенности проекта

- 🧪 **Сценарии с чистым UI** и **API-only тесты**
- 🔗 **Комбинированные сценарии**: действия через API → проверки через UI
- ⚙️ **Аннотируемая инфраструктура** (`@WithLogin`, `@AddRandomBooks`, `@ClearProfileAfterTest`)
- 📋 **Allure-отчёты с расширенными шагами** (в том числе для REST-запросов)
- 🧹 **Очистка данных после теста**
- ✅ **Реализация UI через Selenide и PageObject**
- 📦 **Сборка через Gradle**, Java 17

## 🗂 Структура проекта

```
src/test/java/
├── app.helpers.config/                      # Конфигурационные интерфейсы (Owner)
├── app.helpers/                     # Утилиты (Cookie-хелпер, Allure listener)
├── app.helpers.junit/
│   ├── annotation/              # JUnit 5 аннотации
│   └── extention/              # Расширения JUnit 5
├── app.models/                      # DTO-модели: Login, Book, Request/Response
├── pages/                       # UI PageObjects
├── app.specs/                       # RestAssured спецификации
├── tests/
│   ├── api/                     # API-клиенты: BookApi, LoginApi
│   ├── api_UI_tests/           # Сценарии UI+API
│   └── apiTests/               # Изолированные API-тесты
resources/
└── tpl/                         # Allure шаблоны для кастомного отображения
```

## 🧠 Архитектура тестов

Текущая структура ориентирована на постепенное масштабирование и отделение ответственности:

- `BookApi` и `LoginApi` — API-клиенты с логикой генерации и отправки запросов
- `ProfilePage` — UI-класс для взаимодействия с профилем пользователя
- Расширения (`AddBooksExtension`, `ClearProfileExtension`) управляют подготовкой и очисткой профиля через аннотации

## 💡 Allure-отчёт

В проекте используется Allure с включёнными интеграциями:
- Selenide
- REST Assured (с подсветкой запроса/ответа)
- Аннотированные шаги `@Step`

## ⚙️ Используемые технологии

| Инструмент              | Версия     | Назначение                     |
|-------------------------|------------|--------------------------------|
| Java                    | 17         | Язык                           |
| Gradle                  | —          | Сборка                         |
| JUnit 5                 | 5.10.0     | Фреймворк тестирования         |
| Selenide                | 6.19.1     | UI-тестирование                |
| REST Assured            | 5.3.1      | API-тестирование               |
| Allure                  | 2.21.0     | Отчётность                     |
| Lombok                  | 8.6        | Снижение шаблонного кода       |
| JavaFaker               | 1.0.2      | Генерация тестовых данных      |
| Owner                   | 1.0.12     | Работа с конфигами             |

## 🚀 Запуск тестов

```bash
./gradlew clean test
```

📦 Сборка Allure-отчёта:
```bash
./gradlew allureReport
./gradlew allureServe
```

## 🧪 Пример теста (сценарий API + UI)

```java
@WithLogin
@Tag("API_UI")
@ClearProfileAfterTest
@DisplayName("Добавление книги через API и проверка её наличия в UI-профиле")
@Test
void bookShouldBeVisibleInProfileAfterApiAddition(UserSession session) {
    BookDetailsModel book = new BookApi(session)
            .getRandomBook();

    new BookApi(session)
            .addSingleBookByIsbn(book.isbn());

    new ProfilePage()
            .openPage()
            .checkBookWasAddedToProfile(
                book.title(),
                book.author(),
                book.publisher());
}
```

## 🔧 В разработке / TODO

- 🧪 API-only тесты (с покрытием ошибок, статусов, контрактов)

## ✅ Авторизация через API

Перед каждым тестом выполняется `@WithLogin`, который:
- авторизуется через `LoginApi`
- проставляет cookie в браузер через `BrowserCookieHelper`
- сохраняет `UserSession` в JUnit Extension Store для использования в api

  Этот проект использует **GitHub Actions** для автоматического запуска тестов и генерации Allure отчёта при каждом пуше в ветку `main`.

### 🔧 Что настроено:
- ☕ **Java 17**
- 🌐 **Selenoid** для запуска браузеров
- ⚙️ **Gradle** — автоматический запуск UI и API тестов
- 📊 **Allure-отчёт**, публикуемый в **GitHub Pages**
- 🕓 **История отчетов сохраняется** между запусками

📂 **Отчёт размещается в ветке `gh-pages` и доступен по ссылке:**  
🔗 [Открыть Allure отчёт](https://sentidas.github.io/UI_API_java_demoQA/)
