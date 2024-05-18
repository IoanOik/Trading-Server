import java.io.*;
import java.net.*;

public class Client {

    public Client(String host, int port)
    {
        try
        {
            Socket client = new Socket(host, port);
            PrintStream out = new PrintStream(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String output = null;
            while (true)
            {
                output = keyboard.readLine();
                if (output.equalsIgnoreCase("exit"))
                {
                    out.println("Client exited!");
                    break;
                }
                File f = new File(output);
                String line;
                try (BufferedReader br = new BufferedReader(new FileReader(f)))
                {
                    while ((line = br.readLine()) != null)
                    {
                        out.println(line);
                        out.flush();
                        System.out.println("Sent: " + line);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            keyboard.close();
            out.close();
            in.close();
            client.close();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Client("localhost", 4999);
    }
}