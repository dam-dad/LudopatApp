package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ipSearch {
	
	public static String ip() {
		
		String systemipaddress = ""; 
        try
        { 
        	URL whatismyip = new URL("http://checkip.amazonaws.com");
        	BufferedReader in = new BufferedReader(new InputStreamReader(
        	                whatismyip.openStream()));

        	String ip = in.readLine().trim(); //you get the IP as a String
        	
        	return ip;
        } 
        catch (Exception e) 
        { 
        	e.printStackTrace();
            systemipaddress = "UNKNOWN"; 
        } 
        
		return systemipaddress;
	}

}
