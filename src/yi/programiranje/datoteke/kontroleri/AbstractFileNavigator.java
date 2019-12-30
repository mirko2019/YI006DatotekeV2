package yi.programiranje.datoteke.kontroleri;

import java.util.List;

import yi.programiranje.datoteke.model.FileFolder;
import yi.programiranje.datoteke.model.FileItem;

public abstract class AbstractFileNavigator {
	public  FileNavigator asFileNavigator() {
		if(this instanceof FileNavigator) {
			return (FileNavigator) this; 
		}
		return null; 
	}
	
	public abstract FileFolder getDirectry(); 
	public abstract FileItem getCurrent();
	
	public abstract void stepRight(String name); 
	public abstract void stepLeft(); 
	public abstract void stepDown(); 
	public abstract void setpUp();
	public abstract void expandRight();
	public abstract void expandDown(); 
	public abstract void expandUp(); 
	public abstract void colapse();
	public abstract void applyScreenLocal(); 
	public abstract void applyScreenGeneral(); 
	
	public abstract List<String> open(); 
	public abstract List<String> openAll(); 
	public abstract List<String> openFiles(); 
	public abstract List<String> openFolders(); 
	public abstract AbstractLevelPaggingController getFor(String path);
	public abstract BinnaryContentPaggingController getForFile(String path);
}
