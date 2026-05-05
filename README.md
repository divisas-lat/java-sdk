# Divisas.lat Java SDK

The official Java SDK for interacting with the Divisas.lat API.
This SDK is designed for enterprise usage. It provides a strongly-typed, thread-safe, and highly concurrent client using modern Java capabilities.

## Requirements
- Java 11 or later

## Installation

### Maven
Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>lat.divisas</groupId>
    <artifactId>divisas-java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
```gradle
implementation 'lat.divisas:divisas-java-sdk:1.0.0'
```

## Features
- **Strongly Typed Models**: Complete representations using standard Java POJOs.
- **Fluent API Builder**: An intuitive, chainable interface (`client.query().forCountry()`).
- **Zero Heavy Dependencies**: Built heavily on native `java.net.http.HttpClient` introduced in Java 11. It only uses `Jackson` for lightning-fast JSON processing.
- **Thread-Safe Caching**: Built-in, high-performance `ConcurrentHashMap` cache to respect rate limits effortlessly.

## Basic Usage

```java
import lat.divisas.sdk.DivisasClient;
import lat.divisas.sdk.enums.Country;
import lat.divisas.sdk.enums.Currency;
import lat.divisas.sdk.models.TodayRatesResponse;
import lat.divisas.sdk.models.ConversionResponse;

public class Main {
    public static void main(String[] args) {
        // Initialize the client
        // It will automatically read from the environment variable DIVISAS_API_KEY if present
        DivisasClient client = DivisasClient.builder()
                .apiKey("your_api_key_here")
                .build();

        // Get today's exchange rate for Guatemala
        TodayRatesResponse rates = client.query()
                .forCountry(Country.GUATEMALA)
                .getToday();

        System.out.printf("Currency: %s%n", rates.getRate().getCurrencyCode());
        System.out.printf("Buy: %.2f | Sell: %.2f%n", rates.getRate().getBuy(), rates.getRate().getSell());

        // Perform a conversion
        ConversionResponse conversion = client.query()
                .forCountry(Country.MEXICO)
                .withCurrency(Currency.USD)
                .convert(Currency.MXN, 100.50);

        System.out.printf("100.50 USD is %.2f MXN%n", conversion.getResult());
    }
}
```

## CLI Usage (Standalone Jar)

If you compile this project into a standalone jar (`mvn package`), you can use it directly from the terminal:

```bash
# Check the exchange rate
java -jar target/divisas-java-sdk-1.0.0-jar-with-dependencies.jar today GT USD

# Convert currencies
java -jar target/divisas-java-sdk-1.0.0-jar-with-dependencies.jar convert 100 USD to GTQ in GT
```
