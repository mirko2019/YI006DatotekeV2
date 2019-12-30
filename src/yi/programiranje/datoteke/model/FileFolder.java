package yi.programiranje.datoteke.model;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Репрезентација модела за директорјиме.
 * @author Computer
 * @version 1.0
 */
public final class FileFolder extends FileItem{
	private static final long serialVersionUID = -8223430269074188759L;
	private FileItem root; 
	private FileItem parent; 
	private File file; 
	private List<FileItem> childs;
	private int level; 
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за директоријум. 
	 */
	public FileFolder(String rootPath, String fileName) {
		root = this; 
		parent = this;
		file = new File(rootPath, fileName);
		childs = new ArrayList<>(); 
		level = 0; 
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за директоријум. 
	 */
	public FileFolder(String rootPath, File fileName) {
		root = this;
		parent = this;
		file = new File(rootPath, fileName.getName());
		childs = new ArrayList<>();
		level = 0; 
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за директоријум. 
	 */
	public FileFolder(File rootPath, String fileName) {
		root = this; 
		parent = this; 
		file = new File(rootPath, fileName);
		childs = new ArrayList<>();
		level = 0; 
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param rootPath реална путања за коријен. 
	 * @param fileName реално име за директоријум. 
	 */
	public FileFolder(File rootPath, File fileName) {
		root = this; 
		parent = this; 
		file = new File(rootPath, fileName.getName());
		childs = new ArrayList<>();
		level = 0; 
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param path реална путања за директоријум. 
	 */
	public FileFolder(String path) {
		root = this; 
		parent = this;
		file = new File(path);
		childs = new ArrayList<>();
		level = 0;
	}
	
	/**
	 * Коријенска конструкција. 
	 * @param path реална путања за директоријум. 
	 */
	public FileFolder(File path) {
		root = this; 
		parent = this; 
		file = path;
		childs = new ArrayList<>();
		level = 0;
	}
	
	/**
	 * Везна конструкција. 
	 * Начин за везање и додавање нових директоријума у стабло, на неки чвор.  
	 * @param parent родитељски чвор, односно директоријум. 
	 * @param name име креираног директоријума. 
	 */
	public FileFolder(FileFolder parent, String name) {
		this.root = parent.root; 
		this.parent = parent; 
		parent.childs.add(this); 
		file = new File(parent.file, name); 
		childs = new ArrayList<>(); 
		level = parent.level + 1; 
	}
	
	/**
	 * Везна конструкција. 
	 * Начин за везање и додавање нових директоријума у стабло, на неки чвор.  
	 * @param parent родитељски чвор, односно директоријум. 
	 * @param name име креираног директоријума. 
	 */
	public FileFolder(FileFolder parent, File name) {
		this.root = parent.root; 
		this.parent = parent; 
		parent.childs.add(this); 
		file = new File(parent.file, name.getName()); 
		childs = new ArrayList<>(); 
		level = parent.level + 1;
	}
	
	/**
	 * Оригинални стринг обијекта.
	 * @return оригинални стринг објекта.
	 */
	public final String originalString() {
		return super.toString();
	}
	
	/**
	 * Оригинални код за објекат. Адреса у меморији. 
	 * @return оригинални код објекта. сдреса у меморији. 
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
	@Override public  boolean isData(){ return false; }           /* Упит за објекат као објекат података           */
	@Override public  boolean isFolder(){ return true; }         /* Упит за објекат као објекат директоријума      */
	@Override public  FileData asData(){ return null; }         /* Захтијев за објекат као објекат података       */
	@Override public  FileFolder asFolder(){ return this; }    /* Захтихјев за објекат као објекат директоријума */
	@Override public  File getRoot(){ return root.getFile(); }/* Преузимање коријена као java.io.File           */
	@Override public  FileItem item(){ return this; }        /* Преузимање текуће јединице. Инстанца објекта   */
	@Override public  FileItem root(){ return root;  }      /* Преузимање коријена као ставке јединице.       */
	@Override public  FileItem parent(){ return parent; }  /* Преузимање родитљское ставке као јединице      */ 
	
	/* Преузимање свих чворова директних потомака. */
	@Override
	public synchronized List<FileItem> children() {
		ArrayList<FileItem> items = new ArrayList<>(childs);
		return items; 
	}

	/* Преузимање свих директоријума директних потомака */
	@Override
	public synchronized List<FileFolder> directories() {
		ArrayList<FileFolder> folders = new ArrayList<>();
		for(FileItem item: childs) {
			FileFolder folder = item.asFolder(); 
			if(folder!=null)
				folders.add(folder);
		}
		return folders; 
	}
	
	/* Преузимање свих датотека директних потомака  */
	@Override
	public synchronized List<FileData> files() {
		ArrayList<FileData> files = new ArrayList<>();
		for(FileItem item: childs) {
			FileData data = item.asData();
			if(data!=null)
				files.add(data);
		}
		return null;
	}
	
	/* Постављање коријена. Примјена код уклапања и премјешрања чворова по стаблу. */
	@Override
	public synchronized void setParent(FileFolder parent) {
		this.parent.asFolder().childs.remove(parent); 
		this.level = parent.level+1;  
		this.parent = parent; 
		this.root = parent.root; 
		parent.childs.add(this);
	}
	
	
	
	@Override public  List<FileItem> levelOrderRecursive(){ throw new UnsupportedOperationException("Kod ne postoji."); }                   /**/
	@Override public  List<FileItem> levelOrderRestricted(int level){ throw new UnsupportedOperationException("Kod ne postoji."); }        /**/
	@Override public  List<File> levelOrderRecursiveFiles(){ throw new UnsupportedOperationException("Kod ne postoji."); }                /**/
	@Override public  List<File> levelOrderRestrictedFiles(int level){ throw new UnsupportedOperationException("Kod ne postoji."); }     /**/
	@Override public  List<String> levelOrderRecursivePaths(){ throw new UnsupportedOperationException("Kod ne postoji."); }            /**/
	@Override public  List<String> levelOrderRestrictedPaths(int level){ throw new UnsupportedOperationException("Kod ne postoji."); } /**/
	
	/* 
	 * Преузимање чворова директних потомака за дати чвор. Копија листе. 
	 */
	public List<FileItem> getChilds(){
		return new ArrayList<>(childs);
	}
	
	/* 
	 * Преузимање чворова директних потомака за дати чвор. Жива листа. 
	 * се препоручује се употреба ове методе и живе листе извана.       
	 */
	public List<FileItem> liveChilds(){
		return childs;
	}
	
	
	
	@Override public  int level(){ return level; }                // Ниво чвора у стаблу. 
	@Override public  int countLevel(){ return childs.size(); }  // Број директних потомака.
	@Override public  int countRecursive(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	/**
	 * Додававање новог чвора у стабло директоријума. 
	 * @param file путања датотеке или директоријума за чвор. 
	 */
	public synchronized void add(String file) {
		new FileFolder(file);
	}
	
	/**
	 * Додававање новог чвора у стабло директоријума. 
	 * @param file путања датотеке или директоријума за чвор.
	 */
	public synchronized void add(File file) {
		new FileFolder(file);
	}
	
	/**
	 * Додававање новог чвора у стабло директоријума. 
	 * @param file путања датотеке или директоријума за чвор. 
	 */
	public synchronized void add(FileItem file) { 
		file.setParent(this);
	}
	
	/**
	 * Брисање постојећег директног потомка за чвор директоријума. 
	 * @param file путања датотеке или директоријума за чвор. 
	 */
	public synchronized void remove(FileItem file) {
		if(checkFileObject(file)) {
			file.resetParent();
			childs.remove(file);
		}
	}
	
	/**
	 * Одпајање (одвајање) датог чвора и подстабла од остатка стабла. 
	 */
	public synchronized void isolate() {
		 if(this.parent.asFolder()!=null) {
			 this.parent.asFolder().childs.remove(this); 
		 }
		 this.parent = this; 
		 this.root = this;
		 this.level = 0;
	}
	
	/**
	 * Преузимање јединице датотеке или директоријума.
	 * @param file објекат датотеке или директоријума. 
	 * @return резултат или нула референца ако није нађено. 
	 */
	public synchronized  FileItem get(String file) {
		if(file==null) return null; 
		File f = new File(file);
		for(FileItem item: childs) {
			try {
				if(f.getCanonicalPath().contentEquals(f.getCanonicalPath())) 
					return item; 
			}catch(Exception ex) {
				continue;
			}
		}
		return null; 
	}
	
	/**
	 * Преузимање јединице датотеке или директоријума.
	 * @param file објекат датотеке или директоријума. 
	 * @return резултат или нула референца ако није нађено. 
	 */
	public synchronized FileItem get(File file) {
		if(file==null) return null;
		for(FileItem item: childs) {
			if(item.getFile().equals(file))
				return item;
		}
		return null; 
	}
	
	/**
	 * Провјера да ли је објекат директни потомак датог директоријума. 
	 * @param object провјеравани чвор.
	 * @return резултат провјере.
	 */
	public synchronized boolean checkFileObject(FileItem object) {
		if(object==null) return false;
		for(FileItem item: childs) {
			if(item.equals(object))
				return true;
		}
		return false;
	}
	
	/**
	 * Провјера постојаности за датотеку или директоријум.
	 * @param file објекат датотеке или директоријума. 
	 * @return резултат провјере. 
	 */
	public boolean contains(String file) {
		return get(file)!=null;
	}
	
	/**
	 * Провјера постојаности за датотеку или директоријум.
	 * @param file објекат датотеке или директоријума. 
	 * @return резултат провјере. 
	 */
	public boolean contains(File file) {
		return get(file)!=null;
	}
	
	/**
	 * Ресетовање коријена. Еквиваленотно отпајању стабла.
	 */
	@Override
	public void resetParent() {
		if(this.parent.asFolder()!=null) {
			 this.parent.asFolder().childs.remove(this); 
		 }
		 this.parent = this; 
		 this.root = this;
		 this.level = 0; 
	}

	/**
	 * Добијање чвора који је на задатој релативној путањи.
	 */
	@Override
	public FileItem goOnto(String path) {
		if(path.contentEquals(this.generalRelativePath()))
			return this; 
		else if(path.length()<generalRelativePath().length()) {
			FileItem parent = this;
			while(parent!=parent.parent()) {
				parent = parent.parent();
				if(parent.generalRelativePath().contentEquals(path))
					return parent; 
			}
		}else {
			if(path.startsWith(this.generalRelativePath()))
				return gotoStrictRelative(path.substring(generalRelativePath().length())); 
		}
		return null; 
	}
	
	@Override
	public synchronized FileItem gotoStrictRelative(String strictRelativePath) {
		String path = strictRelativePath;
		for(FileItem item: childs) {
			if(path.contentEquals(File.separator+item.getName())){
				return item; 
			}
			if(path.startsWith(File.separator+item.getName())){
				return gotoStrictRelative(path.substring(item.getName().length()+1));
			}
		}
		return null;
	}
	
	@Override public  boolean correct(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  boolean checkStructure(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  boolean correctStructure(int level){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  boolean correctStructure(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  boolean existsAll(int level){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  boolean existsAll(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  byte[] getContentBinary(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void setContentBinary(byte[] content){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  String getContentText(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void setContentText(String text){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  FileItem copyTo(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void copyFrom(FileItem item){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  FileItem cloneTo(){ throw new UnsupportedOperationException("Kod ne postoji."); } 
	@Override public  void cloneTo(FileItem item){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  List<File> parentPathFiles(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  List<FileFolder> parentPathItems(){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void marshallRecursive(OutputStream stream){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void unmarshallRecursive(InputStream stream){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void marshallLevel(int level){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void unmarshallLevel(int level){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void serializeContent(OutputStream output){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void deserializeContent(InputStream input){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void storeDescribeRecursive(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeRecursive(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeDescribeLevel(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeLevel(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeRecursive(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeRecursive(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeLevel(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeLevel(int level,InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void storeDescribeRecursiveXML(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeRecursiveXML(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeDescribeLevelXML(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeLevelXML(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeRecursiveXML(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeRecursiveXML(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeLevelXML(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeLevelXML(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void storeDescribeRecursiveJSON(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeRecursiveJSON(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeDescribeLevelJSON(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeLevelJSON(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeRecursiveJSON(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeRecursiveJSON(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeLevelJSON(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeLevelJSON(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void storeDescribeRecursiveSRZ(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeRecursiveSRZ(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeDescribeLevelSRZ(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeLevelSRZ(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeRecursiveSRZ(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeRecursiveSRZ(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeLevelSRZ(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeLevelSRZ(int level,InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void storeDescribeRecursiveXMLBase64(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeRecursiveXMLBase64(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeDescribeLevelXMLBase64(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeLevelXMLBase64(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeRecursiveXMLBase64(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeRecursiveXMLBase64(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeLevelXMLBase64(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeLevelXMLBase64(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void storeDescribeRecursiveJSONBase64(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeRecursiveJSONBase64(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeDescribeLevelJSONBase64(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadDescribeLevelJSONBase64(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeRecursiveJSONBase64(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeRecursiveJSONBase64(InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void storeTreeLevelJSONBase64(int level, OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadTreeLevelJSONBase64(int level, InputStream is){ throw new UnsupportedOperationException("Kod ne postoji."); }
	
	@Override public  void storeZipRecursive(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); } 
	@Override public  void storeZipLevel(int level, InputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadZipRecursive(OutputStream os){ throw new UnsupportedOperationException("Kod ne postoji."); }
	@Override public  void loadZipLevel(int level){ throw new UnsupportedOperationException("Kod ne postoji."); }

	/* Релативна путања за стабло директоријума. */
	@Override
	public synchronized String generalRelativePath() {
		String path = "";
		if(this==parent) return File.separator+getName(); 
		FileFolder folder = this; 
		while(folder!=folder.parent.asFolder()) {
			path = folder.getName()+File.separator+path;
			folder = folder.parent.asFolder();
		}
		return File.separator+root.getName()+File.separator+path.substring(0,path.length()-1);
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
	
	/* Отпајање и брисање свих (директних) чворова потомака стабла из листе.*/
	public synchronized void clean() {
		for(FileItem item: new ArrayList<>(childs)) {
			remove(item);
		}
	}
	
	/* Учитавање и повезивање свих јединица датотека и директоријума са стабла. */
	public synchronized void loadFromRealFileSystem() {
		clean(); 
		for(File file: file.listFiles()) {
			if(file.isDirectory())
				new FileFolder(this, file); 
			if(file.isFile())
				new FileData(this, file);
		}
	}
}
