import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

// defines the ADT representing a item that has been put into the server
class Item {
    public Timer Timer;
    public int time;
    private String title;
    private String subtitle = "";
    private String link = "";
    private String updated = "";
    private String author = "";
    private String name = "";
    private String id = "";
    private String entry = "";
    private String summary = "";
    private String original  = "";

    /*
    Using the data object sent in
    Splits it up into properties of the object
    */
    public Item(Timer Timer, String data,int time){
        this.Timer = Timer;
        this.time = time;
        this.original = data;

        String[] fields = data.split("\n");

        for(int i = 0; i < fields.length; i++){
            String title = "";
            String content = "";
            int j = 1;
            while(fields[i].charAt(j) != '>'){
                title+= fields[i].charAt(j);
                j++;
            }
            j++;
            while(fields[i].charAt(j) != '<'){
                content+= fields[i].charAt(j);
                j++;
            }
            switch(title){
                case "title":
                this.title = content;
                break;
                case "subtitle":
                this.subtitle = content;
                break;
                case "link":
                this.link = content;
                break;
                case "updated":
                this.updated = content;
                break;
                case "author":
                this.author = content;
                break;
                case "name":
                this.name = content;
                break;
                case "id":
                this.id = content;
                break;
                case "entry":
                this.entry = content;
                break;
                case "summary":
                this.summary = content;
                break;
            }
        }
    }

    //returns the time
    public int getTime(){
        return this.time;
    }

    //returns the original string used to build the Item
    public String getOriginal(){
        return this.original ;
    }

    //Converts the object into a string
    public String toString(){
        String ret = new String();
        ret = ret + this.time + "\n";
        ret = ret + "title : " + this.title+ "\n";
        if(this.subtitle != ""){
            ret = ret + "subtitle : " +this.subtitle+ "\n";
        }
        if(this.link != ""){
            ret = ret + "link : " +this.link+ "\n";
        }
        if(this.updated != ""){
            ret = ret + "updated : " +this.updated+ "\n";
        }
        if(this.author != ""){
            ret = ret + "author : "+ this.author+ "\n";
        }
        if(this.name != ""){
            ret = ret + "name : " +this.name+ "\n";
        }
        if(this.id != ""){
            ret = ret + "id : " +this.id+ "\n";
        }
        if(this.entry != ""){
            ret = ret + "entry : " +this.entry+ "\n";
        }
        if(this.summary != ""){
            ret = ret + "summary : " + this.summary+ "\n";
        }
        ret = ret + "\n";
        return ret;
    }

}


//This class is the implimentation for an AgreWorker thread
class AgreWorker implements Runnable {

    private final Socket socket;
    private final StoredItems storedItems;
    private final LamportClock clock;
    private final XMLparser X;

    //Writes a faultTollerance file to use for startup
    //Given an input of hashmap of items
    //For each entry of the hashmap input the key, clock time, and the str used to create item

    public void CreateFaultTollerance(HashMap<String,Item> items){
        BufferedWriter bf = null;
  
        try {
  
            bf = new BufferedWriter(new FileWriter("faultTollerance.txt"));
  
            for (String key : items.keySet()) {
                String time = String.valueOf(items.get(key).getTime());
                bf.write(key);
                bf.newLine();
                bf.write(time);
                bf.newLine();
                bf.write(items.get(key).getOriginal());
                bf.newLine();
            }
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);

        }
        finally {
  
            try {
  
                // always close the writer
                bf.close();
            }
            catch (Exception e) {
            System.exit(1);

            }
        }
    }

    public AgreWorker(Socket socket, StoredItems storedItems, LamportClock clock) {
        this.socket = socket;
        this.storedItems = storedItems;
        this.clock = clock;
        this.X = new XMLparser();
    }

    /*
    Read in the contents sent via the socket
    Checks if the Str is a GET request

    If it is, convert all current items in storedItems into a string
    and send it back through the socket

    Else it is a PUT request from content server
    Split the string into its unique Id, lamport clock time and data
    Calculate new clock time with newly recieved time
    Checks if the data is valid XML
    If it is, store the item into storedItems
    And returns a status code
    */
    public void run() {
        while (true) {
            try {
                DataInputStream dis=new DataInputStream(socket.getInputStream());  
                String  str = dis.readUTF(); 
                 
                if(str.equals("GET")){
                    HashMap<String,Item> ret = storedItems.getData();
                    String send = new String();
                    for (String key : ret.keySet()){
                        send = send + ret.get(key).toString();
                    }
                    DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                    dout.writeUTF(send);
                    dout.flush();
                    dout.close();
                }else if(str.charAt(0) == 'P'){
                    String[] temp = str.split("\n\n");
                    String[] meta = temp[0].split("\n");
                    String id = meta[1];
                    clock.recieve(Integer.parseInt(meta[2]));
                    String data = temp[1];

                    data = X.isValidXML(data);
                    
                    if(!data.equalsIgnoreCase("-1")){
                        int size = storedItems.getData().size();

                        Timer t = new Timer();
                        Item item = new Item(t,data,clock.val);
                        storedItems.storeData(id,item);
                        CreateFaultTollerance(storedItems.getData());

                        if(size != storedItems.getData().size()){
                            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                            dout.writeUTF("201 - HTTP_CREATED");
                            dout.flush();
                            dout.close();
                        }else{
                            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                            dout.writeUTF("200");
                            dout.flush();
                            dout.close();
                        }
                    }else if(data.length() == 0){
                        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                        dout.writeUTF("204");
                        dout.flush();
                        dout.close();
                    }
                    else{
                        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                        dout.writeUTF("500");
                        dout.flush();
                        dout.close();
                    }
                }else{
                    DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                    dout.writeUTF("400");
                    dout.flush();
                    dout.close();
                }
                break;
            } catch (Exception e) {
                System.out.println(e);
            System.exit(1);

            }
        }
        try{
            socket.close();
        }catch (Exception e){
            System.out.println(e);
            System.exit(1);

        }
    }
}


