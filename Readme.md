## Дипломный проект профессии "Тестировщик"

___

### Документация по проекту

1. [План тестирования](https://github.com/Ginnyelf/QA-Diploma/blob/main/Plan.md)
2. [Отчёт по итогам тестирования](https://github.com/Ginnyelf/QA-Diploma/blob/main/Report.md)
3. [Отчёт по итогам автоматизации](https://github.com/Ginnyelf/QA-Diploma/blob/main/Summary.md)


### Запуск приложения

Для запуска приложения необходимо следующее ПО:
* IntelliJ IDEA
* Docker

* склонировать репозиторий на свой ПК командой ```git clone https://github.com/Ginnyelf/QA-Diploma```
* В терминале IntelliJ IDEA с помощью команды ```docker-compose up```  разворачиваем контейнер, необходимый для дальнейшей работы (настройки для запуска контейнера 

В новой вкладке терминала запустить тестируемое приложение:
   * Для MySQL: 
   ```
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

   ```
   * Для PostgreSQL: 
   ```
    java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
  
  ```
   ### Запуск тестов
В новой вкладке терминала запустить тесты:
1. Для MySQL: 
   ```
   ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
   ```
1. Для PostgreSQL: 
 ```
   ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
   ```
  
* запустить отчет командой:
```./gradlew allureServe (запуск и открытие отчета)```

* остановить SUT комбдинацией клавиш ```CTRL+C```

* Остановить контейнеры командой ```docker-compose stop``` и после удалить контейнеры командой
  ```docker-compose down```

    
