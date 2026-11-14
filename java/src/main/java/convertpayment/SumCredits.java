package convertpayment;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.math.BigDecimal;

public class SumCredits {

    static String getPage(int page) {
        if (page == 1) {
            return """
                {"page":1,"total_pages":2,"data":[
                  {"userId":"u1","type":"credit","amount":"1234.56"},
                  {"userId":"u1","type":"debit","amount":"5.00"},
                  {"userId":"u2","type":"credit","amount":"10.00"}
                ]}
                """;
        }
        return """
            {"page":2,"total_pages":2,"data":[
              {"userId":"u1","type":"credit","amount":"0.10"}
            ]}
            """;
    }

    public static void main(String[] args) {
        System.out.println(sumCredits("u1")); // 123466
        System.out.println(sumCredits("u2")); // 1000
        System.out.println(sumCredits("u9")); // 0
    }

    public static long sumCredits(String userId) {
        // Write your code here
        long total = 0;
        int totalPages = 1;
        for (int page = 1; page <= totalPages; page++) {
            JsonObject body = JsonParser.parseString(getPage(page)).getAsJsonObject();
            totalPages = body.get("total_pages").getAsInt();

            for (JsonElement element : body.getAsJsonArray("data")) {
                JsonObject tx = element.getAsJsonObject();
                System.out.println(tx);
                String id = tx.get("userId").getAsString();
                String type = tx.get("type").getAsString();
                if(userId.equals(id) && "credit".equals(type)){
                    long amount = new BigDecimal(tx.get("amount").getAsString())
                            .movePointRight(2)
                            .longValueExact();
                    total = Math.addExact(total, amount);
                }
            }
        }

        return total;
    }
}
