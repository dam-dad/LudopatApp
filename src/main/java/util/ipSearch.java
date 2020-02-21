package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
		
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "UNKNOWN";
	}

}
