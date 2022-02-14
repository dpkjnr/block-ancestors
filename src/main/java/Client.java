import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client {
    private static OkHttpClient client = new OkHttpClient();
    private static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static BlockDetail getBlockDetails(int blockHeight) {
        Request request = new Request.Builder().url("https://blockstream.info/api/blocks/" + blockHeight).build();
        try {
            Response response = client.newCall(request).execute();
            return objectMapper.readValue(response.body().bytes(), new TypeReference<List<BlockDetail>>(){}).get(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Transaction> getAllTxn(int blockHeight) {
        BlockDetail blockDetail = getBlockDetails(blockHeight);
        List<Transaction> allTxn = new ArrayList<>();

        for (int i = 0; i < blockDetail.tx_count; i += 25) {
            Request request = new Request.Builder().url("https://blockstream.info/api/block/" + blockDetail.id + "/txs/" + i).build();
            Response response;
            try {
                response = client.newCall(request).execute();
                allTxn.addAll(objectMapper.readValue(response.body().bytes(), new TypeReference<List<Transaction>>() {
                }));
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
        return allTxn;
    }

    private static class BlockDetail {
        private String id;
        private int height;
        private int tx_count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getTx_count() {
            return tx_count;
        }

        public void setTx_count(int tx_count) {
            this.tx_count = tx_count;
        }
    }
}
