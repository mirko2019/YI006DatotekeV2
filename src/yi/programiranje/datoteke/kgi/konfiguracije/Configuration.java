package yi.programiranje.datoteke.kgi.konfiguracije;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public final class Configuration {
	public final static String FILE_ROOT = "file.root"; 
	
	private Configuration() {}
	public static final Properties generalProperties = new Properties();
	
	public static void open() {
		File dir = new File("runtime.configuration");
		if(!dir.exists()) dir.mkdirs(); 
		try {generalProperties.load(new FileInputStream(new File(dir,"config.general.properties")));}
		catch(Exception ex) {
			try {
				new File(dir,"config.general.properties").createNewFile();
			}catch(Exception exc) {
				ex.printStackTrace();
			}
		}finally {
			if(!generalProperties.containsKey("file.root"))
				try {
					generalProperties.setProperty("file.root",new File(".").getCanonicalPath());
				}catch(Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	
	public static void save() {
		File dir = new File("runtime.configuration");
		if(!dir.exists()) dir.mkdirs(); 
		try{generalProperties.store(new FileOutputStream(new File(dir,"config.general.properties")), "");}
		catch(Exception ex) {ex.printStackTrace();}
	}
}
