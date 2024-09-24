1. System information

   Java version : 21
   Maven version : 3.9.2

2. Application information

The application is used to analyze and filter the contents of specified files by data type,
obtaining statistics depending on the input parameters. Input file(s) can contain numbers, russian and english
characters. 

UTF-8 is specified as encoding charset for input and output files.

To run the project do the following steps: 

   1. Open IntelliJ IDE and navigate to the project folder
   2. Open terminal and execute next command to assemble the project: ```mvn clean package```
   3. To launch the project run in terminal the following command:
      * PowerShell: ```java -jar .\target\analyzer-1.0.jar <parameters> <file name(s)>```
      * Unix: ```java -jar ./target/analyzer-1.0.jar <parameters> <file name(s)>```

Acceptable parameters:
-a: add information to existing files;

-o: /some/path: output file path;

Parameter can not be the last character in command line. Provided output path must exist on drive.

-p: file name prefix;

Parameter can not be the last character in command line.
Prefix must include at least three characters and can contain any characters.
Any value after -p parameter in command line will be added as prefix to the file name.

-f: full statistic information;

-s: short statistic information;

The file name must be an absolute path to the file (the root element and the complete directory list required to locate
the file).
The file name can not contain spaces. In this case an error message will be shown.



3. During program execution, output files are created once with try-with-resources statement. 
 
    If output file is empty, it is deleted.

    File statistic is calculating with summaryStatistics methods.
