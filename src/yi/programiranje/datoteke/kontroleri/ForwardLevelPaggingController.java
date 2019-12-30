package yi.programiranje.datoteke.kontroleri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yi.programiranje.datoteke.model.FileFolder;
import yi.programiranje.datoteke.model.FileItem;

/**
 * Краткоспојени приступ листи директних потомака за неки директоријум. 
 * Проформе ради страничења, али само прослеђује цијелокупну сортирану
 * листу директних потомака за неки чвор. Дакле страничење није подржано.
 * @author Computer
 *
 */
public class ForwardLevelPaggingController extends AbstractLevelPaggingController{
	private FileFolder source; 
	public ForwardLevelPaggingController(FileFolder source) {
		this.source = source; 
	}
	
	@Override
	public FileFolder getSource() {
		return source;
	}

	@Override
	public int getSize() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public int getPageNo() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public int countItems() {
		return source.children().size();
	}

	@Override
	public int countPages() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public void setPageNo(int pageNo) {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public void gotoLastPage() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public void gotoFirstPage() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public void up() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public void down() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public List<FileItem> getAll() {
		ArrayList<FileItem> res = new ArrayList<>(source.children());
		Collections.sort(res);
		return res;
	}

	@Override
	public List<FileItem> getPagedRequire() {
		ArrayList<FileItem> res = new ArrayList<>(source.children());
		Collections.sort(res);
		return res;
	}

	@Override
	public ForwardStringPaggingController getAllString() {
		String res = "";
		for(FileItem file: getAll()) {
			res += file.getName() + (file.getFile().isDirectory()?"<DIR>":"<FILE>") + "\n";
		}
		ForwardStringPaggingController ctrl = new ForwardStringPaggingController(res.trim()); 
		ctrl.setLevel(source.level());
		return ctrl;
	}

	@Override
	public ForwardStringPaggingController getRequiredString() {
		String res = "";
		for(FileItem file: getAll()) {
			res += file.getName() + (file.getFile().isDirectory()?"<DIR>":"<FILE>") + "\n";
		}
		ForwardStringPaggingController ctrl = new ForwardStringPaggingController(res.trim()); 
		ctrl.setLevel(source.level());
		return ctrl;
	}

	@Override
	public int getDefaultWidthSize() {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}

	@Override
	public void setDefaultWidthSize(int defaultWidthSize) {
		throw new UnsupportedOperationException("Stranicenje u ovom modelu memorisanja nije podrzano.");
	}
}
