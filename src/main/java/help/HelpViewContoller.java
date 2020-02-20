package help;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
/**
 * <b>HelpViewController</b> <br>
 * <br>
 * 
 * Controlador del WebView de la ayuda básica del juego
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class HelpViewContoller {
	
	@FXML
	private VBox view;
	
	WebView webView;
	
	private String generationCode;
	
	public HelpViewContoller(String generationCode) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/help/HelpViewer.fxml"));
			loader.setController(this);
			loader.load();
			
			this.generationCode = generationCode;
			
			loadInitialContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Carga el contenido inicial
	 */
	private void loadInitialContent() {
		//TODO cambiar el contenido inicial al requerido por el generationCode.

        webView = new WebView();

        webView.getEngine().load(getClass().getResource("/ui/html/" + generationCode + ".html").toString());
        
        webView.setId("content");
        
        this.view.getChildren().add(webView);
	}
	
	public VBox getView() {
		return this.view;
	}
	
}
