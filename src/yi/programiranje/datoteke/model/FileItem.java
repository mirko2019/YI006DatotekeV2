package yi.programiranje.datoteke.model;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Јединица  структурирања података у директоријумско стабло датотека и директоријума.
 * Сама јединица је абстрактна, а строго може бити датотека (фајл) или директоријум (фолдер).
 * Са методама за увоз и извоз садежаја, стабла, цијелокупних података и описа. Копирање, клонирање. 
 * Методе за архивирање. 
 * @author Computer
 * @version 1.0
 */
public abstract class FileItem implements Serializable, Comparable<FileItem>{
	private static final long serialVersionUID = 209694265940420913L;
	
	/*
	 *  Основне методе. 
	 */
	public abstract String getName(); 
	public abstract String getPath();
	public abstract File getFile();
	public abstract File getParent(); 
	public abstract File getRoot(); 
	public abstract boolean isData(); 
	public abstract boolean isFolder(); 
	public abstract FileData asData(); 
	public abstract FileFolder asFolder();
	public abstract FileItem item(); 
	public abstract FileItem root();
	public abstract FileItem parent();
	public abstract List<FileItem> levelOrderRecursive();
	public abstract List<FileItem> levelOrderRestricted(int level);
	public abstract List<File> levelOrderRecursiveFiles();
	public abstract List<File> levelOrderRestrictedFiles(int level);
	public abstract List<String> levelOrderRecursivePaths();
	public abstract List<String> levelOrderRestrictedPaths(int level);
	public abstract int level();
	public abstract int countLevel();
	public abstract int countRecursive();
	public abstract boolean correct();
	public abstract boolean checkStructure();
	public abstract boolean correctStructure(int level);
	public abstract boolean correctStructure();
	public abstract boolean existsAll(int level);
	public abstract boolean existsAll();
	public abstract byte[] getContentBinary();
	public abstract void setContentBinary(byte[] content);
	public abstract String getContentText();
	public abstract void setContentText(String text);
	public abstract FileItem copyTo();
	public abstract void copyFrom(FileItem item);
	public abstract FileItem cloneTo(); 
	public abstract void cloneTo(FileItem item);
	public abstract List<File> parentPathFiles();
	public abstract List<FileFolder> parentPathItems();
	public abstract List<FileItem> children();
	public abstract List<FileFolder> directories(); 
	public abstract List<FileData> files();
	public abstract void resetParent();
	public abstract void setParent(FileFolder parent);
	public abstract String generalRelativePath();
	public abstract String generalAbsolutePath(); 
	public abstract File generalFile();
	public abstract FileItem goOnto(String path);
	public abstract FileItem gotoStrictRelative(String strictRelativePath);
	
	/*
	 * Основни увоз и извоз за стабла без садржаја. Односно самог садржаја.  
	 * Java SRZ. 
	 */
	public abstract void marshallRecursive(OutputStream stream);
	public abstract void unmarshallRecursive(InputStream stream);
	public abstract void marshallLevel(int level);
	public abstract void unmarshallLevel(int level);
	public abstract void serializeContent(OutputStream output);
	public abstract void deserializeContent(InputStream input);
	
	/*
	 * Улаз и излаз описних и бројних података, односно стабла директоријума без података. 
	 * Java SRZ.
	 */
	public abstract void storeDescribeRecursive(OutputStream os);
	public abstract void loadDescribeRecursive(InputStream is);
	public abstract void storeDescribeLevel(int level, OutputStream os);
	public abstract void loadDescribeLevel(int level, InputStream is);
	public abstract void storeTreeRecursive(OutputStream os);
	public abstract void loadTreeRecursive(InputStream is);
	public abstract void storeTreeLevel(int level, OutputStream os);
	public abstract void loadTreeLevel(int level,InputStream is);
	
