package sec;

public class SecCommon {
    public static String getPath() {
        ClassLoader classLoader = SecCommon.class.getClassLoader();
        String relativePath = classLoader.getResource(".sec").getPath();
        return relativePath;
    }
}