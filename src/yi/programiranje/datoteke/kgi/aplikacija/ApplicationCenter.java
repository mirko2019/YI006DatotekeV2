package yi.programiranje.datoteke.kgi.aplikacija;

import java.io.File;

import yi.programiranje.datoteke.kgi.konfiguracije.Configuration;
import yi.programiranje.datoteke.kontroleri.BasicBinaryHexprintFDStringizer;
import yi.programiranje.datoteke.kontroleri.BasicSimpleFIStringizer;
import yi.programiranje.datoteke.model.FileFolder;

public final class ApplicationCenter {
	private ApplicationCenter() {}
	
	private static FileFolder root = new FileFolder(new File(Configuration.generalProperties.getProperty(Configuration.FILE_ROOT)));
	private static BasicSimpleFIStringizer system = new BasicSimpleFIStringizer(root);
	private static BasicBinaryHexprintFDStringizer dataStringizer; 
	
	public static FileFolder getRoot() {
		return root; 
	}
	
	public static File getRootFile() {
		return root.getFile();
	}
	
	public static boolean setRootFile(File file) {
		if(!file.exists()) return false; 
		if(!file.isDirectory()) return false;
		root = new FileFolder(file);
		system = new BasicSimpleFIStringizer(root);
		dataStringizer = null; 
		return true; 
	}
	
	public static BasicSimpleFIStringizer getSystem() {
		return system; 
	}
	
	public static BasicBinaryHexprintFDStringizer getDataStringizer() {
		return dataStringizer;
	}
	
	public static void setDataStringizer(BasicBinaryHexprintFDStringizer dataStringizer) {
		ApplicationCenter.dataStringizer = dataStringizer;
	}
}