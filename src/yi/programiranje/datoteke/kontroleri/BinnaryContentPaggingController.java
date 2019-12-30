package yi.programiranje.datoteke.kontroleri;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

import yi.programiranje.datoteke.model.FileData;

public class BinnaryContentPaggingController {
	private FileData data;
	private int sizeLinesCount = 25; 
	private int lineBytesCount = 10; 
	private int pageNo = 0; 
	
	public BinnaryContentPaggingController(FileData data) {
		this.data = data; 
	}
	
	public BinnaryContentPaggingController(FileData data, int sizeLinesCount, int lineBytesCount) {
		this.data = data; 
		this.sizeLinesCount = sizeLinesCount; 
		this.lineBytesCount = lineBytesCount; 
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if(pageNo<=0) pageNo=0;
		this.pageNo = pageNo;
	}

	public FileData getData() {
		return data;
	}

	public int getSizeLinesCount() {
		return sizeLinesCount;
	}

	public int getLineBytesCount() {
		return lineBytesCount;
	}
	
	public byte[] getDeclaredContent() {
		return data.getContent(); 
	}
	
	public File getDeclaredHeader() {
		return data.getFile(); 
	}
	
	public byte[] getAllDefiedContentBytes() {
		try {
			return Files.readAllBytes(data.getFile().toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	public long getDeclaredContentLength() {
		return data.getContent().length; 
	}
	
	public long getDefinedContentLength() {
		return data.getFile().length(); 
	}
	
	public long getPageSize() {
		return lineBytesCount*sizeLinesCount;
	}
	
	public long getPagesCount() {
		long fullPages = getDefinedContentLength()/getPageSize();
		long linesOfLastPartPage = getDefinedContentLength()%getPageSize();
		if(linesOfLastPartPage == 0) return fullPages; 
		return fullPages+1;
	}
	
	public byte[] readBytesOnPageToArray() {
		try {
			long a = getPageSize()*pageNo;
			long b = Math.min(getDefinedContentLength(), a+getPageSize());
			if(a>=getDefinedContentLength()) return new byte[0];
			byte[] res = new byte[(int)(b-a)+1];
			RandomAccessFile raf = new RandomAccessFile(data.getFile().getCanonicalPath(),"r");
			raf.seek(a);
			raf.read(res, 0, (int)(b-a));
			raf.close();
			return res; 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public byte[][] readBytesOnPageToMatrix(){
		byte content [] = readBytesOnPageToArray(); 
		byte[][] matrix = new byte[sizeLinesCount][lineBytesCount];
		exit:for(int i=0; i<sizeLinesCount; i++) {
			for(int j=0; j<lineBytesCount; j++) {
				if(i*lineBytesCount+j==content.length) break exit; 
				matrix[i][j] = content[i*lineBytesCount+j];
			}
		}
		
		return matrix; 
	}
	
	public void writeBytesOnPageFromContent() {
		try {
			long a = getPageSize()*pageNo;
			RandomAccessFile raf = new RandomAccessFile(data.getFile().getCanonicalPath(),"r");
			raf.read(data.getContent(), (int)a, data.getContent().length);
			raf.close();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
