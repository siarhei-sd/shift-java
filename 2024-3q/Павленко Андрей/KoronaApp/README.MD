Инструкция по запуску.



Install Java 22.0.1

Install Maven 3.9.6

Open terminal and execute mvn clean package install

Run command java -jar /params/

-a - add to existing files

-f - full stats

-s - short stats

-o /some/path - ouput path

-p result_ - file name prefix

any argument without hyphen will be filename



В качестве IDE использовалась Intelligent Idea. Был создан новый проект Apache Maven (3.9.6) и в качестве SDK openjdk-22 (Language level - SDK default).



Все зависимости находятся в pom.xml в корне проекта.





В качестве программы для чтения аргументов командной строки используется Apache Commons CLI. Для вывода в консоль и ведения логов используется Apache Logging Services и PMD для проверки качества кода.



Для реализации был выбран Stream API т.к. это современный и производительный подход работы с данными в функциональном стиле. Также содержит методы сбора статистики. В ходе работы над тестовым заданием и профилированием приложения было принято решение инициализировать ouput  файлы для выходных данных (с использованием конструкции try-with-resources), один раз за все время выполнения программы, т.к. многократное открытие файла, осуществление записи и закрытие файла представляется нецелесообразным и ресурсоёмкий. И в случае отсутствия соответствующего типа данных удаляем пустой файл. Реализован тест, который покрывает класс для работы с командной строкой, но хорошей практикой является максимально возможное тестовое покрытие.



Тех стек:

Appache Common CLI, Apache Logging Services, PMD, Jupiter, Stream API