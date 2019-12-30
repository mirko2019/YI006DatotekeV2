package yi.programiranje.datoteke.kgi.help;

public class HelpItem {
	private String title; 
	private String content; 
	
	public HelpItem(String title, String content) {
		this.title = title;
		this.content = content; 
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content; 
	}
}
