package yi.programiranje.datoteke.kontroleri;

public abstract class AbstractStringPaggingController {
	public StringPaggingController asStringPaggingController() {
		if(this instanceof StringPaggingController) {
			return (StringPaggingController) this; 
		}
		return null;
	}
	
	public abstract int getPageNo();
	public abstract void setPageNo(int pageNo);
	public abstract int getWidthPageNo();
	
	public abstract void setWidthPageNo(int widthPageNo);
	public abstract String getString(); 
	
	public abstract int getSize();
	public abstract int getWidth();
	
	public abstract int countLines();
	public abstract int maxLineLength();
	
	public abstract int countHeightPages();
	public abstract int countWidthPages();
	
	public abstract String toHeightPaggedString();
	public abstract String toWithPaggedString();
	public abstract String toCompletePaggedString(); 
	
	public abstract void up(); 
	public abstract void down();
	public abstract void left();
	
	public abstract void right();
	public abstract String getTabStop();
	
	public abstract void setTabStop(String tabStop); 
	public abstract  int getLevel();
	public abstract void setLevel(int level);
	
	public abstract String preparedTab();
	public abstract String tabbedString(); 
}
