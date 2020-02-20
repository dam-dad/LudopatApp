package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
/**
 * <b>ipSearch</b> <br>
 * <br>
 * 
 * Método de busqueda de la IP del usuario
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class ipSearch {
	/**
	 * Busca la ip del usuario
	 * @return IP del usuario
	 */
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
