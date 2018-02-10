package pv.dotai.datai.message.datast;

import java.util.ArrayList;

public class FieldPath {

	private DataTable parent;
	public ArrayList<FieldPathField> fields;
	public ArrayList<Integer> path;
	private boolean finished;
	
	public FieldPath(DataTable parent) {
		this.parent = parent;
		this.fields = new ArrayList<>();
		this.path = new ArrayList<>();
		this.finished = false;
	}
	
	
}