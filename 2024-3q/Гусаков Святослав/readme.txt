Здравствуйте!
Здесь находится краткая инструкция по установке и работе с данным проектом.

Версия Java: JDK 18.0.2
Система сборки: Maven

Для сбора проекта достаточно открыть папку проекта в IntelliJ IDEA, подождать пока проект подгрузится и после этого прописать следующую строку в терминале IntelliJ:

mvn clean package

Maven настроен так, что он сам соберет все нужные файлы в .jar файл (папка target), через который мы будем работать
с самим проектом.

Для работы с проектом мы заходим в папку проекта в терминале.
Далее основной строкой для его запуска будет:

java -jar target/test-task-1.0-SNAPSHOT.jar

Без каких-либо файлов для проверки программа, разумеется, ничего делать не будет.
Поэтому далее после строки для запуска мы прописываем имена файлов, в которых находятся элементы (типов int -
integers.txt, float - floats.txt и string - strings.txt) для распределения. Лучше всего прописывать полные пути к этим файлам:

java -jar target\test-task-1.0-SNAPSHOT.jar C:\Users\NONAME\Documents\file1.txt

Файлы, которые будут содержать отсортированные элементы, без каких-либо параметров будут создаваться в директории
проекта. Если вам нужна ОПРЕДЕЛЕННАЯ директория для этого, то мы используем опцию "-o" и после нее прописываем путь
к директории:

java -jar target\test-task-1.0-SNAPSHOT.jar C:\Users\NONAME\Documents\file1.txt -o C:\Users\NONAME\Desktop\MyDirectory

Чтобы добавить какой-либо префикс к нашим файлом с конкретными типами данных ( [префикс]integers.txt ), можно
использовать опцию "-p", а после нее добавлять, что нам нужно. Пример:

java -jar target\...SNAPSHOT.jar ...\file1.txt -o ...\MyDirectory -p myPrefix-

Результат:
myPrefix-integers.txt   myPrefix-floats.txt   myPrefix-strings.txt

Если мы хотим новые данные из каких-либо новых файлов добавить к старым, уже отсортированным, данным, тогда
используется опция "-a" (append):

java -jar target\...SNAPSHOT.jar ...\file2.txt -o ...\MyDirectory -p myPrefix- -a

Входные данные:

"myPrefix-strings.txt":
hello
goodbye

"file2.txt":
cheers
hello

Результат:

"myPrefix-strings.txt":
hello
goodbye
cheers
hello

И наконец, мы можем получить статистику используя опции "-s" (краткая статистика) и "-f" (полная статистика).
Указывать две можно, но не нужно.  ;)

java -jar target\...SNAPSHOT.jar ...\file2.txt -o ...\MyDirectory -p myPrefix- -a -f

Результат:

...\MyDirectory\myPrefix-strings.txt:
elements: 4
max: 7
min: 5