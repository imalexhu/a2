import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;  
import java.net.*;  


//ADT for storing the data object recieved from the GET request
class Display{
    private final int time; 
    private final String message;
    
    public Display(int time, String message){
        this.time = time;
        this.message = message;
    }

    // returns the data stoted
    public String getString(){
        return this.message; 
    }

    // returns the lamport clock time
    public int getTime(){
        return this.time; 
    }
} 

class GET extends TimerTask {

    /*
    Creates a socket connection to the aggregation server
    Sends a get request
    Using the returned String data, break it up into seperate display objects
    Sort the display objects using the lamport clock values
    Outputs the display objects in sorted order
    close the socket
     */

    static int empty = 0;

    public static String getData(){
        
        try {
            Socket socket = new Socket("localhost",4567);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("GET");
            dout.flush();
            DataInputStream dis=new DataInputStream(socket.getInputStream()); 
            String  str = dis.readUTF();  
            String[] content = str.split("\n\n");
            ArrayList<Display> display = new ArrayList<Display>();

            for(int i = 0; i<content.length; i++){              
                String[] temp = content[i].split("\n", 2);
                display.add(new Display(Integer.parseInt(temp[0]),temp[1]));
            }

            Collections.sort(display, new Comparator<Display>(){
                @Override
                public int compare(Display lhs, Display rhs) {
                    return lhs.getTime() < rhs.getTime() ? -1 : (lhs.getTime() > rhs.getTime()) ? 1 : 0;
                }
            });

            for(int i = 0; i<content.length; i++){
                System.out.println(display.get(i).getString() + "\n");
            }
            socket.close();
            return "1";
        } catch (Exception e) {
            return "-1";
        }
    }
    
    //Allows for 3 Failed sends before it times out
    @Override
    public void run() {
        int waitTime = 2000;
        int fail = 0;
        int initial= 0;
        String statusCode;
        while(fail <= 3){

            statusCode = getData();
            if(statusCode == "-1"){
                if(initial == 0){
                    System.out.println("GET Attempt " + fail + " failed after " + initial + " seconds");
                }else{
                    System.out.println("GET Attempt " + fail + " failed after " + waitTime + " seconds");

                }
                fail++;
            }else{
                break;
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(initial == 0){
                initial++;
            }else{
                waitTime*= 1.5;
            }
        }
        if(fail > 3){
            fail--;
            System.out.println("Terminating GET request after " + fail + " failed attempts");
            fail = 0;
        }
    }
}


/*
Takes input of seconds, and makes a GET
request to the ATOM sever every x seconds. 
*/
public class GETClient {
    public static void main(String[] args) {

        int seconds = Integer.parseInt(args[0])*1000;

        System.out.println("Started Client \n");
        Timer timer = new Timer();
        timer.schedule(new GET(), 0, seconds);
    }
}
