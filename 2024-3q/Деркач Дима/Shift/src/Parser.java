
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Текстовый парсер от Деркача Дмитрия для Korona Tech в рамках проекта Shift
public class Parser {
	
	private static String PREFIX = "";
	private static String OUTPUT_PATH = System.getProperty("user.dir"); // "дефолтный" путь для сохранения выходных файлов 
	private static boolean APPEND_DATA = false;
	private static boolean PRINT_SIMPLIFIED_STATISTICS = false;
	private static boolean PRINT_FULL_STATISTICS = false;

	static List<String> inputData = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		List<String> inputData = reciveDataFromCommandLine(args);
		checkCommandLineArgumenst(inputData);
		createDirectoriesForOutputFiles();
		List<File> files = createTextFilesForOutput();
		parseTextFromInputAndSaveToOutputFiles(inputData, files);
		printStatisticsIfNeeded(files);
	}
	
	private static List<String> reciveDataFromCommandLine(String[] data) {
		if (data.length == 0) {
			System.out.println("Не заданы аргументы командной строки. Программа будет завершена");
			System.exit(0);
		}
		System.out.print("Принятые данные из командной строки: ");
		for (int i = 0; i < data.length; i++) {
			inputData.add(i, data[i]);
			System.out.print(data[i] + " ");
		}
		System.out.println(); //Перевод каретки на новую строку
		return inputData;
		
	}
	
	private static void checkCommandLineArgumenst(List<String> inputData) throws IOException {
		if (inputData.contains("-p")) {
			addPrefix();
		}

		if (inputData.contains("-o")) {
			specifyOutputPath();
		} 

		if (inputData.contains("-a")) {
			appendData();
		}

		if (inputData.contains("-s")) {
			printSimplifiedStatistics();
		}

		if (inputData.contains("-f")) {
			printFullStatistics();
		}
	}
	
			private static void addPrefix() {
				int indexOfP = inputData.indexOf("-p");
				PREFIX = inputData.get(indexOfP + 1);
			}
			
			private static void specifyOutputPath() {
				int indexOfO = inputData.indexOf("-o");
				OUTPUT_PATH = inputData.get(indexOfO + 1);
			}
			
			private static void appendData() {
				APPEND_DATA = true;
			}

			private static void printSimplifiedStatistics() {
				PRINT_SIMPLIFIED_STATISTICS = true;

			}

			private static void printFullStatistics() {
				PRINT_FULL_STATISTICS = true;
			}
			
	private static void createDirectoriesForOutputFiles() {
			File makeDirectoryForOutputFiles;
			makeDirectoryForOutputFiles = new File(OUTPUT_PATH);
			makeDirectoryForOutputFiles.mkdirs();
	}
	
	private static List<File> createTextFilesForOutput() throws IOException {
		String path = OUTPUT_PATH + File.separator + PREFIX;
		System.out.println("Результат работы программы будет сохранен по пути: " + new File(OUTPUT_PATH).getAbsolutePath() + "\n");
		List<File> filesForOutput = new ArrayList<File>();
		
		File fileWithInts = new File(path + "integers.txt");
		if (!APPEND_DATA) {
			fileWithInts.delete();
			fileWithInts.createNewFile();
		} else {
			fileWithInts.createNewFile();
		}
		
		File fileWithFloats = new File(path + "floats.txt");		
		if (!APPEND_DATA) {
			fileWithFloats.delete();
			fileWithFloats.createNewFile();
		} else {
			fileWithFloats.createNewFile();
		}
		
		File fileWithStrings = new File(path + "strings.txt");
		if (!APPEND_DATA) {
			fileWithStrings.delete();
			fileWithStrings.createNewFile();
		} else {
			fileWithStrings.createNewFile();
		}
		
		filesForOutput.add(fileWithInts);
		filesForOutput.add(fileWithFloats);
		filesForOutput.add(fileWithStrings);
		return filesForOutput;
	}
	
	private static void parseTextFromInputAndSaveToOutputFiles(List<String> inputData, List<File> files) {	
		List<String> textFilesFromInput = new ArrayList<String>();
		int itterator = inputData.size();
		do {
			if (inputData.get(--itterator).contains(".txt")) {
				textFilesFromInput.add(inputData.get(itterator));
			} else {
				break;
			}
		} while (itterator > 0);
	
		for (String file : textFilesFromInput) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) { //?
				String line;
				while ((line = br.readLine()) != null) {
					String result = sortData(line.trim());
					writeToOutputFile(files, result, line);
				}
			} catch (FileNotFoundException e) {
				System.out.println("Текстовый файл, который был указан в командной строке не найден. Возможные причины: \n"
						+ "	1)В корнейвой папке (Shift) нет .txt файлов для обработки; \n"
						+ "	2)Наименование .txt файла в папке Shift не совпадает с тем, что был передан в командную строку");
				System.out.println("Программа будет завершена");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Возникла непридвиденная ошибка. Программа будет завершена");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

			private static String sortData(String line) {		
				if (line.matches("-?\\d+")) {// Integer regex
					return "integer";
				}			
				if (line.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {// Float regex																										// regex
					return "float";
				}	
				if (line.matches("[A-Za-z ]*") || line.matches("[\\p{InCyrillic}\\s]+")) {// String regex for eng or ru strings
					return "string";
				}
				return "incorrect type";			
			}

			private static void writeToOutputFile(List<File> files, String data, String line) throws IOException {
				try (BufferedWriter integerWriter = new BufferedWriter(
						new FileWriter(files.get(0).getAbsolutePath(), true));
						BufferedWriter floatWriter = new BufferedWriter(
						new FileWriter(files.get(1).getAbsolutePath(), true));
						BufferedWriter stringWriter = new BufferedWriter(
						new FileWriter(files.get(2).getAbsolutePath(), true))) {

					if (data == "integer") {
						integerWriter.write(line + "\n");
					}

					if (data == "float") {
						floatWriter.write(line + "\n");
					}
3
					if (data == "string") {
						stringWriter.write(line + "\n");
					}
				}
			}
			
	private static void printStatisticsIfNeeded(List<File> files) {
		if (PRINT_SIMPLIFIED_STATISTICS) {
			printSimplifiedStatistics(files);
			System.exit(0);
		}
		if (PRINT_FULL_STATISTICS) {
			printFullForIntegerAndFloats(files);
			printFullForStrings(files);
			System.exit(0);
		}
		System.out.println("Корректно отработав, программа завершает свое выполнение");
	}
	
			private static void printSimplifiedStatistics(List<File> files) {
				int itterator = 0;
				do {	
					int counter = 0;
					try (BufferedReader br = new BufferedReader(new FileReader(files.get(itterator).getAbsolutePath()))) {	
						String line;		
						while ((line = br.readLine()) != null) {
							counter++;
						}			
					} catch (IOException e) {
						System.err.println("Возникла ошибка во время отображения статистики: " + e.getMessage()); // Handle file read errors		
					}			
					System.out.println("Количество записанных элементов в файл " + files.get(itterator).getName() + ": " + counter);
					itterator++;
				} while (itterator < 3);		
			}
			
			private static void printFullForIntegerAndFloats(List<File> files) {
				int itterator = 0;
				do {
					int counter = 0; 
					float min = Float.MAX_VALUE; 
					float max = Float.MIN_VALUE; 
					float sum = 0;
					try (BufferedReader br = new BufferedReader(new FileReader(files.get(itterator).getAbsolutePath()))) {
						String line;
						while ((line = br.readLine()) != null) {
							float number = Float.parseFloat(line);
							counter++;
							if (number < min)
								min = number; 
							if (number > max)
								max = number;
							sum += number; 
						}
					} catch (IOException e) {
						System.err.println("Ошибка при чтении файла. Программа будет завершена ");
						System.exit(0);
					}
					double mean = counter > 0 ? (double) sum / counter : 0;
					System.out.println("Количество записанных элементов в файл " + files.get(itterator).getName() + ": " + counter);
					System.out.println("Минимальное число: " + min);
					System.out.println("Максиммальное число: " + max);
					System.out.println("Сумма всех чисел: " + sum);
					System.out.println("Среднее арифметическое всех чисел: " + mean);
					System.out.println("-------------------");
					itterator++;
				} while (itterator < 2);
			}

			private static void printFullForStrings(List<File> files) {
				int counter = 0; 
				int maxLength = 0;
				int minLength = Integer.MAX_VALUE;
				try (BufferedReader br = new BufferedReader(new FileReader(files.get(2).getAbsolutePath()))) {
					String line;
					while ((line = br.readLine()) != null) { 
						counter++; 
						int length = line.trim().length(); // Get the length of the current string
						if (length > maxLength) { // Check if current string is the longest
							maxLength = length; // Update maxLength
						}
						if (length < minLength) { // Check if current string is the shortest
							minLength = length; // Update minLength
						}
					}
				} catch (IOException e) { // Handle any IO exceptions that may occur
					System.err.println("Error reading file: " + e.getMessage());
				}
				System.out.println("Количество записанных элементов в файл " + files.get(2).getName() + ": " + counter);
				System.out.println("Длина наиболее длинной строки: " + maxLength);
				System.out.println("Длина наиболее короткой строки: " + (minLength == Integer.MAX_VALUE ? 0 : minLength));
			}
}