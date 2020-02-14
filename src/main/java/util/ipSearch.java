package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ipSearch {
	
	

	public static String ip() {
		String systemipaddress = ""; 
        try
        { 
            URL url_name = new URL("http://bot.whatismyipaddress.com"); 
  
            BufferedReader sc = 
            new BufferedReader(new InputStreamReader(url_name.openStream())); 
  
            // reads system IPAddress 
            systemipaddress = sc.readLine().trim(); 
        } 
        catch (Exception e) 
        { 
            systemipaddress = "Cannot Execute Properly"; 
        } 
		return systemipaddress;
	}

}
