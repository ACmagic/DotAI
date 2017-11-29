package pv.dotai.datai.message.datast;

import java.util.HashMap;
import java.util.Map;

public class StringTables {
	
	private final Map<Integer, StringTable> tables;
	private final Map<String, Integer> nameIndex;
	private int nextIndex;
	
	public StringTables() {
		this.tables = new HashMap<>();
		this.nameIndex = new HashMap<>();
		this.nextIndex = 0;
	}

	public StringTable getByName(String name) {
		Integer idx = this.nameIndex.get(name);
		if(idx != null) {
			return this.tables.get(idx);
		}
		return null;
	}
	
	public int getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	public Map<Integer, StringTable> getTables() {
		return tables;
	}

	public Map<String, Integer> getNameIndex() {
		return nameIndex;
	}
}
