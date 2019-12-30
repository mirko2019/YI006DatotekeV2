package yi.programiranje.datoteke.kgi;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import yi.programiranje.datoteke.model.FileItem;

public class FOPController implements Initializable{
	@FXML private TreeView<FileItem> contentPreview;  
	@FXML private TextField currentItem; 
	@FXML private TextField currentItemType;
	@FXML private Button left; 
	@FXML private Button right;
	@FXML private Button up; 
	@FXML private Button down;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
