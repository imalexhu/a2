import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import java.io.*;
import java.net.Socket;  


class SendData extends TimerTask {

    String nameAndPort;
    List<String> contents;
    int startingIndex;
    LamportClock clock;

    public SendData(String nameAndPort, List<String> contents, int startingIndex) {
        this.nameAndPort = nameAndPort;
        this.contents = contents;
        this.startingIndex = startingIndex;
        this.clock = new LamportClock();
    }

    /*
    Creates a socket connection to the ATOM sever
    Create a PUT string using the name and port, clock time and the content
    Sends all data to the ATOM sever, and increment the startingIndex
    Increment the lamport clock
    */


    public String sendData(){
        try {
            Socket s = new Socket("localhost",4567);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            String send = String.format("%s\n%s\n%d\n\n%s", "PUT",this.nameAndPort,this.clock.val, this.contents.get(startingIndex));
            dout.writeUTF(send);
            dout.flush();
            this.startingIndex = (startingIndex +1) % contents.size();
            DataInputStream dis=new DataInputStream(s.getInputStream()); 
            String str = dis.readUTF();  
            System.out.println(str);
            dis.close();
            s.close();
            clock.send();
            return str;
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

            statusCode = sendData();
            if(statusCode == "-1"){
                if(initial == 0){
                    System.out.println("PUT Attempt " + fail + " failed after " + initial + " seconds");
                }else{
                    System.out.println("PUT Attempt " + fail + " failed after " + waitTime + " seconds");

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
            System.out.println("Terminating PUT request after " + fail + " failed attempts");
            fail = 0;
        }
    }
}

public class ContentServer {



      
    /*
    Read in the name and port as unique identifier
    Read in a starting index to read from in content folder
    Read in the inteveral in which a new put request is sent to atom server
    Fetch all the contents stored in the content folder. 
    Schedule a PUT request every X seconds , name and port, content and the starting index
    */
    
    public static void main(String[] args) {
        XMLparser X = new XMLparser();
        String nameAndPort = args[0];
        String location = "content/";
        int startingIndex = Integer.parseInt(args[1]);
        int seconds = Integer.parseInt(args[2])*1000;

        
        final File folder = new File(location);

        List<String> contents = new ArrayList<String>();

        for(File files : folder.listFiles()){
            try {
                FileReader file = new FileReader(location + files.getName());
                try {
                BufferedReader br = new BufferedReader(file);
                    try {
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();
        
                        while (line != null) {
                            sb.append(line);
                            sb.append(System.lineSeparator());
                            line = br.readLine();
                        }
                        String everything = sb.toString();
                        contents.add(everything);
                        br.close();
                    } catch( Exception e ){
                        System.out.println("Error getting data from content");
                        System.exit(1);
                    }
                } catch (Exception e) {
                    System.out.println("Error creating buffer");
                    System.exit(1);
                }    
            } catch (Exception e) {
                System.out.println("Error fileReader");
                System.exit(1);
            }
        }

        
        for(int i = 0; i< contents.size();i++){
            contents.set(i, X.convertToXML(contents.get(i)));
        }

        startingIndex = startingIndex % contents.size();


        System.out.println("Started Content Server");
        Timer timer = new Timer();
        timer.schedule(new SendData(nameAndPort,contents,startingIndex), 0, seconds);
    }
}
