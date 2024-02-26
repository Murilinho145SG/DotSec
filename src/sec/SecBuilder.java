package sec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SecBuilder {
    private static boolean isLoaded = false;
    private static final Map<String, String> SecVariable = new HashMap();
    private static boolean isNotFinding = false;
    private static String findSec;
    private static String path;
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
    private String readLine(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("=")) {
                return line;
            }
        }
        System.exit(1);
        System.err.println("Error to read .sec in line " + line);
        return null;
    }
    public static void load() throws IOException {
        isLoaded = true;
        new SecBuilder(SecCommon.getPath());
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
            }
        }
        return false;
    }
    public static Object get(String secName) throws IOException {
        if (isLoaded) {
            if (SecVariable.get(secName) != null) {
                try {
                    return Integer.parseInt(SecVariable.get(secName));
                } catch (NumberFormatException e) {
                    return SecVariable.get(secName);
                }
            } else {
                isNotFinding = true;
                findSec = secName;
            }
            if (isNotFinding()) {
                try {
                    return Integer.parseInt(SecVariable.get(secName));
                } catch (NumberFormatException e) {
                    return SecVariable.get(secName);
                }
            }
        } else {
            System.out.println("Error to read .sec file because Sec is not Loaded");
            return null;
        }
        System.out.println("Error to get " + secName + " Value");
        return null;
    }
}
