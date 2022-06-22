import java.util.Date;

/**
*
* @author Chenqiunian
* @date 2021/12/29
*/

public class JavaTest {
    public static void main(String[] args) throws InterruptedException {
        Date a = new Date();
        Thread.sleep(3000);
        Date b = new Date();
        long interval = (b.getTime() - a.getTime())/1000;

    }
}
