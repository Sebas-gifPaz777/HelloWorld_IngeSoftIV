import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrinterI implements Demo.Printer
{

    String msgToSent = "";
    public void printString(String s, com.zeroc.Ice.Current current)
    {
        System.out.println(s);

        try{
            String msg[] = s.split(":");
            String inst = "";
            if(msg[1].matches("[0-9]+")){
                primeNumber(Integer.parseInt(msg[1]));
            }else if(msg[1].equalsIgnoreCase("listports")){
                String temp[] = msg[1].split(" ");
                inst = "nmap "+temp[1];
                instruction(inst);
            }else if(msg[1].equalsIgnoreCase("listifs")){
                inst = "ifconfig";
                instruction(inst);
            }else if(msg[1].charAt(0) == '!'){
                inst = msg[1].substring(1);
                if (msg.length > 2) {
                    String[] message = new String[msg.length - 2];
                    for (int i = 2; i < msg.length; i++) {
                        message[i - 2] = msg[i];
                    }
                    for (String elemento : message) {
                        inst += " "+elemento;
                    }
                }
                instruction(inst);
            }
        }
    
        current.
    }

    private void instruction(String inst) throws IOException{
        Process process = new ProcessBuilder(inst).start();
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            output.append(line).append("\n");
        }
        msgToSent = output.toString();
    }

    private void primeNumber(int numero){
        StringBuilder output = new StringBuilder();
        output.append("Factors: ");
        if (numero < 0) {
            numero *= -1;
        }
        if (numero == 0){
            output.append("None.").append("\n");
        }
        if (numero == 1){
            output.append("1").append("\n");
        }
        if(numero > 1){
            for (int factor = 2; factor <= numero; factor++) {
                while (numero % factor == 0) {
                    output.append(Integer.toString(factor)).append(" ");
                    numero /= factor;
                }
            }
            output.append("\n");
        }
        msgToSent = output.toString();
        
    }



}



