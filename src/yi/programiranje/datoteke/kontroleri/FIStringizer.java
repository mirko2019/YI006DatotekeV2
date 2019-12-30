package yi.programiranje.datoteke.kontroleri;

import yi.programiranje.datoteke.model.FileItem;

public class FIStringizer {
	private FileItem item; 
	
	public FIStringizer(FileItem item) {
		this.item = item;
	}
	
	public FileItem getItem() {
		return item;
	}
	
	public void setItem(FileItem item) {
		this.item = item; 
	}
	
	public String stringize() {
		if(item.isFolder()) return asFDStringizer().stringize(); 
		if(item.isData()) return asFFStringizer().stringize(); 
		return item.toString(); 
	}
	
	
	public FFStringizer asFDStringizer() {
		if(item.isFolder()) return new FFStringizer(item.asFolder());
		return null;
	}
	
	public FDStringizer asFFStringizer() {
		if(item.isData()) return new FDStringizer(item.asData());
		return null;
	}
}
