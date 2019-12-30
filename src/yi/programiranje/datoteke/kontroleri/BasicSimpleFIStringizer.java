package yi.programiranje.datoteke.kontroleri;

import yi.programiranje.datoteke.model.FileItem;

public class BasicSimpleFIStringizer extends FIStringizer{
	private BasicSimpleFFStringizer folderStringizer;
	private BasicBinaryHexprintFDStringizer fileStringizer; 
	
	public BasicSimpleFIStringizer(FileItem item) {
		super(item);
		if(item.isData()) fileStringizer = new BasicBinaryHexprintFDStringizer(item.asData());
		if(item.isFolder()) folderStringizer = new BasicSimpleFFStringizer(item.asFolder());
	}
	
	public BasicSimpleFFStringizer getAsFFNewEquiuivalent() {
		if(getItem().isFolder()) return new BasicSimpleFFStringizer(getItem().asFolder()); 
		return null; 
	}
	
	
	public BasicBinaryHexprintFDStringizer getAsNewFDEquivalent() {
		if(getItem().isData()) return new BasicBinaryHexprintFDStringizer(getItem().asData()); 
		return null;
	}
	
	public BasicSimpleFFStringizer getAsFolderStringizer() {
		return folderStringizer;
	}
	
	public BasicBinaryHexprintFDStringizer getAsDataStringizer() {
		return fileStringizer; 
	}
	
	public BasicBinaryHexprintFDStringizer getCurrentDataStringizer() {
		if(folderStringizer==null) return null; 
		var x = folderStringizer.getController().getCurrent();
		if(x.isData()) return new BasicBinaryHexprintFDStringizer(x.asData());
		return null; 
	}
	
	public SimpleFileNavigator getFolderNavigator() {
		return folderStringizer.getController();
	}
	
	public String generalStringize() {
		return super.stringize(); 
	}
	
	@Override
	public String stringize() {
		if(folderStringizer!=null) return folderStringizer.stringize(); 
		if(fileStringizer!=null) return fileStringizer.allQualifiedPageString();
		return super.stringize(); 
	}
}
