
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class XMLparser {
    // Converts all the string of contents into XML versions of itself
    String convertToXML(String content){
        String ret = new String(); 


        String body = new String(); 
        int sum = 0;

        String[] lines = content.split("\n");
        for(int i = 0; i < lines.length; i++){
            String[] temp = lines[i].split(":",2);
            if(temp.length < 2){
                return lines[i];
            }
            String title = temp[0];
            String data = temp[1];
            sum += data.length();
            String add = new String();

            switch(title){
                case "title":
                add = "<title>" + data + "</title>\n";
                break;
                case "subtitle":
                add = "<subtitle>" + data + "</subtitle>\n";
                break;
                case "link":
                add = "<link>" + data + "</link>\n";
                break;
                case "updated":
                add = "<updated>" + data + "</updated>\n";
                break;
                case "author":
                add = "<author>" + data + "</author>\n";
                break;
                case "name":
                add = "<name>" + data + "</name>\n";
                break;
                case "id":
                add = "<id>" + data + "</id>\n";
                break;
                case "entry":
                add = "<entry>" + data + "</entry>\n";
                break;
                case "summary":
                add = "<summary>" + data + "</summary>\n";
                break;
            }

            body += add;
        }

        ret +=  "PUT /atom.xml HTTP/1.1\n";
        ret +=  "User-Agent: ATOMClient/1/0\n";
        ret +=  "Content-Type: text/xml\n";
        ret +=  "Content-Length: " + sum + "\n";
        ret +=  "<?xml version='1.0' encoding='iso-8859-1' ?>\n";
        ret +=  "<feed xml:lang=\"en-US\" xmlns=\"http://www.w3.org/2005/Atom\">\n";
        ret += body;
        ret +=  "</feed>\n";
        
        
        return ret; 
    }


    //Given an input string
    //Checks if the given string is a valid data to store
    //Returns original data if valid, else return -1
    public String isValidXML(String data){
        
        String[] fields = data.split("\n");
        if(fields.length < 2) return "-1";
        int sum = 0;

        if(!fields[6].substring(1,6).equals("title")){
            return "-1";
        }
        String ret = new String();
        for(int i = 6; i<fields.length-1; i++){
            ret += fields[i] + "\n";
            String temp[] = fields[i].split(">",2);
            String temp2[] = temp[1].split("<",2);
            if(temp2[0].length() == 0) return "-1" ;
            sum += temp2[0].length();
        }
        if(!fields[0].equalsIgnoreCase("PUT /atom.xml HTTP/1.1")) return "-1";
        if(!fields[1].equalsIgnoreCase("User-Agent: ATOMClient/1/0")) return "-1";
        if(!fields[2].equalsIgnoreCase("Content-Type: text/xml")) return "-1";
        if(!fields[3].equalsIgnoreCase("Content-Length: " + sum)) return "-1";
        if(!fields[4].equalsIgnoreCase("<?xml version='1.0' encoding='iso-8859-1' ?>")) return "-1";
        if(!fields[5].equalsIgnoreCase("<feed xml:lang=\"en-US\" xmlns=\"http://www.w3.org/2005/Atom\">")) return "-1";
        return ret;
    }
    
    // //converts XML string to normal String
    String readFromXML(String  content){
        String ret = new String(); 
        return ret;
    }
    
}
