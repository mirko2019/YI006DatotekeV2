package yi.programiranje.datoteke.kgi;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DAPController implements Initializable{
	@FXML private TextArea contentView; 
	@FXML private TextField countPages; 
	@FXML private TextField currentPageNo; 
	@FXML private Button navigationUp; 
	@FXML private Button navigationDown;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
