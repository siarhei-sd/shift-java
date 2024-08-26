package team.korona.shift;

public class DataUtils {

    public static boolean isInteger(String data) {
        try {
            Integer.parseInt(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String data) {
        try {
            Double.parseDouble(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
