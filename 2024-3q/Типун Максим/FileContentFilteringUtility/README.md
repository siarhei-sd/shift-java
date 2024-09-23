
1. При разработке использована версия Java 17 

2. Система сборки - Maven версии 3.9.8

## Запуск приложения

### Запуск приложения через InteleJ Idea
В клонированном проекте в настройки конфигурации запуска
В нем создайте конфигурацию App 
В конфигурации задайте:
1. Имя
2. Версию JDK 
3. Main class - com.filtr.utility.Application
4. Program argument (например -a -f -s -o <path> -p <prefix>, в соответствии с заданием)


### Запуск приложения, собранного Apache Maven
1. `cd FileContentFilteringUtility`
2. `mvn install`
3. `mvn clean install`
4. `java -jar target/FileContentFilteringUtility-1.0-SNAPSHOT.jar <набор опций и файлов см. задание>`

