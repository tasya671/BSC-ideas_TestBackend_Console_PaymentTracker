package printer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DollarRateData {

    private static final String url = "http://www.vokrugsveta.ru/encyclopedia/index.php?title=%D0%9A%D1%83%D1%80%D1%81%D1%8B_%D0%B2%D1%81%D0%B5%D1%85_%D0%B2%D0%B0%D0%BB%D1%8E%D1%82_%D0%BC%D0%B8%D1%80%D0%B0";
    public static final Map<String, Double> dollarRate = new HashMap<>();

    static {
        init();
    }

    public static void init() {
        try {
            Document doc = Jsoup.connect(url).get();
            Element table = doc.select("table").get(3);
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++)
                parse(rows.get(i).text());
        } catch (IOException exp) {
            //System.err.println("Not find resource");
        }
    }


    private static void parse(String text) {
        Pattern amountPattern = Pattern.compile("\\d+[/./,]\\d+");
        Pattern dollar = Pattern.compile("(1 доллар США( )?=( )?(\\d+[/./,]\\d+( )?[A-Z]{3}))");
        String current;
        String currency;
        Double amount;

        Matcher findDollar = dollar.matcher(text);
        if (findDollar.find()) {
            current = text.substring(findDollar.start(), findDollar.end());
            Matcher findAmount = amountPattern.matcher(current);
            findAmount.find();
            amount = Double.parseDouble(current.substring(findAmount.start(), findAmount.end()).replace(",", "."));
            currency = current.substring(current.length() - 3);
            dollarRate.put(currency, amount);
        }
    }
}
