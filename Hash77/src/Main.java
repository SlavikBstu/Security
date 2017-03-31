
import java.util.Map;

public class Main {


    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        System.out.println(Hash.getHash());
        long stop = System.currentTimeMillis();
        System.out.println((stop - start)/1000);

    }
}
