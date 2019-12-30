package yi.programiranje.datoteke.kontroleri;

import yi.programiranje.datoteke.model.FileFolder;

public class FFStringizer {
	private FileFolder directory; 
	
	public FFStringizer(FileFolder directory) {
		this.directory = directory; 
	}
	
	public FileFolder  getDirectory() {
		return directory;
	}
	
	public String stringize() {
		return directory.toString(); 
	}
}
