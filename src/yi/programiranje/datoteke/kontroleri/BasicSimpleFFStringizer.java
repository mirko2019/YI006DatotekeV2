package yi.programiranje.datoteke.kontroleri;

import yi.programiranje.datoteke.model.FileFolder;

public class BasicSimpleFFStringizer extends FFStringizer{
	private SimpleFileNavigator controller; 
	
	public BasicSimpleFFStringizer(FileFolder directory) {
		super(directory);
		controller = new SimpleFileNavigator(getDirectory());
	}
	
	public void reset() {
		controller = new SimpleFileNavigator(getDirectory());
	}
	
	public SimpleFileNavigator getController() {
		return controller; 
	}

	@Override
	public String stringize() {
		String res = "";
		for(String path : controller.open()) {
			res += path+"\n";
		}
		return res;
	}
	
	public String treeStringize() {
		String res = ""; 
		for(String path : controller.open()) {
			var ctrl = controller.getFor(path);
			var ctrlF = controller.getForFile(path);
			if(ctrl!=null) {
				var fi = ctrl.getSource();
				int level = fi.level(); 
				for(int i=0; i<level; i++)
					res+=" ";
				res += fi.getName()+"\n";
			}
			if(ctrlF != null) {
				var fi = ctrlF.getData();
				int level = fi.level(); 
				for(int i=0; i<level; i++)
					res+=" ";
				res += fi.getName()+"\n";
			}
		}
		return res;
	}
}
