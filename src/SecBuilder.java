import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SecBuilder {
    private static boolean isLoaded = false;
    private static final Map<String, String> SecVariable = new HashMap<>();
    private static boolean isNotFinding = false;
    private static String findSec;
    private static String path;
    private static boolean isDiferentDir = false;

    private SecBuilder(String path) throws IOException {
        SecBuilder.path = path;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String[] part = readLine(reader).split("=");
        String variable = part[0].trim();
        String value = part[1].trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            SecVariable.put(variable, value.substring(1, value.length() -1));
        } else {
            SecVariable.put(variable, value);
        }
    }

    public static SecBuilder dir(String path) {
        SecBuilder.path = path;
        isDiferentDir = true;
        try {
            if (!isLoaded) {
                isLoaded = true;
                return new SecBuilder(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("SecBuilder is already loaded");
    }

    private String readLine(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("=")) {
                return line;
            }
        }
        return null;
    }

    public static SecBuilder load() throws IOException {
        if (!isLoaded && !isDiferentDir) {
            isLoaded = true;
            new SecBuilder(SecCommon.getPath());
        } else {
            throw new IllegalStateException("SecBuilder is already loaded");
        }
        return null;
    }

    private static boolean isAlreadyLoaded() {
        return isLoaded;
    }

    private static boolean isNotFinding() throws IOException {
        if (isNotFinding) {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (SecVariable.get(findSec) == null) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.split("=")[0].trim().equals(findSec)) {
                        String value = line.split("=")[1].trim();
                        if (value.startsWith("\"") && value.trim().endsWith("\"")) {
                            SecVariable.put(findSec, value.substring(1, value.length() -1));
                        } else {
                            SecVariable.put(findSec, value);
                        }
                        return true;
                    }
                }
                if (SecVariable.get(findSec) == null) {
                    throw new IllegalStateException("Error to read " + findSec + " because is not exist");
                }
            }
        }
        throw new IllegalStateException("Error to read " + findSec + " because is not exist");
    }

    public static String get(String secName) throws IOException {
        if (isLoaded) {
            if (SecVariable.get(secName) != null) {
                return SecVariable.get(secName);
            } else {
                isNotFinding = true;
                findSec = secName;
            }
            if (isNotFinding()) {
                return SecVariable.get(secName);
            }
        } else {
            throw new IllegalStateException("Error to read .sec file because Sec is not Loaded");
        }
        throw new IllegalStateException("Error to get " + secName + " Value");
    }

    public static int getInt(String secName) {
        try {
            return Integer.parseInt(SecBuilder.get(secName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
