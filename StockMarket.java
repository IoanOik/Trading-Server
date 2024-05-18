import java.util.List;
import java.util.ArrayList;

public class StockMarket {

    public List<Stock> allStocks;  //Λιστα με το συνολο των μετοχων
    private List<Company> allCompanies; //Λιστα με το συνολο των εταιριων
    private List<Investor> allTraders;  // Λιστα με το συνολο των χρηστων

    private int numberOfTraders;
    private int numberOfCompanies;
    private int stocksPerTrader;


    public StockMarket(int numberOfCompanies, int numberOfTraders, int stocksPerTrader)
    {
        setNumberOfCompanies(numberOfCompanies);
        setNumberOfTraders(numberOfTraders);
        setStocksPerTrader(stocksPerTrader);
    }

    public void setNumberOfTraders(int numberOfTraders)
    {
        if (numberOfTraders > 0)
        {
            allTraders = new ArrayList<>();
            this.numberOfTraders = numberOfTraders;
            for (int i = 0; i < numberOfTraders; i++)
            {
                Investor investor = new Investor(i);
                allTraders.add(investor);
            }
        }
        else
        {
            System.err.println("Invalid number of traders argument");
            System.exit(-1);
        }
    }

    public void setStocksPerTrader(int numberOfStocks)
    {
        if (numberOfStocks > 0)
        {
            allStocks = new ArrayList<>();
            this.stocksPerTrader = numberOfStocks;
            for (int i = 0; i < allTraders.size(); i++)
            {
                for (int j = 0; j < allCompanies.size(); j++)
                {
                    for (int k = 0; k < numberOfStocks; k++)
                    {
                        Stock stock = new Stock(allCompanies.get(j).getCompanyId(), allTraders.get(i).getId(), allCompanies.get(j).getStockPrice(), allCompanies.get(j).getStockPrice());
                        allTraders.get(i).addMyStocks(stock);
                        allStocks.add(stock);
                    }
                }
            }
        }
        else
        {
            System.err.println("Invalid stocks per trader argument");
            System.exit(-1);
        }
    }

    public void setNumberOfCompanies(int numberOfCompanies)
    {
        if (numberOfCompanies > 0)
        {
            allCompanies = new ArrayList<>();
            int price = 10;
            this.numberOfCompanies = numberOfCompanies;
            for (int i = 100; i < (100 + numberOfCompanies); i++)
            {
                Company company = new Company(i, price);
                allCompanies.add(company);
                price += 10;
            }
        }
        else
        {
            System.err.println("Invalid argument as number of companies!");
            System.exit(-1);
        }
    }

    public int getNumberOfTraders()
    {
        return this.numberOfTraders;
    }

    public int getNumberOfCompanies()
    {
        return this.numberOfCompanies;
    }

    public int getStocksPerTrader()
    {
        return this.stocksPerTrader;
    }

    //Αναζητηση ενος χρηστη στην λιστα, με βαση το μοναδικο id του
    public Investor getInvestor(int id)
    {
        for (int i = 0; i < allTraders.size(); i++)
        {
            if (id == allTraders.get(i).getId())
            {
                return allTraders.get(i);
            }
        }
        return null;
    }

    /*
        και εδω η main εχει χαρακτηρα testing
     */
    public static void main(String[] args)
    {
        StockMarket stockMarket = new StockMarket(20, 10, 10);
        //System.out.println(stockMarket.allCompanies.toString());
        //System.out.println(stockMarket.allTraders.toString());
        System.out.println(stockMarket.allStocks.toString());
    }
}