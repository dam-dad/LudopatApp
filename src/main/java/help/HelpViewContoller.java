package help;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class HelpViewContoller {
	
	@FXML
	private BorderPane view;
//		@FXML
//		private WebView webview;
	
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
	
	private void loadInitialContent() {
		//TODO cambiar el contenido inicial al requerido por el generationCode.
//		webview.setContent(getClass().getResource("/ui/html/ayuda/" + generationCode + ".html"));
	}
	
}
