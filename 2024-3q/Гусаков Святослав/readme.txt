������������!
����� ��������� ������� ���������� �� ��������� � ������ � ������ ��������.

������ Java: JDK 18.0.2
������� ������: Maven

��� ����� ������� ���������� ������� ����� ������� � IntelliJ IDEA, ��������� ���� ������ ����������� � ����� ����� ��������� ��������� ������ � ��������� IntelliJ:

mvn clean package

Maven �������� ���, ��� �� ��� ������� ��� ������ ����� � .jar ���� (����� target), ����� ������� �� ����� ��������
� ����� ��������.

��� ������ � �������� �� ������� � ����� ������� � ���������.
����� �������� ������� ��� ��� ������� �����:

java -jar target/test-task-1.0-SNAPSHOT.jar

��� �����-���� ������ ��� �������� ���������, ����������, ������ ������ �� �����.
������� ����� ����� ������ ��� ������� �� ����������� ����� ������, � ������� ��������� �������� (����� int -
integers.txt, float - floats.txt � string - strings.txt) ��� �������������. ����� ����� ����������� ������ ���� � ���� ������:

java -jar target\test-task-1.0-SNAPSHOT.jar C:\Users\NONAME\Documents\file1.txt

�����, ������� ����� ��������� ��������������� ��������, ��� �����-���� ���������� ����� ����������� � ����������
�������. ���� ��� ����� ������������ ���������� ��� �����, �� �� ���������� ����� "-o" � ����� ��� ����������� ����
� ����������:

java -jar target\test-task-1.0-SNAPSHOT.jar C:\Users\NONAME\Documents\file1.txt -o C:\Users\NONAME\Desktop\MyDirectory

����� �������� �����-���� ������� � ����� ������ � ����������� ������ ������ ( [�������]integers.txt ), �����
������������ ����� "-p", � ����� ��� ���������, ��� ��� �����. ������:

java -jar target\...SNAPSHOT.jar ...\file1.txt -o ...\MyDirectory -p myPrefix-

���������:
myPrefix-integers.txt   myPrefix-floats.txt   myPrefix-strings.txt

���� �� ����� ����� ������ �� �����-���� ����� ������ �������� � ������, ��� ���������������, ������, �����
������������ ����� "-a" (append):

java -jar target\...SNAPSHOT.jar ...\file2.txt -o ...\MyDirectory -p myPrefix- -a

������� ������:

"myPrefix-strings.txt":
hello
goodbye

"file2.txt":
cheers
hello

���������:

"myPrefix-strings.txt":
hello
goodbye
cheers
hello

� �������, �� ����� �������� ���������� ��������� ����� "-s" (������� ����������) � "-f" (������ ����������).
��������� ��� �����, �� �� �����.  ;)

java -jar target\...SNAPSHOT.jar ...\file2.txt -o ...\MyDirectory -p myPrefix- -a -f

���������:

...\MyDirectory\myPrefix-strings.txt:
elements: 4
max: 7
min: 5