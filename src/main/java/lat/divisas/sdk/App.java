package lat.divisas.sdk;

import lat.divisas.sdk.enums.Country;
import lat.divisas.sdk.enums.Currency;
import lat.divisas.sdk.exceptions.DivisasException;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0].toLowerCase();
        DivisasClient client = DivisasClient.builder().build();

        try {
            switch (command) {
                case "today":
                    if (args.length < 2) {
                        System.out.println("Usage: java -jar sdk.jar today <COUNTRY> [CURRENCY]");
                        return;
                    }
                    Country cToday = Country.fromCode(args[1]);
                    var query = client.query().forCountry(cToday);
                    if (args.length >= 3) {
                        query.withCurrency(Currency.fromCode(args[2]));
                    }
                    var res = query.getToday();
                    System.out.printf("Country: %s | Base: %s | Date: %s%n", res.getCountry(), res.getBaseCurrency(), res.getDate());
                    System.out.printf("Rate: %s - Buy: %.2f / Sell: %.2f%n", res.getRate().getCurrencyCode(), res.getRate().getBuy(), res.getRate().getSell());
                    break;

                case "convert":
                    // convert 100 USD to GTQ in GT
                    if (args.length < 6 || !args[3].equalsIgnoreCase("to") || !args[5].equalsIgnoreCase("in")) {
                        System.out.println("Usage: java -jar sdk.jar convert <AMOUNT> <FROM> to <TO> in <COUNTRY>");
                        return;
                    }
                    double amount = Double.parseDouble(args[1]);
                    Currency from = Currency.fromCode(args[2]);
                    Currency to = Currency.fromCode(args[4]);
                    Country cConvert = Country.fromCode(args[6]);

                    var conv = client.query()
                            .forCountry(cConvert)
                            .withCurrency(from)
                            .convert(to, amount);

                    System.out.printf("Conversion: %.2f %s -> %.2f %s%n", conv.getAmount(), conv.getFrom().getCurrency(), conv.getResult(), conv.getTo().getCurrency());
                    System.out.printf("Effective Rate: %.2f (Via %s)%n", conv.getEffectiveRate(), conv.getVia());
                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    printUsage();
            }
        } catch (DivisasException ex) {
            System.err.println("API Error: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Divisas.lat Java CLI");
        System.out.println("  today <COUNTRY> [CURRENCY]");
        System.out.println("  convert <AMOUNT> <FROM> to <TO> in <COUNTRY>");
    }
}
