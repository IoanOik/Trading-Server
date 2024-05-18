import java.util.Random;
import java.sql.Timestamp;
import java.time.Instant;
import java.io.*;

enum CommandType {
    //τυπος εντολής
    SellDuePrice, SellDuePercentage, BuyDuePrice, BuyDuePercentage
}

public class Command {

    private int userId;
    private int companyId;
    private CommandType commandType;
    private int numberOfStocks;
    private double price;
    private long commandId;
    private Timestamp timestamp;

    //constructor
    public Command(int userId, int companyId, CommandType commandType, int numberOfStocks, double price)
    {
        setCommand(userId, companyId, commandType, numberOfStocks, price);
    }

    @Override
    public String toString()
    {
        return "\nCommand: " + "userId=" + userId + ", companyId=" + companyId + ", commandType=" + commandType + ", numberOfStocks=" + numberOfStocks + ", price=" + price + ", commandId=" + commandId + ", timestamp=" + timestamp;
    }

    //setters

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public void setCompanyId(int companyId)
    {
        this.companyId = companyId;
    }

    public void setCommandType(CommandType commandType)
    {
        this.commandType = commandType;
    }

    public void setNumberOfStocks(int numberOfStocks)
    {
        this.numberOfStocks = numberOfStocks;
    }

    /*
            Σε περίπτωση που η εντολη εχει να κανει με ποσοστα μπορει ο χρηστης
            να μην εισαγει καποια λογικη τιμη μετοχης,ωστοσο
            γινεται ο υπολογισμος αυτης εντος της συναρτησης
         */
    public void setPrice(double price)
    {
        if (this.commandType == CommandType.SellDuePercentage)
        {
            for (int i = 0; i < Server.stockMarket.allStocks.size(); i++)
            {
                if (this.companyId == Server.stockMarket.allStocks.get(i).getCompanyId())
                {
                    this.price = Server.stockMarket.allStocks.get(i).getCurrentPrice() * 0.05 + Server.stockMarket.allStocks.get(i).getCurrentPrice();
                    break;
                }
            }
        }
        else if (this.commandType == CommandType.BuyDuePercentage)
        {
            for (int i = 0; i < Server.stockMarket.allStocks.size(); i++)
            {
                if (this.companyId == Server.stockMarket.allStocks.get(i).getCompanyId())
                {
                    this.price = Server.stockMarket.allStocks.get(i).getCurrentPrice() - Server.stockMarket.allStocks.get(i).getCurrentPrice() * 0.05;
                    break;
                }
            }
        }
        else
            this.price = price;
    }

    //μοναδικο id για καθε εντολη, θεωρητικα
    public void setCommandId()
    {
        Random rand = new Random();
        long number = rand.nextLong();
        if (number < 0)
            number *= (-1);
        this.commandId = number;
    }

    public void setTimestamp()
    {
        this.timestamp = Timestamp.from(Instant.now());
    }

    public void setCommand(int userId, int companyId, CommandType commandType, int numberOfStocks, double price)
    {
        setUserId(userId);
        setCompanyId(companyId);
        setCommandType(commandType);
        setNumberOfStocks(numberOfStocks);
        setPrice(price);
        setCommandId();
        setTimestamp();
    }

    //getters

    public int getUserId()
    {
        return this.userId;
    }

    public int getCompanyId()
    {
        return this.companyId;
    }

    public CommandType getCommandType()
    {
        return this.commandType;
    }

    public int getNumberOfStocks()
    {
        return this.numberOfStocks;
    }

    public double getPrice()
    {
        return this.price;
    }

    public long getCommandId()
    {
        return this.commandId;
    }

    public Timestamp getTimestamp()
    {
        return this.timestamp;
    }

    public static void main(String[] args)
    {
        /*
            Το διαβασμα της εντολης θα γινεται απο αρχειο που πριεχει λιστα εντολων,
            ουσιαστικα η υλοποιηση αυτου γινεται στο server, η main εδω εγινε για testing
         */
        File f = new File("Test.txt");
        String line = null;

        try (BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            while ((line = br.readLine()) != null)
            {
                String[] arguments = line.split(" ");
                int id = Integer.parseInt(arguments[0]);
                int companyId = Integer.parseInt(arguments[1]);
                CommandType commandType = CommandType.valueOf(arguments[2]);
                int numberOfStocks = Integer.parseInt(arguments[3]);
                double price = Double.parseDouble(arguments[4]);
                Command cmd = new Command(id, companyId, commandType, numberOfStocks, price);
                //commandsList.add(cmd);
                System.out.println(cmd);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
