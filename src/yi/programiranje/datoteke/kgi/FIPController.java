package yi.programiranje.datoteke.kgi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;

import yi.programiranje.datoteke.kgi.aplikacija.ApplicationCenter;
import yi.programiranje.datoteke.kgi.help.HelpItem;
import yi.programiranje.datoteke.kgi.help.HelpLoader;
import yi.programiranje.datoteke.kgi.konfiguracije.Configuration;
import yi.programiranje.datoteke.kontroleri.BasicBinaryHexprintFDStringizer;
import yi.programiranje.datoteke.model.FileData;
import yi.programiranje.datoteke.model.FileFolder;
import yi.programiranje.datoteke.model.FileItem;

public class FIPController implements Initializable{
	public static class FileLabel extends Label{
		private FileItem item; 
		public FileLabel(FileItem item) {
			super(item.getName()); 
			this.item = item; 
		}
		
		public FileItem getItem() {
			return item; 
		}
	}
	
	@FXML private TextField currentRoot;
	@FXML private Button changeRoot; 
	@FXML private Button help; 
	
	@FXML private TreeView<FileLabel> contentPreview;  
	@FXML private TextField currentItem; 
	@FXML private TextField currentItemType;
	@FXML private Button left; 
	@FXML private Button right;
	@FXML private Button up; 
	@FXML private Button down;
	
