package pv.dotai.datai.message.datast;

import java.util.ArrayList;

public class FieldPath {

	private DataTable parent;
	public final ArrayList<FieldPathField> fields;
	public final ArrayList<Integer> path;
	public boolean finished;
	
	public FieldPath(DataTable parent) {
		this.parent = parent;
		this.fields = new ArrayList<>();
		this.path = new ArrayList<>();
		this.finished = false;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public DataTable getParent() {
		return parent;
	}

	public ArrayList<FieldPathField> getFields() {
		return fields;
	}

	public ArrayList<Integer> getPath() {
		return path;
	}
	
	
}