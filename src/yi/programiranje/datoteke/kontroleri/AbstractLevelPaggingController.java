package yi.programiranje.datoteke.kontroleri;

import java.util.List;

import yi.programiranje.datoteke.model.FileFolder;
import yi.programiranje.datoteke.model.FileItem;

public  abstract class AbstractLevelPaggingController {
	public LevelPaggingController asLevelPaggingController() {
		if(this instanceof LevelPaggingController)
			return (LevelPaggingController) this; 
		return null; 
	}
	
	public abstract FileFolder getSource(); 
	public abstract int getSize();
	public abstract int getPageNo();
	
	public abstract int countItems();
	public abstract int countPages(); 
	
	public abstract void setPageNo(int pageNo);
	public abstract void gotoLastPage();
	public abstract void gotoFirstPage(); 
	
	public abstract void up();
	public abstract void down();
	
	public abstract List<FileItem> getAll();
	public abstract List<FileItem> getPagedRequire();
	public abstract AbstractStringPaggingController getAllString();
	
	public abstract AbstractStringPaggingController getRequiredString(); 
	public abstract int getDefaultWidthSize(); 
	public abstract void setDefaultWidthSize(int defaultWidthSize); 
}