	@FXML private TextArea contentView; 
	@FXML private TextField countPages; 
	@FXML private TextField currentPageNo; 
	@FXML private Button navigationUp; 
	@FXML private Button navigationDown;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contentPreview.getStylesheets().add("/yi/programiranje/datoteke/kgi/style-treeview-ec.fxcss"); 
		contentView.setFont(Font.loadFont(getClass().getResourceAsStream("/yi/programiranje/datoteke/kgi/font-cour.ttf"), 12));
		countPages.setFont(Font.loadFont(getClass().getResourceAsStream("/yi/programiranje/datoteke/kgi/font-courbd.ttf"), 12));
		currentPageNo.setFont(Font.loadFont(getClass().getResourceAsStream("/yi/programiranje/datoteke/kgi/font-courbd.ttf"), 12));
		currentItem.setFont(Font.loadFont(getClass().getResourceAsStream("/yi/programiranje/datoteke/kgi/font-courbd.ttf"), 12));
		currentItemType.setFont(Font.loadFont(getClass().getResourceAsStream("/yi/programiranje/datoteke/kgi/font-courbd.ttf"), 12));
		currentRoot.setText(ApplicationCenter.getRootFile().getAbsolutePath());
		contentPreview.getSelectionModel().selectedItemProperty().addListener((e,o,n)->{
			if(n==null) {
				currentItem.setText("");
				currentItemType.setText("");
			}else {
				FileItem fi = n.getValue().item;
				if(fi.isFolder()) {
					FileFolder fo = fi.asFolder();
					try {
						currentItem.setText(fo.getFile().getCanonicalPath());
						currentItemType.setText("Директоријум");
						currentItem.selectEnd();
						ApplicationCenter.setDataStringizer(null);
						contentView.clear(); 
						countPages.clear();
						currentPageNo.clear();
					}catch(Exception ex) {
						throw new RuntimeException(ex);
					}
				}else if(fi.isData()) {
					FileData fd = fi.asData();
					try {
						currentItem.setText(fd.getFile().getCanonicalPath());
						currentItemType.setText("Датотека");
						currentItem.selectEnd();
						try {
							File file = new File(currentItem.getText());
							if(!file.exists()) return;
							if(!file.isFile()) return;
							FileData data = new FileData(file);
							BasicBinaryHexprintFDStringizer engine = new BasicBinaryHexprintFDStringizer(data);
							ApplicationCenter.setDataStringizer(engine);
							contentView.setText(ApplicationCenter.getDataStringizer().stringize());
							countPages.setText(""+ApplicationCenter.getDataStringizer().getController().getPagesCount());
							currentPageNo.setText(""+ApplicationCenter.getDataStringizer().getController().getPageNo());
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}catch(Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		});
		contentPreview.setRoot(new TreeItem<>(new FileLabel(ApplicationCenter.getRoot())));
		contentPreview.getSelectionModel().select(0); 
		final var item = contentPreview.getSelectionModel().getSelectedItem(); 
		item.expandedProperty().addListener((e,o,n)->{
			if(n)
				expand(item);
			else
				colapse(item);  
		});
		expandLevel(item);
		contentPreview.refresh();
		contentPreview.applyCss();
	}
	
	private synchronized void expandLevel(TreeItem<FileLabel> selected) {
		if(selected==null) return;
		ArrayList<File> files = new ArrayList<File>();
		if(selected.getValue().getItem() instanceof FileData) return; 
		for(File file: selected.getValue().getItem().getFile().listFiles()) {
			files.add(file); 
		}
		selected.getChildren().clear();
		Collections.sort(files);
		for(File file: files) {
			if(file.isDirectory()) {
				FileItem item = new FileFolder(file);
				TreeItem<FileLabel> treeItem = new TreeItem<>();  
				treeItem.setValue(new FileLabel(item));
				treeItem.expandedProperty().addListener((e,o,n)->{
					contentPreview.getSelectionModel().select(treeItem);
					if(n)
						expand(treeItem);
					else
						colapse(treeItem); 
				});
				treeItem.setExpanded(false);
				selected.getChildren().add(treeItem);
			}else if(file.isFile()) {
				FileItem item = new FileData(file);
				TreeItem<FileLabel> treeItem = new TreeItem<>();  
				treeItem.setValue(new FileLabel(item));
				treeItem.expandedProperty().addListener((e,o,n)->{
					contentPreview.getSelectionModel().select(treeItem);
					if(n)
						expand(treeItem);
					else
						colapse(treeItem); 
				});
				treeItem.setExpanded(false);
				selected.getChildren().add(treeItem);
			}
		}
		contentPreview.refresh();
		contentPreview.applyCss();
	}
	
	private synchronized void expand(TreeItem<FileLabel> selectedItem) {
		if(selectedItem==null) return;
		if(selectedItem.getValue().getItem() instanceof FileData) return;
		for(var selected: selectedItem.getChildren()) {
			if(!(selected.getValue().getItem() instanceof FileFolder)) continue;
			ArrayList<File> files = new ArrayList<File>();
			for(File file: selected.getValue().getItem().getFile().listFiles()) {
				files.add(file); 
			}
			Collections.sort(files);
			for(File file: files) {
				if(file.isDirectory()) {
					FileItem item = new FileFolder(file);
					TreeItem<FileLabel> treeItem = new TreeItem<>();  
					treeItem.setValue(new FileLabel(item));					
					treeItem.expandedProperty().addListener((e,o,n)->{
						Platform.runLater(()->{
							contentPreview.getSelectionModel().select(treeItem);
							if(n)
								expand(treeItem);
							else
								colapse(treeItem); 
						}); 
					});
					selected.getChildren().add(treeItem);
				}else if(file.isFile()) {
					FileItem item = new FileData(file);
					TreeItem<FileLabel> treeItem = new TreeItem<>();  
					treeItem.setValue(new FileLabel(item));
					treeItem.expandedProperty().addListener((e,o,n)->{
						contentPreview.getSelectionModel().select(treeItem);
						Platform.runLater(
							()->{
								if(n)
									expand(treeItem);
								else
									colapse(treeItem); 
							}
						); 
					});
					selected.getChildren().add(treeItem);
				}
			} 
		}
		contentPreview.refresh();
		contentPreview.applyCss();
	}
		
	
	private synchronized void colapse(TreeItem<FileLabel> selected) {
			if(selected==null) return;
			selected.getChildren().clear();
			selected.setExpanded(false);
			this.expandLevel(selected);
			contentPreview.refresh();
			contentPreview.applyCss();
	}
	
	@FXML
	private void currentRootKeyPress(KeyEvent event) {
		if(event.getCode()==KeyCode.ENTER) {
			File file = new File(currentRoot.getText());
			if(file.exists()) {
				try {
					file = new File(file.getCanonicalPath());
					ApplicationCenter.setRootFile(file);
					Configuration.generalProperties.setProperty(Configuration.FILE_ROOT, file.getCanonicalPath());
					contentPreview.setRoot(new TreeItem<>(new FileLabel(ApplicationCenter.getRoot())));
					contentPreview.getSelectionModel().select(0); 
					final var item = contentPreview.getSelectionModel().getSelectedItem(); 
					item.expandedProperty().addListener((e,o,n)->{
						if(n)
							expand(item);
						else
							colapse(item); 
					});
					expandLevel(item);
					contentPreview.refresh();
					contentPreview.applyCss();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			} 
			else {
				currentRoot.setText(ApplicationCenter.getRootFile().getPath());
			}
		}
	}
	
	@FXML
	private void changeRootClick(ActionEvent event) {
		DirectoryChooser dialog = new DirectoryChooser();
		dialog.setTitle("Датотеке и фасцикле - избор коријенског директоријума");
		dialog.setInitialDirectory(ApplicationCenter.getRootFile());
		File file = dialog.showDialog(Program.getMainStage());
		if(file == null) return; 
		if(!file.exists()) return;
		if(!file.isDirectory()) return; 
		try {
			file = new File(file.getCanonicalPath());
			ApplicationCenter.setRootFile(file);
			Configuration.generalProperties.setProperty(Configuration.FILE_ROOT, file.getCanonicalPath());
			currentRoot.setText(ApplicationCenter.getRootFile().getPath());
			contentPreview.setRoot(new TreeItem<>(new FileLabel(ApplicationCenter.getRoot())));
			contentPreview.getSelectionModel().select(0); 
			final var item = contentPreview.getSelectionModel().getSelectedItem(); 
			item.expandedProperty().addListener((e,o,n)->{
				if(n)
					expand(item);
				else
					colapse(item); 
			});
			expandLevel(item);
			contentPreview.refresh();
			contentPreview.applyCss();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	private void helpClick(ActionEvent event) {
		Map<String,HelpItem> helpMap = HelpLoader.getEmbbededHelpMap(); 
		HelpItem appsHelp = helpMap.get(HelpLoader.APLIKACIJE_HELP); 
		HelpItem filesHelp = helpMap.get(HelpLoader.DATOTEKE_HELP); 
		HelpItem dirsHelp = helpMap.get(HelpLoader.DIREKTORIJUMI_HELP);
		HelpItem configsHelp = helpMap.get(HelpLoader.KONFIGURACIJE_HELP); 
		Alert helps = new Alert(AlertType.INFORMATION);
		TabPane tabs = new TabPane();
		Tab appsHelpTab = new Tab(appsHelp.getTitle());
		Tab filesHelpTab = new Tab(filesHelp.getTitle());
		Tab dirsHelpTab = new Tab(dirsHelp.getTitle());
		Tab configsHelpTab = new Tab(configsHelp.getTitle());
		appsHelpTab.setClosable(false);
		filesHelpTab.setClosable(false);
		dirsHelpTab.setClosable(false);
		configsHelpTab.setClosable(false);
		TextArea appsHelpText = new TextArea();
		TextArea filesHelpText = new TextArea(); 
		TextArea dirsHelpText = new TextArea();
		TextArea configsHelpText = new TextArea();
		appsHelpText.setEditable(false);
		filesHelpText.setEditable(false);
		dirsHelpText.setEditable(false);
		configsHelpText.setEditable(false);
		appsHelpTab.setContent(appsHelpText);
		filesHelpTab.setContent(filesHelpText);
		dirsHelpTab.setContent(dirsHelpText);
		configsHelpTab.setContent(configsHelpText);
		appsHelpText.setText(appsHelp.getContent());
		filesHelpText.setText(filesHelp.getContent());
		dirsHelpText.setText(dirsHelp.getContent());
		configsHelpText.setText(configsHelp.getContent());
		tabs.getTabs().addAll(appsHelpTab, filesHelpTab, dirsHelpTab, configsHelpTab);
		helps.setTitle("Датотеке и фасцикле");
		helps.setHeaderText("Помоћ и информације");
		helps.getDialogPane().setContent(tabs);
		helps.showAndWait();
	}
	
	@FXML 
	private void dirDownAction(ActionEvent event) {
		int selectedIndex = contentPreview.getSelectionModel().getSelectedIndex(); 
		if(selectedIndex==-1) contentPreview.getSelectionModel().select(0);
		else contentPreview.getSelectionModel().select(selectedIndex+1);
	}
	
	@FXML 
	private void dirUpAction(ActionEvent event) {
		int selectedIndex = contentPreview.getSelectionModel().getSelectedIndex(); 
		if(selectedIndex==-1) contentPreview.getSelectionModel().select(0);
		else contentPreview.getSelectionModel().select(Math.max(selectedIndex-1,0));
	}
	
	@FXML
	private void dirLeftAction(ActionEvent event) {
		int selectedIndex = contentPreview.getSelectionModel().getSelectedIndex(); 
		if(selectedIndex==-1) contentPreview.getSelectionModel().select(0);
		else {
			colapse(contentPreview.getSelectionModel().getSelectedItem());
			contentPreview.getSelectionModel().getSelectedItem().setExpanded(false);
		}
	}
	
	@FXML 
	private void dirRightAction(ActionEvent event) {
		int selectedIndex = contentPreview.getSelectionModel().getSelectedIndex(); 
		if(selectedIndex==-1) contentPreview.getSelectionModel().select(0);
		else {
			colapse(contentPreview.getSelectionModel().getSelectedItem());
			contentPreview.getSelectionModel().getSelectedItem().setExpanded(false);
			expand(contentPreview.getSelectionModel().getSelectedItem());
			contentPreview.getSelectionModel().getSelectedItem().setExpanded(true);
		}
	}
	
	@FXML 
	private void navigationUp(ActionEvent event) {
		if(ApplicationCenter.getDataStringizer()==null) return;
		if(ApplicationCenter.getDataStringizer()!=null) {
			long x = ApplicationCenter.getDataStringizer().getController().getPageNo();
			if(x>0)
				x--;
			ApplicationCenter.getDataStringizer().getController().setPageNo((int)x);
			currentPageNo.setText(""+x);
			contentView.setText(ApplicationCenter.getDataStringizer().stringize());
		}
	}
	
	@FXML
	private void navigationDown(ActionEvent event) {
		if(ApplicationCenter.getDataStringizer()==null) return;
		if(ApplicationCenter.getDataStringizer()!=null) {
			long x = ApplicationCenter.getDataStringizer().getController().getPageNo();
			if(x<ApplicationCenter.getDataStringizer().getController().getPagesCount())
				x++;
			ApplicationCenter.getDataStringizer().getController().setPageNo((int)x);
			currentPageNo.setText(""+x);
			contentView.setText(ApplicationCenter.getDataStringizer().stringize());
		}
	}
	
	@FXML 
	private void currentPageNoKeyPress(KeyEvent event) {
		if(event.getCode()==KeyCode.ENTER) {
			if(ApplicationCenter.getDataStringizer()==null) return;
			ApplicationCenter.getDataStringizer().getController().getPageNo(); 
			try {
				int x = Integer.parseInt(currentPageNo.getText());
				if(x<0) x = 0;
				if(ApplicationCenter.getDataStringizer() == null) {
					contentView.setText("0");
					currentPageNo.setText("0");
					ApplicationCenter.getDataStringizer().getController().setPageNo(0);
				}else {
					if(x>=ApplicationCenter.getDataStringizer().getController().getPagesCount())
						x = (int) ApplicationCenter.getDataStringizer().getController().getPagesCount();
					currentPageNo.setText(""+x);
					ApplicationCenter.getDataStringizer().getController().setPageNo(x);
					contentView.setText(ApplicationCenter.getDataStringizer().stringize()); 
				}
			}catch(Exception ex) {
				if(ApplicationCenter.getDataStringizer() == null) return;
				ApplicationCenter.getDataStringizer().getController().setPageNo(0);
				contentView.setText("0");
				currentPageNo.setText("0");
			}
		}
	}
}
