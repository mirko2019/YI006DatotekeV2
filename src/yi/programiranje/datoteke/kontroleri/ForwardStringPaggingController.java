package yi.programiranje.datoteke.kontroleri;

/**
 * Краткоспојни контролер који само форме ради управља страничењем стринга, а 
 * уствари само прослеђује цијели стринг и на дате функционалности и на функционалности 
 * гдје се даје страничен стринг. 
 * @author Computer
 *
 */
public class ForwardStringPaggingController extends AbstractStringPaggingController{
	private String string = ""; 
	private int level = 0; 
	private String tabStop = "   "; 
	
	public  ForwardStringPaggingController(String string) {
		this.string = string; 
	}
	
	@Override
	public int getPageNo() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public void setPageNo(int pageNo) {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public int getWidthPageNo() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public void setWidthPageNo(int widthPageNo) {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");		
	}

	@Override
	public String getString() {
		return string;
	}

	@Override
	public int getSize() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public int getWidth() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
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
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public int countWidthPages() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public String toHeightPaggedString() {
		return tabbedString();
	}

	@Override
	public String toWithPaggedString() {
		return tabbedString();
	}

	@Override
	public String toCompletePaggedString() {
		return tabbedString();
	}

	@Override
	public void up() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public void down() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public void left() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
	}

	@Override
	public void right() {
		throw new UnsupportedOperationException("U ovom modelu operacije za stranice nisu podrzane.");
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
