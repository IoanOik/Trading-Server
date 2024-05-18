
public class Stock {

    private int companyId;
    private int userId;
    private double initPrice;
    private double currentPrice;

    public Stock(int companyId, int userId, double initPrice, double currentPrice)
    {
        setStock(companyId, userId, initPrice, currentPrice);
    }

    public void setStock(int companyId, int userId, double initPrice, double currentPrice)
    {
        setCompanyId(companyId);
        setUserId(userId);
        setInitPrice(initPrice);
        setCurrentPrice(currentPrice);
    }

    public void setCompanyId(int companyId)
    {
        this.companyId = companyId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public void setInitPrice(double initPrice)
    {
        this.initPrice = initPrice;
    }

    public void setCurrentPrice(double currentPrice)
    {
        this.currentPrice = currentPrice;
    }

    public void setNewPrice(double price)
    {
        try
        {
            for (int i = 0; i < Server.stockMarket.allStocks.size(); i++)
            {
                if (this.companyId == Server.stockMarket.allStocks.get(i).getCompanyId())
                {
                    Server.stockMarket.allStocks.get(i).updatePrice(price);
                }
            }
        }
        catch (IndexOutOfBoundsException exception)
        {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    public void updatePrice(double price)
    {
        this.currentPrice = price;
    }

    public static Stock getStock(int userId, int companyId)
    {
        for (int i = 0; i < Server.stockMarket.allStocks.size(); i++)
        {
            if (Server.stockMarket.allStocks.get(i).userId == userId && Server.stockMarket.allStocks.get(i).companyId == companyId)
                return Server.stockMarket.allStocks.get(i);
        }
        return null;
    }

    public int getCompanyId()
    {
        return companyId;
    }

    public int getUserId()
    {
        return userId;
    }

    public double getInitPrice()
    {
        return initPrice;
    }

    public double getCurrentPrice()
    {
        return currentPrice;
    }


    public String toString()
    {
        return "\nStock [ " + "companyId= " + companyId + " userId= " + userId + " initPrice= " + initPrice + " currentPrice= " + currentPrice + " ]";
    }
}