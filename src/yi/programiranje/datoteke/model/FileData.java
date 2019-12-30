package yi.programiranje.datoteke.model;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;


public class FileData extends FileItem{
	private static final long serialVersionUID = -624926278840065334L;

	private FileItem root; 
	private FileItem parent; 
	private File file; 
	private transient byte[] content; 
	private int level;
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за датотеку. 
	 */
	public FileData(String rootPath, String fileName) {
		root = this; 
		parent = this;
		file = new File(rootPath, fileName);
		level = 0; 
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за датотеку. 
	 */
	public FileData(String rootPath, File fileName) {
		root = this;
		parent = this;
		file = new File(rootPath, fileName.getName());
		level = 0;
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за датотеку. 
	 */
	public FileData(File rootPath, String fileName) {
		root = this; 
		parent = this; 
		file = new File(rootPath, fileName);
		level = 0;
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за датотеку. 
	 */
	public FileData(File rootPath, File fileName) {
		root = this; 
		parent = this; 
		file = new File(rootPath, fileName.getName());
		level = 0;
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param path реална путања за датотеку. 
	 */
	public FileData(String path) {
		root = this; 
		parent = this;
		file = new File(path);
		level = 0;
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param path реална путања за датотеку. 
	 */
	public FileData(File path) {
		root = this; 
		parent = this; 
		file = path;
		level = 0;
	}
	
	/**
	 * Везна конструкција. 
	 * Начин за везање и додавање нових директоријума у стабло, на неки чвор.  
	 * @param parent родитељски чвор, односно датотеке. 
	 * @param name име креиране датотеке. 
	 */
	public FileData(FileFolder parent, String name) {
		this.root = parent.root(); 
		this.parent = parent; 
		parent.liveChilds().add(this); 
		file = new File(parent.getFile(), name); 
		level = parent.level()+1;
	}
	
	/**
	 * Везна конструкција. 
	 * Начин за везање и додавање нових директоријума у стабло, на неки чвор.  
	 * @param parent родитељски чвор, односно датотеке. 
	 * @param name име креиране датотеке. 
	 */
	public FileData(FileFolder parent, File name) {
		this.root = parent.root(); 
		this.parent = parent; 
		parent.liveChilds().add(this); 
		file = new File(parent.getFile(), name.getName());
		level = parent.level()+1;
	}
	
	/**
	 * Оригинални стринг обијекта.
	 * @return оригинални стринг објекта.
	 */
	public final String originalString() {
		return super.toString();
	}
	
	/**
	 * Оригинални код за објекат. сдреса у меморији. 
	 * @return оригинални код објекта. Адреса у меморији. 
	 */
	public final int originalCode() {
		return super.hashCode();
	}
	
	/**
	 * Стрингизација. Оригинална путања. Пуна варијанта.
	 * @return Пуна варијанта путање. 
	 */
	@Override 
	public String toString() {
		try {
			return getFile().getCanonicalPath().toString();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	

	/**
	 * Идентификациони код. 
	 * @return Код од стринга пуне путање.  
	 */
	@Override
	public int hashCode() {
		try {
			return toString().hashCode();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Провјера да ли су објекти односно њихове пуне путање исте. 
	 * @return резултат једначења пуних путања.  
	 */
	@Override 
	public boolean equals(Object obj) {
		try {
			if(obj instanceof FileFolder) {
				return toString().equals(obj.toString());
			}
			return false;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Поређење обијеката по њиховом алафабетском, кодном односно лексикографском проедку.
	 * @return поређење пуних путања по лексикографији. 
	 */
	@Override
	public int compareTo(FileItem folder) {
		try {
			return toString().compareTo(folder.toString());
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/* Директоријум преко java.io.File                */
	@Override public  File getFile(){ 
		try {
			file=new File(file.getCanonicalPath()); 
			return file; 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		} 
	} 
	@Override public  String getName(){ return getFile().getName(); }      /* Име директоријума                              */
	@Override public  String getPath(){ return getFile().getPath(); }     /* Пуна путања директоријума                      */
	@Override public  File getParent(){ return parent.getFile(); } /* Директоријум родитеља преко java.io.File       */
	@Override public  boolean isData(){ return true; }            /* Упит за објекат као објекат података           */
	@Override public  boolean isFolder(){ return false; }        /* Упит за објекат као објекат директоријума      */
	@Override public  FileData asData(){ return this; }         /* Захтијев за објекат као објекат података       */
	@Override public  FileFolder asFolder(){ return null; }    /* Захтихјев за објекат као објекат директоријума */
	@Override public  File getRoot(){ return root.getFile(); }/* Преузимање коријена као java.io.File           */
	@Override public  FileItem item(){ return this; }        /* Преузимање текуће јединице. Инстанца објекта   */
	@Override public  FileItem root(){ return root;  }      /* Преузимање коријена као ставке јединице.       */
	@Override public  FileItem parent(){ return parent; }  /* Преузимање родитљское ставке као јединице      */ 
	
	
	@Override public List<FileItem> levelOrderRecursive() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<FileItem> levelOrderRestricted(int level) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<File> levelOrderRecursiveFiles() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<File> levelOrderRestrictedFiles(int level) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<String> levelOrderRecursivePaths() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<String> levelOrderRestrictedPaths(int level) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public int level() { return level; } // Ниво хијерархије стабла на коме је чвор.
	@Override public int countLevel() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public int countRecursive() { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public boolean correct() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public boolean checkStructure() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public boolean correctStructure(int level) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public boolean correctStructure() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public boolean existsAll(int level) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public boolean existsAll() { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public byte[] getContentBinary() { return content; }                        // Преузимање бинарног садржаја
	@Override public void setContentBinary(byte[] content) { this.content = content; }   // Постављање бинарног садржаја
	@Override public String getContentText() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void setContentText(String text) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public FileItem copyTo() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void copyFrom(FileItem item) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public FileItem cloneTo() { throw new UnsupportedOperationException("Код није изведен."); } 
	@Override public void cloneTo(FileItem item) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<File> parentPathFiles() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<FileFolder> parentPathItems() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<FileItem> children() { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public List<FileFolder> directories() { throw new UnsupportedOperationException("Код није изведен."); } 
	@Override public List<FileData> files() { throw new UnsupportedOperationException("Код није изведен."); }
	
	/**
	 * Ресетовање коријена. Еквиваленотно отпајању стабла.
	 */
	@Override
	public void resetParent() {
		if(this.parent.asFolder()!=null) {
			 this.parent.asFolder().liveChilds().remove(this); 
		 }
		 this.parent = this; 
		 this.root = this;
		 this.level = 0; 
	}
	
	/* Постављање коријена. Примјена код уклапања и премјешрања чворова по стаблу. */
	@Override
	public synchronized void setParent(FileFolder parent) {
		this.parent.asFolder().liveChilds().remove(parent); 
		this.level = parent.level()+1;  
		this.parent = parent; 
		this.root = parent.root();
		parent.liveChilds().add(this);
	}
	
	/* Релативна путања за стабло директоријума. */
	@Override
	public synchronized String generalRelativePath() {
		String path = getName();
		if(this==parent) return File.separator+root.getName(); 
		FileFolder folder = parent.asFolder(); 
		while(folder!=folder.parent().asFolder()) {
			path = folder.getName()+File.separator+path;
			folder = folder.parent().asFolder();
		}
		return File.separator+root.getName()+File.separator+path.substring(0,path.length());
	}

	/* Апсолутна путања за стабло директоријума. */
	@Override
	public synchronized String generalAbsolutePath() {
		return root.getFile().getParent()+generalRelativePath();
	}
	
	/* Апсолутни оквир датотека за стабло директоријума. */
	@Override
	public synchronized File generalFile() {
		return new File(generalAbsolutePath());
	}
	
	@Override public FileItem goOnto(String path) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public FileItem gotoStrictRelative(String strictRelativePath) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void marshallRecursive(OutputStream stream) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void unmarshallRecursive(InputStream stream) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void marshallLevel(int level) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void unmarshallLevel(int level) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void serializeContent(OutputStream output) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void deserializeContent(InputStream input) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void storeDescribeRecursive(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeRecursive(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeDescribeLevel(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeLevel(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeRecursive(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeRecursive(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeLevel(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeLevel(int level,InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void storeDescribeRecursiveXML(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeRecursiveXML(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeDescribeLevelXML(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeLevelXML(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeRecursiveXML(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeRecursiveXML(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeLevelXML(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeLevelXML(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void storeDescribeRecursiveJSON(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeRecursiveJSON(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeDescribeLevelJSON(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeLevelJSON(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeRecursiveJSON(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeRecursiveJSON(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeLevelJSON(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeLevelJSON(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void storeDescribeRecursiveSRZ(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeRecursiveSRZ(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeDescribeLevelSRZ(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeLevelSRZ(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeRecursiveSRZ(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeRecursiveSRZ(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeLevelSRZ(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeLevelSRZ(int level,InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void storeDescribeRecursiveXMLBase64(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeRecursiveXMLBase64(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeDescribeLevelXMLBase64(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeLevelXMLBase64(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeRecursiveXMLBase64(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeRecursiveXMLBase64(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeLevelXMLBase64(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeLevelXMLBase64(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void storeDescribeRecursiveJSONBase64(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeRecursiveJSONBase64(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeDescribeLevelJSONBase64(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadDescribeLevelJSONBase64(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeRecursiveJSONBase64(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeRecursiveJSONBase64(InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void storeTreeLevelJSONBase64(int level, OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadTreeLevelJSONBase64(int level, InputStream is) { throw new UnsupportedOperationException("Код није изведен."); }
	
	@Override public void storeZipRecursive(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); } 
	@Override public void storeZipLevel(int level, InputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadZipRecursive(OutputStream os) { throw new UnsupportedOperationException("Код није изведен."); }
	@Override public void loadZipLevel(int level) { throw new UnsupportedOperationException("Код није изведен."); }

	
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public void resetContent() {
		this.content = null; 
	}
	
	/**
	 * Очитавање садржаја из датотеке. 
	 */
	public void read() {
		try {
			content = Files.readAllBytes(getFile().toPath());
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Упис садржаја у датотеку. 
	 */
	public void write() {
		try {
			Files.write(getFile().toPath(), content);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
