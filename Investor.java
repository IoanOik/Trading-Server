import java.util.List;
import java.util.ArrayList;

public class Investor {

    private List<Stock> myStocks = new ArrayList<>();//λιστα με τις μετοχες που εχει στην κατοχη του ο καθε χρηστης
    private final int id;

    public Investor(int id)
    {
        this.id = id;
    }

    public void sellStock(Stock stock)
    {
        for (int i = 0; i < myStocks.size(); i++)
        {
            if (stock.getCompanyId() == myStocks.get(i).getCompanyId())
            {
                myStocks.remove(stock);
                break;
            }
        }
    }

    public void buyStock(Stock stock)
    {
        stock.setUserId(this.id);
        myStocks.add(stock);
    }

    public int getId()
    {
        return id;
    }

    public void addMyStocks(Stock stock)
    {
        myStocks.add(stock);
    }

    public ArrayList<Stock> getStockList()
    {
        return (ArrayList<Stock>) this.myStocks;
    }

    @Override
    public String toString()
    {
        return "\nInvestor{" + "myStocks =\n" + myStocks + ", id=" + id + '}';
    }
}