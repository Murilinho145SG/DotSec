package sec;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SecEvent {
    private static final Map<String, String> BufferVariable = new HashMap();
    public static void read(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;
            while((line = br.readLine()) != null) {
                if (line.contains("=")) {
                    String[] part = line.split("=");
                    if (part.length == 2) {
                        String variable = part[0].trim();
                        String value = part[1].trim();
                        if (value.startsWith("\"") && value.endsWith("\"")) {
                            BufferVariable.put(variable, value.substring(1, value.length() -1));
                        } else {
                            BufferVariable.put(variable, value);
                        }
                    }
                } else {
                    String[] part = line.split(":");
                    if (part.length == 2) {
                        String variable = part[0].trim();
                        String value = part[1].trim();
                        if (value.startsWith("\"") && value.endsWith("\"")) {
                            BufferVariable.put(variable, value.substring(1, value.length() -1));
                        } else {
                            BufferVariable.put(variable, value);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String secName) {
        return BufferVariable.get(secName);
    }
    public static int getInt(String secName) {
        return Integer.parseInt(BufferVariable.get(secName));
    }
}