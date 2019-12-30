package yi.programiranje.datoteke.kontroleri;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import yi.programiranje.datoteke.model.FileFolder;
import yi.programiranje.datoteke.model.FileItem;

/**
 * Проформе ради користи краткоспојено управљање стринговима и 
 * нивом страница. Односи се на пуно очитавање и примјећивање 
 * јединица (датотека и директоријума на неком чвору). 
 * Управља стаблом тако да је веза само мапа и коријенски чвор. 
 * То је чвор директоријума који је прослијеђен овом навигатору. 
 * @author Computer
 *
 */
public class SimpleFileNavigator extends AbstractFileNavigator{
	private FileFolder directory; 
	private FileItem current; 
	private HashMap<String, FileItem> open = new HashMap<>();
	
	public SimpleFileNavigator(FileFolder directory) {
		this.directory = directory; 
		this.current = directory; 
		open.put(directory.generalRelativePath(), directory); 
	}
	
	@Override
	public FileFolder getDirectry() {
		return directory;
	}

	@Override
	public FileItem getCurrent() {
		return current;
	}

	@Override
	public synchronized void stepRight(String name) {
		String path = current.generalRelativePath()+File.separator+name;
		if(!open.containsKey(path)) return; 
		current = open.get(path);
	}
	
	public synchronized void stepRight() {
		if(current.isFolder()) { 
			var files = current.getFile().listFiles(); 
			if(files==null) return; 
			if(files.length==0) return;
			var list = Arrays.asList(files);
			Collections.sort(list);
			stepRight(list.get(0).getName());
		}
	}

	@Override
	public synchronized void stepLeft() {
		current = current.parent(); 
	}

	@Override
	public void stepDown() {
		throw new UnsupportedOperationException("Stranicenje nije podrzano.");
	}

	@Override
	public void setpUp() {
		throw new UnsupportedOperationException("Stranicenje nije podrzano.");
	}

	@Override
	public synchronized void expandRight() {
		colaseOpend(); 
		if(!current.isFolder()) return; 
		
		current.asFolder().loadFromRealFileSystem(); 
		
		for(FileItem item: current.asFolder().getChilds()) {
			open.put(item.generalRelativePath(), item);
		}
	}

	@Override
	public void expandDown() {
		throw new UnsupportedOperationException("Stranicenje nije podrzano.");
	}

	@Override
	public void expandUp() {
		throw new UnsupportedOperationException("Stranicenje nije podrzano.");
	}

	@Override
	public synchronized void colapse() {
		if(current==current.parent()) return; 
		for(var mEntry: new HashSet<>(open.entrySet())) {
			if(mEntry.getKey().contentEquals(current.parent().generalRelativePath())) continue; 
			if(mEntry.getKey().startsWith(current.parent().generalRelativePath())) {
				open.remove(mEntry.getKey()); 
			}
		}
		current = current.parent(); 
	}
	
	public void colaseOpend() {
		for(var mEntry: new HashSet<>(open.entrySet())) {
			if(mEntry.getKey().contentEquals(current.generalRelativePath())) continue; 
			if(mEntry.getKey().startsWith(current.generalRelativePath())) {
				open.remove(mEntry.getKey()); 
			}
		}
	}

	@Override
	public void applyScreenLocal() {
		throw new UnsupportedOperationException("Stranicenje nije podrzano.");
	}

	@Override
	public void applyScreenGeneral() {
		throw new UnsupportedOperationException("Stranicenje nije podrzano.");
	}

	@Override
	public synchronized List<String> open() {
		ArrayList<String> open = new ArrayList<>(this.open.keySet());
		Collections.sort(open);
		return open;
	}

	@Override
	public synchronized ForwardLevelPaggingController getFor(String path) {
		if(open.get(path)==null) return null; 
		if(!open.get(path).isFolder()) return null; 
		return new ForwardLevelPaggingController(open.get(path).asFolder()); 
	}
	
	public synchronized List<String> openLevel() {
		ArrayList<String> result = new ArrayList<>();
		if(current.getFile().equals(directory.getFile())) {
			result.add(current.generalRelativePath()); 
		}else {
			for(File f: current.getFile().getParentFile().listFiles()) {
				try{result.add(f.getCanonicalPath().substring(directory.getFile().getParentFile().getCanonicalPath().length()));}
				catch(Exception ex) {continue;}
			}
		}
		Collections.sort(result);
		return result; 
	}
	
	public synchronized int openLevelIndex() {
		return openLevel().indexOf(current.generalRelativePath());
	}
	
	public synchronized void gotoDown() {
		var openLevel = openLevel(); 
		int index = openLevel.indexOf(current.generalRelativePath());
		if(index+1==openLevel.size()) return; 
		var newCurrent = open.get(openLevel.get(index+1)); 
		if(newCurrent==null) return; 
		current = newCurrent; 
	}

	public synchronized void gotoUp() {
		var openLevel = openLevel(); 
		int index = openLevel.indexOf(current.generalRelativePath());
		if(index-1<0) return; 
		var newCurrent = open.get(openLevel.get(index-1)); 
		if(newCurrent==null) return; 
		current = newCurrent; 
	}

	@Override
	public List<String> openAll() {
		return open();
	}

	@Override
	public synchronized List<String> openFiles() {
		ArrayList<String> open = new ArrayList<>();
		for(var mEntry : this.open.entrySet()) {
			if(mEntry.getValue().isData())
				open.add(mEntry.getKey()); 
		}
		Collections.sort(open);
		return open;
	}

	@Override
	public synchronized  List<String> openFolders() {
		ArrayList<String> open = new ArrayList<>();
		for(var mEntry : this.open.entrySet()) {
			if(mEntry.getValue().isFolder())
				open.add(mEntry.getKey()); 
		}
		Collections.sort(open);
		return open;
	}

	@Override
	public synchronized BinnaryContentPaggingController getForFile(String path) {
		if(open.get(path)==null) return null; 
		if(!open.get(path).isData()) return null; 
		return new BinnaryContentPaggingController(open.get(path).asData()); 
	}
}
