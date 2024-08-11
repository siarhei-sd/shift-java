## Требования
- Java 17
- Maven 3.8.1

## Сборка проекта
Для сборки проекта используйте команду:
```sh
mvn clean install

## Запуск программы
java -jar target/DataFilterUtility-1.0-SNAPSHOT.jar [options] <input-files>

Опции
-o <path>: Задает путь для выходных файлов.
-p <prefix>: Задает префикс имен выходных файлов.
-a: Добавляет новые результаты в файлы с предыдущими.
-s: Выводит краткую статистику.
-f: Выводит полную статистику.

Запустите проект с нужными аргументами, например:
java -jar target/DataFilterUtility-1.0-SNAPSHOT.jar -o /some/path -p result_ -a -f in1.txt in2.txt
