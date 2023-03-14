# Examples
This repository keeps my examples of test cases and auto tests. 

**"User.bugred"**
"http://users.bugred.ru/" это веб-приложение принадлежащее Ольге Назино (Киселева). Это приложение представляет собой task manager. К нему есть документация с описанием как приложение должно работать (https://testbase.atlassian.net/wiki/spaces/USERS/overview?homepageId=1074221). Приложение реализовано для практики rest и soap запросов. Приложение имеет значительно больше методов, чем я реализовал в своих тестах. Тесты написаны в качесте примера и на данный момент имеются примеры только rest запросов.
В папке **"API/Users.Bugred"** находится еще две папки, в одной папке лежит коллекция из postman, а в другой примеры тест кейсов REST API запросов. 
В папке **"Postman_collections"** лежит json файл с коллекцией REST API запросов написанными в программе "Postman". Запросы написаны по тест кейсам, которые находятся в папке "Test cases".
В папке **"Test cases"** лежат тест кейсы написанные под определенные запросы, которые реализованы в веб-приложении "http://users.bugred.ru/".

**"Saucedemo"**
"https://www.saucedemo.com/" это веб-приложение, от "Sauce Labs", реализующее функционал интернет магазина. Создано для практики в автоматезированном тестировании. 
В папке **"Saucedemo"** находятся несколько примеров тест кейсов и папка **"autotestCase"**.
В папке **"autotestCase"** лежат файлы в которых выполнена практика по испольхованию локаторов, циклов и условий. Так же имеется папка **"saucedemoPageFactory"**.
В папке **"saucedemoPageFactory"** лежит end2end тест. Выполнен рефакторинг кода и применен page factory design.