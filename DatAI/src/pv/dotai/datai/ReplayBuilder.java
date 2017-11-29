package pv.dotai.datai;

import java.util.HashMap;
import java.util.Map;

import pv.dotai.datai.message.datast.StringTable;
import pv.dotai.datai.message.datast.StringTableItem;
import pv.dotai.datai.message.datast.StringTables;
import pv.dotai.datai.replay.Entity;

public class ReplayBuilder {
	public int CLASSID_SIZE;
	
	private final Map<Integer, Entity> entities;
	private final Map<Integer, String> classInfo;
	private boolean classInfoComplete = false;
	private final StringTables stringTables;
	
	public ReplayBuilder() {
		entities = new HashMap<>();
		this.classInfo = new HashMap<>();
		this.stringTables = new StringTables();
	}
	
	public void updateInstanceBaseline() {
		if(!classInfoComplete) return;
		StringTable st = this.getStringTables().getByName("instancebaseline");
		if(st == null) {
			System.out.println("Skipping updateInstanceBaseline: no instancebaseline stringtable");
			return;
		}
		
		for (StringTableItem item : st.getItems().values()) {
			updateInstanceBaseline(item);
		}
	}
	
	private void updateInstanceBaseline(StringTableItem item) {
		//TODO
	}
	
	private static ReplayBuilder instance;
	public static ReplayBuilder getInstance() {
		if(instance == null) instance = new ReplayBuilder();
		return instance;
	}
	
	public Map<Integer, String> getClassInfo() {
		return classInfo;
	}

	public boolean isClassInfoComplete() {
		return classInfoComplete;
	}

	public void setClassInfoComplete(boolean classInfoComplete) {
		this.classInfoComplete = classInfoComplete;
	}

	public StringTables getStringTables() {
		return stringTables;
	}
}
