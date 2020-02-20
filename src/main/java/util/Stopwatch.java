package util;

import java.time.Duration;
import java.time.LocalTime;
/**
 * <b>Stopwatch</b> <br>
 * <br>
 * 
 * Cronometro utilizado para contar el tiempo de ronda en juegos
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Stopwatch extends Thread {

	private static LocalTime startTime;

	public static String handle(long now, LocalTime start) {
		startTime = start;
		String time = "";
		long elapsedSeconds = Duration.between(startTime, LocalTime.now()).getSeconds();
		int seconds = (int) (elapsedSeconds % 60);
		long minutes = elapsedSeconds / 60;
		if (minutes < 10 && seconds < 10) {
			time = "0" + minutes + ":0" + seconds;
		} else {
			if (minutes < 10) {
				time = "0" + minutes + ":" + seconds;
			} else {
				if (seconds < 10) {
					time = minutes + ":0" + seconds;
				}
			}
		}
		return time;

	}
}