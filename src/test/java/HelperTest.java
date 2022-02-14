import org.junit.Test;

import java.util.List;

public class HelperTest {

    @Test
    public void test() {
        int blockHeight = 680000;
        if (System.getProperty("blockHeight") != null) {
            try {
                blockHeight = Integer.parseInt(System.getProperty("blockHeight"));
            } catch (Exception ignore) {
            }
        }
        List<Transaction> allTransactions = Client.getAllTxn(blockHeight);
        Helper.top10(allTransactions);
    }
}
