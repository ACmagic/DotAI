package pv.dotai.datai.message.datast;

public class FieldPathField {
	private final String name;
	private final DataTableField field;
	
	public FieldPathField(String name, DataTableField field) {
		this.name = name;
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public DataTableField getField() {
		return field;
	}
}
