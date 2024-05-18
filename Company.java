public class Company {

    private double stockPrice;
    private int companyId;

    public Company(int companyId, double stockPrice)
    {
        setStockPrice(stockPrice);
        setCompanyId(companyId);
    }

    public void setStockPrice(double stockPrice)
    {
        if (stockPrice > 0)
            this.stockPrice = stockPrice;
        else
        {
            System.err.println("Invalid stock price!");
            System.exit(-1);
        }
    }

    public void setCompanyId(int companyId)
    {
        this.companyId = companyId;
    }

    public double getStockPrice()
    {
        return this.stockPrice;
    }

    public int getCompanyId()
    {
        return companyId;
    }

    @Override
    public String toString()
    {
        return "\nCompany{" + "stockPrice=" + stockPrice + ", companyId=" + companyId + '}';
    }
}