// Describes the data structure that is used to store all PUT requests

class StoredItems {

    private final HashMap<String,Item> items; 
    /*
    Checks if there is an exisiting entry in the hashmap with the same key
    If there is, cancle the scheduled remove method and remove the entry
    Else, schedule a remove method for 12s in the future and insert the current item
    */

    public void storeData(String key, Item item){
        if(items.containsKey(key)){
            System.out.println("Overriding exisiting item");
            items.get(key).Timer.cancel();
            items.remove(key);
        }

        item.Timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Content Expired");
                item.toString();
                items.remove(key);
                
                item.Timer.cancel();
            }
        }, 12000);
        items.put(key, item);
        System.out.println("New Content Added from CS : " + key );

    }

    // returns a copy of the hashmap
    public HashMap<String,Item> getData(){
        HashMap<String,Item> ret = new HashMap<String, Item>(items);
        return ret;
    }


    public StoredItems(){
        this.items = new HashMap<String,Item>();
    }

}


public class AggregationServer {

    // same as fault tollerance but used to demonstrate startup
    public static int startup(int val, StoredItems storedItems) throws IOException{
        String location = "startup/" + val + ".txt";
        try {
            FileReader file = new FileReader(location);
            BufferedReader br = new BufferedReader(file);
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                br.close();
                String s = sb.toString();
                if(s.isEmpty()){
                    return 0;
                }
                int max = 0; 
                String[] contents = s.split("\n\n");
                for(int i = 0;i< contents.length; i++){
                    String[] temp = contents[i].split("\n",3);
                    String id = temp[0];
                    int time = Integer.parseInt(temp[1]);
                    if(time > max){
                        max = time; 
                    }
                    String content = temp[2];
                    Timer t = new Timer();
                    Item item = new Item(t,content,time);
                    storedItems.storeData(id, item);
                }
                return max;
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                return 0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }



    /* 
    Given the location of the faultTollerance file
    Read in all the file data 
    Using the string data, create item objects and add it to the storedItem object
    Returns the maximum lamport clock stored
    */

    public static int faultTollerance(StoredItems storedItems) throws IOException{
        String location = "faultTollerance.txt";
        try {
            FileReader file = new FileReader(location);
            BufferedReader br = new BufferedReader(file);
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                br.close();
                String s = sb.toString();
                if(s.isEmpty()){
                    return 0;
                }
                int max = 0; 
                String[] contents = s.split("\n\n");
                for(int i = 0;i< contents.length; i++){
                    String[] temp = contents[i].split("\n",3);
                    String id = temp[0];
                    int time = Integer.parseInt(temp[1]);
                    if(time > max){
                        max = time; 
                    }
                    String content = temp[2];
                    Timer t = new Timer();
                    Item item = new Item(t,content,time);
                    storedItems.storeData(id, item);
                }
                return max;
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                return 0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        /*
        Initalise ADT for storing items and lamport clock
        Checks for fault tollerance and use it to set the lamport clock
        Creates a server socket and accepts all connections
        Each connection is passed to a multi threaded Agreworker class to process the connection
        */

        StoredItems storedItems = new StoredItems();
        LamportClock clock = new LamportClock();

        if(args.length == 0){
            try {
                clock.val = faultTollerance(storedItems);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(1);

            }
        }else{
            try {
                clock.val = startup(Integer.parseInt(args[0]), storedItems);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(1);

            }
        }


        System.out.println("Started Aggregation Server");

        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(4567);
        }catch(Exception e){
            System.out.println(e);
            System.exit(1);
        }  

        while(true){
            try
            {
                AgreWorker worker = new AgreWorker(serverSocket.accept(),storedItems, clock);
                new Thread(worker).start();
            }catch(Exception e)
            {
                System.out.println(e);
                System.exit(1);

            }  
        }
    }
}
