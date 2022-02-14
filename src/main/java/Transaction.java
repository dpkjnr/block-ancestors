import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Transaction {
    private String txid;
    private List<Input> vin;
    Set<Transaction> parents = new HashSet<>();
    Set<Transaction> ancestors = new HashSet<>();

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public List<Input> getVin() {
        return vin;
    }

    public void setVin(List<Input> vin) {
        this.vin = vin;
    }

    public Set<Transaction> getParents() {
        return parents;
    }

    public void setParents(Set<Transaction> parents) {
        this.parents = parents;
    }

    public Set<Transaction> getAncestors() {
        return ancestors;
    }

    public void setAncestors(Set<Transaction> ancestors) {
        this.ancestors = ancestors;
    }

    public static class Input {
        private String txid;

        public String getTxid() {
            return txid;
        }

        public void setTxid(String txid) {
            this.txid = txid;
        }

        @Override
        public String toString() {
            return "Input{" +
                    "txid='" + txid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "txid='" + txid + '\'' +
                ", vin=" + vin +
                '}';
    }
}
