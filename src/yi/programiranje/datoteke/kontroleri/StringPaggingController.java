package yi.programiranje.datoteke.kontroleri;

public class StringPaggingController extends  AbstractStringPaggingController{
	private String string = ""; 
	private int size=25; 
	private int pageNo=0; 
	private int width=80; 
	private int widthPageNo=0; 
	private String tabStop = "   ";
	private int level = 0;
	
	public StringPaggingController(String string) {
		this.string = string; 
	}
	public StringPaggingController(String string, int size, int width) {
		this.string = string; 
		this.size = size; 
		this.width = width; 
		if(size<1) size = 1; 
		if(width<1) width = 1; 
	}
	@Override
	public int getPageNo() {
		return pageNo;
	}
	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if(pageNo < 0) pageNo=0; 
	}
	@Override
	public int getWidthPageNo() {
		return widthPageNo;
	}
	
	@Override
	public void setWidthPageNo(int widthPageNo) {
		this.widthPageNo = widthPageNo;
		if(widthPageNo < 0) widthPageNo = 0;
	}
	@Override
	public String getString() {
		return string;
	}
	@Override
	public int getSize() {
		return size;
	}
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int countLines() {
		String[] parts= string.split("\n");
		return parts.length; 
	}
	
	@Override
	public int maxLineLength() {
		int ml = 0;
		String[] parts= string.split("\n");
		for(String part: parts)
			ml = Math.max(ml, part.length());
		return ml; 
	} 
	
	@Override
	public int countHeightPages() {
		return countLines()/getSize()+(countLines()%getSize()==0?1:0);
	}
	
	@Override
	public int countWidthPages() {
		return maxLineLength()/getWidth()+(maxLineLength()%getWidth()==0?1:0);
	}
	
	@Override
	public String toHeightPaggedString() {
		String res = "";
		String[] parts = string.split("\n");
		for(int i=pageNo*getSize(); i<Math.min(parts.length, pageNo*getSize()+getSize()); i++) {
			res += (i==0?preparedTab():"")+parts[i]+"\n";
		}
		return res;
	}
	
	@Override
	public String toWithPaggedString() {
		String res = "";
		String[] parts = string.split("\n");
		for(int i=0; i<parts.length; i++) {
			res += (i==0?preparedTab():"")+parts[i].substring(this.widthPageNo, Math.min(widthPageNo+width, parts[i].length()))+"\n";
		}
		return res;
	}
	
	@Override
	public String toCompletePaggedString() {
		String res = "";
		String[] parts = string.split("\n");
		for(int i=pageNo*getSize(); i<Math.min(parts.length, pageNo*getSize()+getSize()); i++) {
			res += (i==0?preparedTab():"") +parts[i].substring(this.widthPageNo, Math.min(widthPageNo+width, parts[i].length()))+"\n";
		}
		return res;
	}
	
	@Override
	public void up() {
		pageNo--;
		if(pageNo<0) pageNo = 0;
	}
	
	@Override
	public void down() {
		pageNo++;
		if(pageNo>=countWidthPages())
			pageNo=countWidthPages()-1;
	}
	
	@Override
	public void left() {
		widthPageNo--;
		if(widthPageNo<0)
			widthPageNo=0;
	}
	
	@Override
	public void right() {
		widthPageNo++; 
		if(widthPageNo>countWidthPages())
			widthPageNo=countWidthPages()-1;
	}
	
	
	@Override
	public String getTabStop() {
		return tabStop;
	}
	@Override
	public void setTabStop(String tabStop) {
		this.tabStop = tabStop;
	}
	@Override
	public int getLevel() {
		return level;
	}
	@Override
	public void setLevel(int level) {
		this.level = level;
		if(level<0) level = 0; 
	}
	
	
	@Override
	public String preparedTab() {
		String res = "";
		for(int i=0; i<level; i++)
			res+=tabStop; 
		return res; 
	}
	
	@Override
	public String tabbedString() {
		String res = "";
		String[] parts = string.split("\n");
		for(String part:parts)
			res+=preparedTab()+part+"\n"; 
		return res; 
	}
}
