package yi.programiranje.datoteke.kontroleri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import yi.programiranje.datoteke.model.FileFolder;
import yi.programiranje.datoteke.model.FileItem;

public class FileNavigator extends AbstractFileNavigator{
	private FileFolder directory;
	private FileItem current; 
	private int defaultHeigthSize = 25; 
	private int defaultWidthSize = 80;
	
	private HashMap<String, LevelPaggingController> open = new HashMap<>();
	private HashMap<String, BinnaryContentPaggingController> openFiles = new HashMap<>(); 
	
	public FileNavigator(FileFolder folder) {
		directory = folder;
		current = folder; 
		LevelPaggingController controller = new LevelPaggingController(directory, defaultHeigthSize, defaultWidthSize);
		open.put(directory.generalRelativePath(), controller);
	}
	
	@Override
	public FileFolder getDirectry() {
		return directory;
	}
	
	@Override
	public FileItem getCurrent() {
		return current;
	}
	
	@Override public void stepRight(String name) {}
	@Override public void stepLeft() {}
	@Override public void stepDown() {}
	@Override public void setpUp() {}
	@Override public void expandRight() {}
	@Override public void expandDown() {}
	@Override public void expandUp() {}
	@Override public void colapse() {}
	@Override public void applyScreenLocal() {}
	@Override public void applyScreenGeneral() {}
	
	@Override
	public synchronized List<String> open(){
		return openAll(); 
	}
	
	@Override
	public synchronized List<String> openAll() {
		ArrayList<String> opened = new ArrayList<>(); 
		opened.addAll(openFiles()); 
		opened.addAll(openFolders());
		return opened;
	}
	
	@Override
	public synchronized List<String> openFiles() {
		ArrayList<String> opened = new ArrayList<>(openFiles.keySet());
		Collections.sort(opened);
		return opened;
	}

	@Override
	public synchronized List<String> openFolders() {
		ArrayList<String> opened = new ArrayList<>(open.keySet());
		Collections.sort(opened);
		return opened;
	}
	@Override
	public synchronized BinnaryContentPaggingController getForFile(String path) {
		return openFiles.get(path);
	}

	@Override
	public synchronized LevelPaggingController getFor(String path) {
		return open.get(path);
	}

	public int getDefaultHeigthSize() {
		return defaultHeigthSize;
	}

	public void setDefaultHeigthSize(int defaultHeigthSize) {
		if(defaultHeigthSize<1) defaultHeigthSize = 1;
		this.defaultHeigthSize = defaultHeigthSize;
	}

	public int getDefaultWidthSize() { 
		return defaultWidthSize;
	}

	public void setDefaultWidthSize(int defaultWidthSize) {
		if(defaultWidthSize<1) defaultWidthSize = 1;
		this.defaultWidthSize = defaultWidthSize;
	}
}
