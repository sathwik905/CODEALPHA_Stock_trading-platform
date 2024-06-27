import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
class Stock {
    private String symbol;
    private String name;
    private double price;
    private int quantity;
    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.quantity = 0; 
    }
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
class Portfolio {
    private Map<String, Stock> stocksOwned;
    private double cashBalance;
    public Portfolio(double initialCash) {
        this.stocksOwned = new HashMap<>();
        this.cashBalance = initialCash;
    }
    public void buyStock(Stock stock, int quantity) {
        double totalPrice = stock.getPrice() * quantity;
        if (totalPrice <= cashBalance) {
            cashBalance -= totalPrice;
            if (stocksOwned.containsKey(stock.getSymbol())) {
                Stock existingStock = stocksOwned.get(stock.getSymbol());
                existingStock.setQuantity(existingStock.getQuantity() + quantity);
            } else {
                Stock newStock = new Stock(stock.getSymbol(), stock.getName(), stock.getPrice());
                newStock.setQuantity(quantity);
                stocksOwned.put(stock.getSymbol(), newStock);
            }
            System.out.println(quantity + " shares of " + stock.getName() + " bought successfully.");
        } else {
            System.out.println("Insufficient funds to buy " + quantity + " shares of " + stock.getName() + ".");
        }
    }
    public void sellStock(Stock stock, int quantity) {
        if (stocksOwned.containsKey(stock.getSymbol())) {
            Stock ownedStock = stocksOwned.get(stock.getSymbol());
            if (ownedStock.getQuantity() >= quantity) {
                double totalPrice = stock.getPrice() * quantity;
                cashBalance += totalPrice;
                ownedStock.setQuantity(ownedStock.getQuantity() - quantity);
                if (ownedStock.getQuantity() == 0) {
                    stocksOwned.remove(stock.getSymbol());
                }
                System.out.println(quantity + " shares of " + stock.getName() + " sold successfully.");
            } else {
                System.out.println("You do not own enough shares of " + stock.getName() + " to sell " + quantity + ".");
            }
        } else {
            System.out.println("You do not own any shares of " + stock.getName() + ".");
        }
    }
    public double getPortfolioValue() {
        double portfolioValue = cashBalance;
        for (Stock stock : stocksOwned.values()) {
            portfolioValue += stock.getPrice() * stock.getQuantity();
        }
        return portfolioValue;
    }
    public double getCashBalance() {
        return cashBalance;
    }

    public Map<String, Stock> getStocksOwned() {
        return stocksOwned;
    }
}
class MarketData {
    private Map<String, Double> stockPrices;
    private Random random;
    public MarketData() {
        this.stockPrices = new HashMap<>();
        this.random = new Random();
        initializeStockPrices();
    }
    private void initializeStockPrices() {
        stockPrices.put("AAPL", 150.0);
        stockPrices.put("GOOGL", 2500.0);
        stockPrices.put("AMZN", 3500.0);
 
    }
    public void updateStockPrices() {
        for (String symbol : stockPrices.keySet()) {
            double currentPrice = stockPrices.get(symbol);
            double newPrice = currentPrice * (1 + (random.nextDouble() - 0.5) * 0.1); // Fluctuate within +/- 5%
            stockPrices.put(symbol, newPrice);
        }
    }
    public double getPrice(String symbol) {
        return stockPrices.getOrDefault(symbol, 0.0);
    }
}
public class StockTradingPlatform {

    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio(10000.0); // Initial cash balance of $10,000
        MarketData marketData = new MarketData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Stock Trading Platform ===");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    buyStock(portfolio, marketData, scanner);
                    break;
                case 2:
                    sellStock(portfolio, marketData, scanner);
                    break;
                case 3:
                    viewPortfolio(portfolio);
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }

            marketData.updateStockPrices(); // Update stock prices after each transaction or view
        }
    }

    private static void buyStock(Portfolio portfolio, MarketData marketData, Scanner scanner) {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        double price = marketData.getPrice(symbol);
        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();

        Stock stock = new Stock(symbol, "Dummy Stock", price); // Replace "Dummy Stock" with actual stock name
        portfolio.buyStock(stock, quantity);
    }

    private static void sellStock(Portfolio portfolio, MarketData marketData, Scanner scanner) {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        double price = marketData.getPrice(symbol);
        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        Stock stock = new Stock(symbol, "Dummy Stock", price); // Replace "Dummy Stock" with actual stock name
        portfolio.sellStock(stock, quantity);
    }

    private static void viewPortfolio(Portfolio portfolio) {
        System.out.println("\n=== Portfolio Summary ===");
        System.out.println("Cash Balance: $" + portfolio.getCashBalance());
        System.out.println("Stocks Owned:");
        for (Stock stock : portfolio.getStocksOwned().values()) {
            System.out.println("- " + stock.getName() + " (" + stock.getSymbol() + "): " + stock.getQuantity() + " shares");
        }
        System.out.println("Total Portfolio Value: $" + portfolio.getPortfolioValue());
    }
}
