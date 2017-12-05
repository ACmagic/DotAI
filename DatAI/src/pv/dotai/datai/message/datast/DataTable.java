package pv.dotai.datai.message.datast;

import java.util.ArrayList;
import java.util.List;

public class DataTable {
	private String name;
	private int flags;
	private int version;
	private final List<DataTableProperty> properties;
	
	public DataTable(String name, int version) {
		this.name = name;
		this.version = version;
		this.properties = new ArrayList<>();
	}
	
	public DataTable(DataTable dataTable) {
		this.name = dataTable.name;
		this.version = dataTable.version;
		this.flags = dataTable.flags;
		this.properties = new ArrayList<>(dataTable.properties);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public List<DataTableProperty> getProperties() {
		return properties;
	}
	
	
}
