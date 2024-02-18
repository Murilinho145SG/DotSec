import sec.SecCommon;
import sec.SecEvent;

public class DotSec {
    public static void main(String[] args) {
        SecEvent.read(SecCommon.getPath(".sec"));
        System.out.println(SecEvent.get("ALELO"));
    }
}
