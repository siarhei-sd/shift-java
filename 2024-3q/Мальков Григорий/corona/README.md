<h5>Версия Java 21</h5>
<h5>Для сборки проекта не использовались никакие инструменты для сборки по типу Maven или Gradle</h5>
<h5>Никаких дополнительных библиотек по типу lombok не использовалось, онли чистая Java :)</h5>
<h5>P.S. соре за отсутствие тестов, исправлюсь, обещаю, чесслово :)</h5>

<h5>Первый способ запуска без создания jar файла</h5>

<h3>Откройте командную строку в корневой папке </h3>

<h3>Скомпилируйте Java файлы: </h3>

**javac -d bin src/ru/korona/core/model/Arguments.java src/ru/korona/core/model/Stats.java src/ru/korona/enums/Types.java src/ru/korona/service/FileService.java
src/ru/korona/service/impl/DefaultFileService.java src/ru/korona/utils/FileProcessUtils.java src/DataSeparator.java**

<h3>Запустите программу, указав полный путь к файлам(если они находятся не в корневой папке): </h3>
**java -cp bin DataSeparator path\to\file1.txt path\to\file2.txt path\to\file3.txt**

**Если файлы находятся в корне:**

**java -cp bin DataSeparator file1.txt file2.txt file3.txt**

<h3>Добавьте опции:</h3>  

<h3>#1 Файлы с результатом с префиксом result_ :</h3>

**java -cp bin DataSeparator -p result_ file1.txt file2.txt file3.txt**

<h3>#2 Сохранить файлы в директорию result (находящуюся в корне проекта) с префиксом result_ :</h3>

**java -cp bin DataSeparator -o result -p result_ file1.txt file2.txt file3.txt**

<h3>#3 Распарсить другие файлы и результат положить уже существующую директорию result
в файлы с префиксом result_ в которых уже присутствуют результат предыдущего запуска(заапендить в существующий файл):</h3>

**java -cp bin DataSeparator -o result -p result_ -a file4.txt file5.txt file6.txt**

<h3>#4 Шаг аналогичный пункту #3 с выводом расширенной статистики в консоль:</h3>

**java -cp bin DataSeparator -o result -p result_ -a -f file4.txt file5.txt file6.txt**

========================================================================================================================

<h3>**Способ с созданием jar файла**</h3>

<h3>Скомпилируйте Java файлы: </h3>

**javac -d bin src/ru/korona/core/model/Arguments.java src/ru/korona/core/model/Stats.java src/ru/korona/enums/Types.java src/ru/korona/service/FileService.java src/ru/korona/service/impl/DefaultFileService.java src/ru/korona/utils/FileProcessUtils.java src/DataSeparator.java**

<h3>Создайте jar c помощью следующей команды: </h3>

**jar cfe korona.jar DataSeparator -C bin .**

<h3>Убедитесь что все классы попали в jar</h3>

**jar tf korona.jar**

<h3>Запустите jar файл</h3>

**java -jar korona.jar -f -a -p result_ -o result file4.txt file5.txt file6.txt**

<h3>Посмотрите результат в папке result или другой папке, которую вы указали в пути после аргумента -o</h3>
<h3>Все аргументы можно запустить по аналогии с примером описанном для запуска утилиты без jar файла</h3>

Writed By https://t.me/OtecGrigoryi