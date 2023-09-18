import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrinterI implements Demo.Printer{

    String msgToSent = "";
    double arriveTime;
    double finalTime;
    double latency;
    public void printString(String s, com.zeroc.Ice.Current current){
        System.out.println(s);


        arriveTime = System.currentTimeMillis();
        try{
            String[] msg = s.split(": ");
            System.out.println("Has arrived");
            String inst = "";
            if(msg[1].matches("[0-9]+"))
                primeNumber(Integer.parseInt(msg[1]));
            else if(msg[1].equalsIgnoreCase("listports")){
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
                    for (int i = 2; i < msg.length; i++) 
                        message[i - 2] = msg[i];
                    
                    for (String elemento : message) 
                        inst += " "+elemento;
                    
                }
                instruction(inst);
            }

            finalTime= System.currentTimeMillis();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Final");
       
    }

    private void instruction(String inst) throws IOException{
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("bash", "-c", inst.toString()); // Para sistemas Windows

        // Redirigir la salida estándar y el error estándar del proceso
        builder.redirectErrorStream(true);

        // Iniciar el proceso
        Process proceso = builder.start();

        // Obtener la salida del proceso
        InputStream inputStream = proceso.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }
        try{
            int exitCode = proceso.waitFor();
            System.out.println("El comando terminó con código de salida: " + exitCode);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }

    public String reply(com.zeroc.Ice.Current current){
        return msgToSent;
    }

    public String replyTime(com.zeroc.Ice.Current current){
        return String.valueOf(arriveTime-finalTime);
    }

    public double latency(com.zeroc.Ice.Current current){
        return arriveTime;
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
                    System.out.println(numero);
                }
            }
            output.append("\n");
        }
        msgToSent = output.toString();
        
    }



}



