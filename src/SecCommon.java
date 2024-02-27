public class SecCommon {
    public static String getPath() {
        ClassLoader classLoader = SecCommon.class.getClassLoader();
        String relativePath = classLoader.getResource(".sec").getPath();
        return relativePath;
    }
    public static String getPath(String path) {
        ClassLoader classLoader = SecCommon.class.getClassLoader();
        String relativePath = classLoader.getResource(path + "/.sec").getPath();
        return relativePath;
    }
}