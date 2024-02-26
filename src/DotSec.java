import sec.SecBuilder;

import java.io.IOException;

public class DotSec {
    public static void main(String[] args) throws IOException {
        SecBuilder.load();
        System.out.println(SecBuilder.get("MY_NUMBER"));

    }
}
