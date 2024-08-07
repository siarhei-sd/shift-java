Java version: Java 22
Build tool: Maven 3.9.8

To build project:
1) Load dependencies in pom.xml
2) Enter the following line in the terminal from project folder:
   mvn clean compile package

To use the utility, enter the following line:
java -jar target/testProject-1.0.jar [commands separated by spaces] [names of files separated by spaces]

Available commands:
-o + path : sets directory where the results will be written
-p + prefix : sets a prefix to file names
-a : new results will be appended to previous results
-s : shows short statistics
-f : shows full statistics

Example: java -jar target/testProject-1.0.jar -s -a -p sample- in1.txt in2.txt

For the utility to work correctly, the values transferred in the files must satisfy the boundaries of the float or int or string data types.
Files must have UTF-8 encoding.