	/*
	 * Улаз и излаз описних и бројних података, односно стабла директоријума без података. 
	 * XML. Постоји дефинисана и схема XSD. 
	 */
	public abstract void storeDescribeRecursiveXML(OutputStream os);
	public abstract void loadDescribeRecursiveXML(InputStream is);
	public abstract void storeDescribeLevelXML(int level, OutputStream os);
	public abstract void loadDescribeLevelXML(int level, InputStream is);
	public abstract void storeTreeRecursiveXML(OutputStream os);
	public abstract void loadTreeRecursiveXML(InputStream is);
	public abstract void storeTreeLevelXML(int level, OutputStream os);
	public abstract void loadTreeLevelXML(int level, InputStream is);
	
	/*
	 * Улаз и излаз описних и бројних података, односно стабла директоријума без података. 
	 * JSON. 
	 */
	public abstract void storeDescribeRecursiveJSON(OutputStream os);
	public abstract void loadDescribeRecursiveJSON(InputStream is);
	public abstract void storeDescribeLevelJSON(int level, OutputStream os);
	public abstract void loadDescribeLevelJSON(int level, InputStream is);
	public abstract void storeTreeRecursiveJSON(OutputStream os);
	public abstract void loadTreeRecursiveJSON(InputStream is);
	public abstract void storeTreeLevelJSON(int level, OutputStream os);
	public abstract void loadTreeLevelJSON(int level, InputStream is);
	
	/*
	 * Улаз и излаз стабла директотијума и датотека (са или без описа) са садржајем. 
	 * Формат Java SRZ.  
	 */
	public abstract void storeDescribeRecursiveSRZ(OutputStream os);
	public abstract void loadDescribeRecursiveSRZ(InputStream is);
	public abstract void storeDescribeLevelSRZ(int level, OutputStream os);
	public abstract void loadDescribeLevelSRZ(int level, InputStream is);
	public abstract void storeTreeRecursiveSRZ(OutputStream os);
	public abstract void loadTreeRecursiveSRZ(InputStream is);
	public abstract void storeTreeLevelSRZ(int level, OutputStream os);
	public abstract void loadTreeLevelSRZ(int level,InputStream is);
	
	/*
	 * Улаз и излаз стабла директотијума и датотека (са или без описа) са садржајем. 
	 * Формат XML.  Кодовање за садржај Base64 на бајтове. 
	 */
	public abstract void storeDescribeRecursiveXMLBase64(OutputStream os);
	public abstract void loadDescribeRecursiveXMLBase64(InputStream is);
	public abstract void storeDescribeLevelXMLBase64(int level, OutputStream os);
	public abstract void loadDescribeLevelXMLBase64(int level, InputStream is);
	public abstract void storeTreeRecursiveXMLBase64(OutputStream os);
	public abstract void loadTreeRecursiveXMLBase64(InputStream is);
	public abstract void storeTreeLevelXMLBase64(int level, OutputStream os);
	public abstract void loadTreeLevelXMLBase64(int level, InputStream is);
	
	
	/*
	 * Улаз и излаз стабла директотијума и датотека (са или без описа) са садржајем. 
	 * Формат Java JSON.  Кодовање за садржај Base64 на бајтове. 
	 */
	public abstract void storeDescribeRecursiveJSONBase64(OutputStream os);
	public abstract void loadDescribeRecursiveJSONBase64(InputStream is);
	public abstract void storeDescribeLevelJSONBase64(int level, OutputStream os);
	public abstract void loadDescribeLevelJSONBase64(int level, InputStream is);
	public abstract void storeTreeRecursiveJSONBase64(OutputStream os);
	public abstract void loadTreeRecursiveJSONBase64(InputStream is);
	public abstract void storeTreeLevelJSONBase64(int level, OutputStream os);
	public abstract void loadTreeLevelJSONBase64(int level, InputStream is);
	
	/*
	 * Архивирање. Формат ZIP. 
	 */
	public abstract void storeZipRecursive(OutputStream os); 
	public abstract void storeZipLevel(int level, InputStream os);
	public abstract void loadZipRecursive(OutputStream os);
	public abstract void loadZipLevel(int level);
}
