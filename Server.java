import java.io.*;
import java.net.*;
import java.util.*;

/*
    Γενικα ο σερβερ θα λειτουργει ασταματητα, περιμενοντας την συνσδεση του επομενου πελατη.
    Στην συνεχεια με καθε συνδεση κατασκευαζει ενα καινουριο thread και αναθετει τον πελατη σε αυτο
 */
public class Server {

    public static List<Command> seller;   //ουρα που περιλαμβανει εντολες πωλησης
    public static List<Command> buyer;    //ουρα που περιλαμβανει εντολες αγορας
    public static StockMarket stockMarket;
    public static List<Command> commandsList;
    public static List<Command> invCommands;  // ουρα με τις μη εκτελεσιμες εντολες
    public static StockThread[] threads;
    public static int numberOfThreads;

    public Server(int port)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            commandsList = new ArrayList<>();
            stockMarket = new StockMarket(20, 10, 1000); // initialization της αγορας
            seller = Collections.synchronizedList(new LinkedList<>());
            buyer = Collections.synchronizedList(new LinkedList<>());
            invCommands = new ArrayList<>();
            threads = new StockThread[numberOfThreads];

            for (int i = 0; i < numberOfThreads; i++)
            {
                StockThread stockThread = new StockThread(i);
                threads[i] = stockThread;
                new Thread(stockThread).start();
            }
            //noinspection InfiniteLoopStatement
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        public void display(List list)
        {
            if (!list.isEmpty())
            {
                if (list.equals(invCommands))
                    System.err.println(list);
                else if (list.equals(seller))
                    System.out.println("Selling list:\n" + list + "\n");
                else
                    System.out.println("Buying list:\n" + list + "\n");
            }
        }


        public ClientHandler(Socket clientHandler)
        {
            this.clientSocket = clientHandler;
        }

        @Override
        public void run()
        {
            try
            {
                PrintStream out = new PrintStream(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String input = null;
                // Το thread θα εξυπηρετει τον client εως οτου λαβει το συγκεκριμενο text
                while (!(input = in.readLine()).equals("Client exited!"))
                {
                    //Γινεται το parsing του input και αναθετονται οι τυποι δεδομενων στις μεταβλητες

                    String[] arguments = input.split(" ");
                    int id = Integer.parseInt(arguments[0]);
                    int companyId = Integer.parseInt(arguments[1]);
                    CommandType commandType = CommandType.valueOf(arguments[2]);
                    int numberOfStocks = Integer.parseInt(arguments[3]);
                    double price = Double.parseDouble(arguments[4]);
                    Command command = new Command(id, companyId, commandType, numberOfStocks, price);

                    if (command.getCommandType() == CommandType.BuyDuePrice || command.getCommandType() == CommandType.BuyDuePercentage)
                    {
                        commandsList.add(command);
                        buyer.add(command);
                    }
                    else
                    {
                        //Πραγματοποιηται ελεγχος για το αν ο χρηστης εχει στην κατοχη του τις μετοχες που θελει να πουλησει

                        int stockQuantity = 0;
                        for (int i = 0; i < stockMarket.getInvestor(command.getUserId()).getStockList().size(); i++)
                        {
                            if (stockMarket.getInvestor(command.getUserId()).getStockList().get(i).getCompanyId() == command.getCompanyId())
                                stockQuantity++;
                        }
                        if (stockQuantity < command.getNumberOfStocks())
                            invCommands.add(command);
                        else
                        {
                            commandsList.add(command);
                            seller.add(command);
                        }
                    }
                }
                System.out.printf("Client exited : %s\n", clientSocket.getInetAddress().getHostAddress());
                out.close();
                in.close();
                clientSocket.close();
                // εκτυπωση των λιστων μετα την εξοδο του πελατη
                this.display(seller);
                this.display(buyer);
                this.display(invCommands);
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Insert number of threads and try again");
            System.exit(0);
        }
        numberOfThreads = Integer.parseInt(args[0]);
        new Server(4999);
    }
}