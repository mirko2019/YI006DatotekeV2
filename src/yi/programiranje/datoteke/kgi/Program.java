package yi.programiranje.datoteke.kgi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import yi.programiranje.datoteke.kgi.help.HelpLoader;
import yi.programiranje.datoteke.kgi.konfiguracije.Configuration;

public class Program extends Application{
	private static Stage mainStage; 
	
	public static Stage getMainStage() {
		return mainStage; 
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/yi/programiranje/datoteke/kgi/FilePreview.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(500d);
		primaryStage.setMinWidth(1000d);
		primaryStage.setTitle("Датотеке и фасцикле");
		primaryStage.show();
		mainStage = primaryStage; 
	}
	
	public static void main(String ... args) {
		Configuration.open(); 
		HelpLoader.loadEmbeddedHelpMap();
		Application.launch(args);
		Configuration.save();
	}
}
