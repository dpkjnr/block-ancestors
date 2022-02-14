import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Helper {
    public static List<String> top10(List<Transaction> txns) {
        Map<String, Transaction> graph = txns.stream().collect(Collectors.toMap(Transaction::getTxid, Function.identity()));

        for(Transaction txn : txns) {
            for (Transaction.Input input : txn.getVin()) {
                if (graph.containsKey(input.getTxid())) {
                    txn.parents.add(graph.get(input.getTxid()));
                }
            }
        }

        PriorityQueue<Transaction> priorityQueue = new PriorityQueue<>(11,
                Comparator.comparingInt(o -> o.ancestors.size()));
        for (Transaction txn : txns) {
            traverse(txn);
            if (priorityQueue.size() < 10) {
                priorityQueue.add(txn);
            } else {
                priorityQueue.add(txn);
                priorityQueue.poll();
            }
        }
        return priorityQueue.stream().peek(transaction -> {
            System.out.println("txnId: " + transaction.getTxid() + " ==> ancestors: " + transaction.ancestors.size());
        }).map(Transaction::getTxid).collect(Collectors.toList());
    }

    public static void traverse(Transaction txn) {
        if (txn.parents.isEmpty()) return;

        if (!txn.ancestors.isEmpty()) return;

        for (Transaction parentTxn : txn.parents) {
            traverse(parentTxn);
            txn.ancestors.addAll(parentTxn.ancestors);
            txn.ancestors.add(parentTxn);
        }
    }
}
