package yi.programiranje.datoteke.kontroleri;

import yi.programiranje.datoteke.model.FileData;

public class BasicBinaryHexprintFDStringizer extends FDStringizer{
	private BinnaryContentPaggingController controller; 
		
	public BasicBinaryHexprintFDStringizer(FileData data) {
		super(data);
		controller = new BinnaryContentPaggingController(getData());
	}
	
	public void reset() {
		controller = new BinnaryContentPaggingController(getData());
	}

	@Override
	public String stringize() {
		String res = "";
		byte[][] contentTable = controller.readBytesOnPageToMatrix();
		for(byte[] bytes:contentTable) {
			for(byte by: bytes) {
				res+=String.format("%02x", Byte.toUnsignedInt(by))+" "; 
			}
			res+="\n"; 
		}
		return res;
	}
	
	public String allQualifiedPageString() {
		return "PG.: "+controller.getPageNo()+"\n"+stringize(); 
	}
	
	public BinnaryContentPaggingController getController() {
		return controller; 
	}
}
