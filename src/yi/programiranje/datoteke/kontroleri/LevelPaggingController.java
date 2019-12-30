package yi.programiranje.datoteke.kontroleri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yi.programiranje.datoteke.model.FileFolder;
import yi.programiranje.datoteke.model.FileItem;

public class LevelPaggingController extends AbstractLevelPaggingController{
	private int size = 10;
	private int pageNo = 0;
	private int defaultWidthSize = 80;
	
	private FileFolder source;
	
	public LevelPaggingController(FileFolder source) {
		this.source = source; 
	}
	
	public LevelPaggingController(FileFolder source, int size) {
		if(size<1) size=1;
		this.size = size;
		this.source = source; 
	}
	
	public LevelPaggingController(FileFolder source, int size, int defaultWidthSize) {
		if(size<1) size=1;
		if(defaultWidthSize<1) defaultWidthSize=1;
		this.size = size;
		this.source = source; 
		this.defaultWidthSize = defaultWidthSize; 
	}
	
	@Override
	public FileFolder getSource() {
		return source; 
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public int getPageNo() {
		return pageNo; 
	}
	
	@Override
	public int countItems() {
		return source.children().size();
	}
	
	@Override
	public int countPages() {
		return countItems()/getSize()+(countItems()%getSize()!=0?1:0);
	}
	
	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo; 
		if(pageNo>countPages())
			pageNo = this.countPages()-1;
		if(pageNo<0)
			pageNo = 0; 
	}
	
	@Override
	public void gotoLastPage() {
		this.pageNo = countPages()-1; 
	}
	
	@Override
	public void gotoFirstPage() {
		this.pageNo = 0;
	}
	
	@Override
	public void up() {
		this.pageNo--;
		if(pageNo<0) pageNo = 0;
	}
	
	@Override
	public void down() {
	    pageNo++; 
		if(pageNo>countPages())
			pageNo = this.countPages()-1;
	}
	
	@Override
	public List<FileItem> getAll(){
		List<FileItem> items = new ArrayList<>(source.children());
		Collections.sort(items); 
		return items; 
	}
	
	@Override
	public List<FileItem> getPagedRequire(){
		ArrayList<FileItem> items = new ArrayList<>(source.children());
		Collections.sort(items); 
		ArrayList<FileItem> res = new ArrayList<>(); 
		for(int i=pageNo*getSize(); i<Math.min(items.size(),pageNo*getSize()+pageNo) ; i++) {
			res.add(items.get(i));
		}
		return res; 
	}
	
	@Override
	public StringPaggingController getAllString() {
		String res = "";
		for(FileItem file: getAll()) {
			res += file.getName() + (file.getFile().isDirectory()?"<DIR>":"<FILE>") + "\n";
		}
		StringPaggingController ctrl = new StringPaggingController(res.trim(), size, defaultWidthSize); 
		ctrl.setLevel(source.level());
		return ctrl;
	}
	
	@Override
	public StringPaggingController getRequiredString() {
		String res = "";
		for(FileItem file: getPagedRequire()) {
			res += file.getName() + (file.getFile().isDirectory()?"<DIR>":"<FILE>") +"\n";
		}
		StringPaggingController ctrl = new StringPaggingController(res.trim(),size,defaultWidthSize); 
		ctrl.setLevel(source.level());
		return ctrl;
	}

	@Override
	public int getDefaultWidthSize() {
		return defaultWidthSize;
	}

	@Override
	public void setDefaultWidthSize(int defaultWidthSize) {
		if(defaultWidthSize < 1)
			this.defaultWidthSize = 1; 
		else
			this.defaultWidthSize = defaultWidthSize; 
	}
}
