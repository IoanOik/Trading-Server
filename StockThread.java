import java.lang.Runnable;
import java.sql.Timestamp;
import java.io.*;
import java.time.Instant;

public class StockThread implements Runnable {

    int j, i, id;
    volatile int[] ticket = new int[Server.numberOfThreads];
    volatile boolean[] choosing = new boolean[Server.numberOfThreads];
    //private final Object LOCK = new Object();

    public StockThread(int id)
    {
        this.id = id;
    }

    @Override
    public void run()
    {
        Thread t = Thread.currentThread();
        t.setName("Thread id: " + this.id);
        while (true)
        {
            try
            {
                //noinspection BusyWait
                Thread.sleep(5000);
                for (i = 0; i < Server.seller.size(); i++)
                {
                    for (j = 0; j < Server.buyer.size(); j++)
                    {
                        lock(this.id);
//                        synchronized (LOCK)
//                        {
                        try
                        {
                            //semaphore.acquire();
                            System.out.println("In ->" + t.getName());
                            if (Server.seller.get(i).getCompanyId() == Server.buyer.get(j).getCompanyId() && Server.seller.get(i).getPrice() <= Server.buyer.get(j).getPrice())
                            {
                                File file = new File("Trades.txt");
                                Timestamp timestamp = Timestamp.from(Instant.now());
                                Stock stock = Stock.getStock(Server.seller.get(i).getUserId(), Server.seller.get(i).getCompanyId());
                                assert stock != null;
                                stock.setNewPrice(Server.buyer.get(j).getPrice());
                                if (Server.seller.get(i).getNumberOfStocks() > Server.buyer.get(j).getNumberOfStocks())
                                {
                                    for (int k = 0; k < Server.buyer.get(j).getNumberOfStocks(); k++)
                                    {
                                        Server.stockMarket.getInvestor(Server.seller.get(i).getUserId()).sellStock(stock);
                                        Server.stockMarket.getInvestor(Server.buyer.get(j).getUserId()).buyStock(stock);
                                    }
                                    try
                                    {
                                        FileWriter fw = new FileWriter(file, true);
                                        fw.write("Sell id: " + Server.seller.get(i).getCommandId() + " Buy id: " + Server.buyer.get(j).getCommandId() + " Stocks: " + Server.buyer.get(j).getNumberOfStocks() + " Price: " + Server.seller.get(i).getPrice() + " DateTime: " + timestamp + "\n");
                                        fw.close();
                                    }
                                    catch (IOException e2)
                                    {
                                        e2.printStackTrace();
                                        System.exit(1);
                                    }
                                    Server.seller.get(i).setNumberOfStocks(Server.seller.get(i).getNumberOfStocks() - Server.buyer.get(j).getNumberOfStocks());
                                    for (int k = 0; k < Server.numberOfThreads; k++)
                                    {
                                        if (k == this.id)
                                            continue;
                                        if (Server.threads[k].j == this.j)
                                        {
                                            if (Server.threads[k].j == Server.buyer.size() - 1)
                                                Server.threads[k].j = 0;
                                            else
                                                Server.threads[k].j = this.j + 1;
                                        }
                                    }
                                    Server.buyer.remove(j);
                                }
                                else if (Server.seller.get(i).getNumberOfStocks() < Server.buyer.get(j).getNumberOfStocks())
                                {
                                    for (int k = 0; k < Server.seller.get(i).getNumberOfStocks(); k++)
                                    {
                                        Server.stockMarket.getInvestor(Server.seller.get(i).getUserId()).sellStock(stock);
                                        Server.stockMarket.getInvestor(Server.buyer.get(j).getUserId()).buyStock(stock);
                                    }
                                    try
                                    {
                                        FileWriter fw = new FileWriter(file, true);
                                        fw.write("Sell id: " + Server.seller.get(i).getCommandId() + " Buy id: " + Server.buyer.get(j).getCommandId() + " Stocks: " + Server.seller.get(i).getNumberOfStocks() + " Price: " + Server.seller.get(i).getPrice() + " DateTime: " + timestamp + "\n");
                                        fw.close();
                                    }
                                    catch (IOException e3)
                                    {
                                        e3.printStackTrace();
                                        System.exit(1);
                                    }
                                    Server.buyer.get(j).setNumberOfStocks(Server.buyer.get(j).getNumberOfStocks() - Server.seller.get(i).getNumberOfStocks());
                                    for (int k = 0; k < Server.numberOfThreads; k++)
                                    {
                                        if (k == this.id)
                                            continue;
                                        if (Server.threads[k].i == this.i)
                                        {
                                            if (Server.threads[k].i == Server.seller.size() - 1)
                                                Server.threads[k].i = 0;
                                            else
                                                Server.threads[k].i = this.i + 1;
                                        }
                                    }
                                    Server.seller.remove(i);
                                }
                                else
                                {
                                    for (int k = 0; k < Server.seller.get(i).getNumberOfStocks(); k++)
                                    {
                                        Server.stockMarket.getInvestor(Server.seller.get(i).getUserId()).sellStock(stock);
                                        Server.stockMarket.getInvestor(Server.buyer.get(j).getUserId()).buyStock(stock);
                                    }
                                    try
                                    {
                                        FileWriter fw = new FileWriter(file, true);
                                        fw.write("Sell id: " + Server.seller.get(i).getCommandId() + " Buy id: " + Server.buyer.get(j).getCommandId() + " Stocks: " + Server.buyer.get(j).getNumberOfStocks() + " Price: " + Server.seller.get(i).getPrice() + " DateTime: " + timestamp + "\n");
                                        fw.close();
                                    }
                                    catch (IOException e4)
                                    {
                                        e4.printStackTrace();
                                        System.exit(1);
                                    }
                                    for (int k = 0; k < Server.numberOfThreads; k++)
                                    {
                                        if (k == this.id)
                                            continue;
                                        if (Server.threads[k].j == this.j)
                                        {
                                            if (Server.threads[k].j == Server.buyer.size() - 1)
                                                Server.threads[k].j = 0;
                                            else
                                                Server.threads[k].j = this.j + 1;
                                        }
                                        if (Server.threads[k].i == this.i)
                                        {
                                            if (Server.threads[k].i == Server.seller.size() - 1)
                                                Server.threads[k].i = 0;
                                            else
                                                Server.threads[k].i = this.i + 1;
                                        }
                                    }
                                    Server.buyer.remove(j);
                                    Server.seller.remove(i);
                                }
                            }
                            //semaphore.release();
                            System.out.println("Out ->" + t.getName());
                            unlock(this.id);
                        }
                        catch (IndexOutOfBoundsException e1)
                        {
                            e1.getMessage();
                            System.out.println("Out ->" + t.getName());
                            unlock(this.id);
                            break;
                        }
                        //}
                    }
                }
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public void lock(int num)
    {
        choosing[num] = true;
        ticket[num] = max(ticket) + 1;
        choosing[num] = false;

        for (int k = 0; k < ticket.length; k++)
        {
            if (k == num)
                continue;
            while (choosing[k]) ;
            while (ticket[k] != 0 && (ticket[num] > ticket[k] || (ticket[k] == ticket[num] && num > k))) ;
        }
    }

    public void unlock(int num)
    {
        ticket[num] = 0;
    }

    public int max(int[] array)
    {
        int max = array[0];
        for (int value : array)
        {
            if (max < value)
                max = value;
        }
        return max;
    }
}