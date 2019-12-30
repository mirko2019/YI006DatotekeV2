package yi.programiranje.datoteke.kgi.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.TreeMap;

public final class HelpLoader {
	public static final String APLIKACIJE_HELP = "/yi/programiranje/datoteke/kgi/help/help-aplikacija.txt"; 
	public static final String DATOTEKE_HELP = "/yi/programiranje/datoteke/kgi/help/help-datoteke.txt"; 
	public static final String DIREKTORIJUMI_HELP = "/yi/programiranje/datoteke/kgi/help/help-direktorijumi.txt";
	public static final String KONFIGURACIJE_HELP = "/yi/programiranje/datoteke/kgi/help/help-konfiguracije.txt";
	
	private static TreeMap<String, HelpItem> embeddedHelpMap = new TreeMap<>();
	
	private HelpLoader() {}
	
	public static HelpItem loadHelp(File file) {
		try {
			FileInputStream fis = new FileInputStream(file); 
			return loadHelp(fis);
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static HelpItem loadHelp(String helpRscName) {
		InputStream is = HelpLoader.class.getResourceAsStream(helpRscName); 
		return loadHelp(is);
	}
	
	public static HelpItem loadHelp(InputStream is) {
		try (Scanner scanner = new Scanner(new InputStreamReader(is,"UTF-8"))){
			String title = ""; 
			String content = "";
			if(!scanner.hasNextLine()) throw new RuntimeException("Pomoc je nedostupna.");
			title= scanner.nextLine().trim();
			if(title.length()==0) throw new RuntimeException("Pomoc je nedostupna.");
			while(scanner.hasNextLine()) {
				content += scanner.nextLine()+"\n"; 
			}
			content = ("."+content).trim().substring(1);
			return new HelpItem(title, content);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public synchronized static TreeMap<String, HelpItem> getEmbbededHelpMap(){
		return new TreeMap<String, HelpItem>(embeddedHelpMap); 
	}

	public synchronized static void loadEmbeddedHelpMap() {
		embeddedHelpMap.clear(); 
		embeddedHelpMap.put(APLIKACIJE_HELP, loadHelp(APLIKACIJE_HELP)); 
		embeddedHelpMap.put(DATOTEKE_HELP, loadHelp(DATOTEKE_HELP)); 
		embeddedHelpMap.put(DIREKTORIJUMI_HELP, loadHelp(DIREKTORIJUMI_HELP)); 
		embeddedHelpMap.put(KONFIGURACIJE_HELP, loadHelp(KONFIGURACIJE_HELP)); 
	}
	
	
	public synchronized static TreeMap<String, HelpItem> calculateNameKeyEHM() {
		TreeMap<String, HelpItem> map = new TreeMap<>();
		for(var mEntry: embeddedHelpMap.entrySet()) {
			embeddedHelpMap.put(mEntry.getValue().getTitle(), mEntry.getValue());
		}
		return map;
	}
	
	public synchronized static void restartEmbeddedHelpMap() {
		embeddedHelpMap.clear();
	}
}
