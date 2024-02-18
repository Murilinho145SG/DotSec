package sec;

public class SecCommon {
    public static String getPath(String path) {
        ClassLoader classLoader = SecCommon.class.getClassLoader();
        String relativePath = classLoader.getResource(path).getPath();
        return relativePath;
    }
}