package pv.dotai.datai.message.datast;

/**
 * Abstract representation of a field path's field to be read with it's serializer
 * @author Thomas Ibanez
 * @since  1.0
 */
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
