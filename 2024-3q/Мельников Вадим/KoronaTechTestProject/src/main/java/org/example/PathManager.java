package org.example;
import java.io.File;


public class PathManager {
    final String REGEX_BACKSLASH ="\\\\";

    private String dirPath;
    private String prefix;

    public PathManager(String directoryPath, String prefix)
    {
        this.prefix = (prefix==null) ? "" : prefix;
        this.dirPath = (directoryPath==null) ? "" : directoryPath;
    }

    public void setDirPath(String dirPath) {
        if(dirPath != null){
            this.dirPath = dirPath;
        }
    }

    public void setPrefix(String prefix) {
        if(dirPath != null){
            this.prefix = prefix;
        }
    }

    public String SecureDestinationDirectory() {
        StringBuilder nestedDirPaths = new StringBuilder();
        String [] directories = dirPath.split(REGEX_BACKSLASH);

        String existingPath = "";
        for(int i=0; i< directories.length; i++ ) {

            if (directories[i].isEmpty()){
                continue;
            }

            nestedDirPaths.append(directories[i]);
            File dir = new File (nestedDirPaths.toString());

            if (!dir.exists() || !dir.isDirectory()) {
                if (dir.mkdir()){
                    System.out.println("Каталог \""+dir.getName()+"\" успешно создан");
                }
                else{
                    System.out.println("Не удалось создать каталог \""+dir.getName()+"\"");
                    break;
                }
            }
            existingPath = nestedDirPaths.toString();
            nestedDirPaths.append('\\');
        }
        dirPath = existingPath;
        return existingPath;
    }

    public String assembleFullPath(String fileName) {
        if (dirPath == null || dirPath.isEmpty()) {
            return prefix + fileName;
        }
        return dirPath +'\\'+ prefix + fileName;
    }
}
