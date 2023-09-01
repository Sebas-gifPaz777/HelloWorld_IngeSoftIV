import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.zeroc.IceInternal.OutgoingAsync;

public class Client
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            //com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(
                communicator.propertyToProxy("Printer.Proxy"));
            Demo.PrinterPrx twoway = Demo.PrinterPrx.checkedCast(
                communicator.propertyToProxy("Printer.Proxy")).ice_twoway().ice_secure(false);
            //Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            Demo.PrinterPrx printer2 = twoway.ice_oneway();

            if(printer == null)
            {
                throw new Error("Invalid proxy");
            }
            

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String user = System.getProperty("user.name"); 
            String msg="";
            String hostname = InetAddress.getLocalHost().getHostName();

            String[] entry;
            boolean conti = true;
            while(conti){
                
                msg = br.readLine();

                entry = msg.split(" ");

                if (msg.equalsIgnoreCase("listifs") 
                || msg.matches("[0-9]+") 
                || (entry[0].equalsIgnoreCase("listports") && entry[1].matches("\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?"))
                || msg.charAt(0) == '!'){

                    printer.printString(user + "@" + hostname + ": " + msg);
                    

                }
            }

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}