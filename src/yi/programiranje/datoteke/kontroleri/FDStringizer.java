package yi.programiranje.datoteke.kontroleri;

import yi.programiranje.datoteke.model.FileData;

public class FDStringizer {
	private FileData data;
	
	public FDStringizer(FileData data) {
		this.data = data; 
	}
	
	public FileData getData() {
		return data;
	}
	
	public String stringize() {
		return data.toString(); 
	}
}
