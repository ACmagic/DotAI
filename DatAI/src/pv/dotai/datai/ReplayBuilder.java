package pv.dotai.datai;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import pv.dotai.datai.message.datast.DataTable;
import pv.dotai.datai.message.datast.Property;
import pv.dotai.datai.message.datast.StringTable;
import pv.dotai.datai.message.datast.StringTableItem;
import pv.dotai.datai.message.datast.StringTables;
import pv.dotai.datai.replay.Entity;
import pv.dotai.datai.util.BitStream;

public class ReplayBuilder {
	public int CLASSID_SIZE;
	public int GAME_BUILD;
	
	private final Map<Integer, Entity> entities;
	private final Map<Integer, String> classInfo;
	private Map<String, Map<Integer, DataTable>> serializers;
	private final Map<Integer, Property> classBaseline;
	private boolean classInfoComplete = false;
	private final StringTables stringTables;
	
	public ReplayBuilder() {
		this.entities = new HashMap<>();
		this.classInfo = new HashMap<>();
		this.stringTables = new StringTables();
		this.classBaseline = new HashMap<>();
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
		int classID = -1;
		try {
			classID = Integer.parseInt(item.getKey());
		} catch(NumberFormatException e) {
			System.err.println("Invalid InstanceBaseline key: "+item.getKey());
			e.printStackTrace();
		}
		String className = ReplayBuilder.getInstance().getClassInfo().get(classID);
		if(className == null) {
			throw new ReplayException("Unable to find class info for instancebaseline key "+classID);
		}
		
		Property p = ReplayBuilder.getInstance().getClassBaseline().get(classID);
		if(p == null) {
			ReplayBuilder.getInstance().getClassBaseline().put(classID, new Property());
		}
		
		Map<Integer, DataTable> serializer = ReplayBuilder.getInstance().getSerializers().get(className);
		if(serializer == null) {
			throw new ReplayException("Unable to find send table "+ className +" for instancebaseline key "+classID);
		}
		
		if(item.getValue() != null && item.getValue().length > 0) {
			BitStream bs = new BitStream(ByteBuffer.wrap(item.getValue()));
			this.classBaseline.get(classID).readProperties(bs, serializer.get(0));
		}
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

	public Map<String, Map<Integer, DataTable>> getSerializers() {
		return serializers;
	}

	public void setSerializers(Map<String, Map<Integer, DataTable>> serializers) {
		this.serializers = serializers;
	}

	public Map<Integer, Property> getClassBaseline() {
		return classBaseline;
	}
}
