package utils;

public class Utils {
    public static final String COMMA_DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    public static int parseInt(String value) {
        return value.isEmpty() ? -1 : Integer.parseInt(value);
    }
}