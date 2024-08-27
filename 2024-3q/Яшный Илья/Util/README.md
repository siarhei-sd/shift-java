## Requirements
- **Java**: 17
- **gradle wrapper**: v8.7

## Running the Application

To run the application, use the following console command:

```
1) ./gradlew clean jar
2) java -jar build/libs/util.jar (-args) (intput files)
```

## Explanation Commands
- #### 1) ./gradlew clean jar
   - #### clean: This step removes previous builds of the project to ensure a fresh start.
   - #### jar: This step compiles the current source code and packages it into a JAR file
  ### If you are unable to build the project from the command line, you can do it by clicking the 'jar' button in the Gradle tool window.
  ![gradleImg](https://github.com/user-attachments/assets/a4e1e81e-7aec-4667-a314-c182b362c0a5)


- #### 2) java -jar build/libs/util.jar -args input files
   - #### This command runs the application from the newly created JAR file.
   - #### Replace -args with the appropriate arguments your application requires.
   - #### Replace input files with the actual input files needed for the application.

## Arguments 
- #### 1) -o <path>: Specifies the output path for the result files.

  - #### Example: -o /some/path will save output files to /some/path.
- #### 2) -p <prefix>: Sets the prefix for the output file names.

  - #### Example: -p result_ will create files like result_integers.txt, result_strings.txt, etc.
- #### 3) -a: Appends new results to existing files instead of overwriting them.

- #### 4) -s: Collects and displays short statistics for each data type.

- #### 5) -f: Collects and displays full statistics for each data type.

## Test Files
   #### To test the application, two sample files filled with values for verification are provided in the root of the project:
- **in1.txt**
- **in2.txt**

#### These files can be used as input files to check the functionality of the program.

## Example Command
```
java -jar build/libs/util.jar -f -o /some/path -p sample- in1.txt in2.txt
```
