package pv.dotai.datai.message.datast;

public class DataTableProperty {
	private DataTableField field;
	private DataTable dataTable;
		
	public DataTableProperty(DataTableField field, DataTable dataTable) {
		this.field = field;
		this.dataTable = dataTable;
	}
	
	public DataTableField getField() {
		return field;
	}
	public void setField(DataTableField field) {
		this.field = field;
	}
	public DataTable getDataTable() {
		return dataTable;
	}
	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	
}
