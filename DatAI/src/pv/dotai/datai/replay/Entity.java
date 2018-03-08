package pv.dotai.datai.replay;

import pv.dotai.datai.message.datast.DataTable;
import pv.dotai.datai.message.datast.Property;

public class Entity {

	private int index;
	private int classID;
	private int serial;
	private Property property;
	private Property classBaseline;
	private String className;
	private DataTable flatTable;
	
	public Entity(int index, int classID, int serial, Property property, Property classBaseline, String className, DataTable flatTable) {
		this.index = index;
		this.classID = classID;
		this.serial = serial;
		this.property = property;
		this.classBaseline = classBaseline;
		this.className = className;
		this.flatTable = flatTable;
	}
	
	public double getWorldX() {
		long cellX = (long) this.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellX");
		float vecX = (float) this.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_vecX");
		
		return cellX * 128.0f + vecX;
	}
	
	public double getWorldY() {
		long cellY = (long) this.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellY");
		float vecY = (float) this.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_vecY");
		
		return cellY * -128.0f - vecY + 32768.0f;
	}
	
	public Object fetchProperty(String key) {
		if(property.getKV().get(key) != null) {
			return property.getKV().get(key);
		}
		return classBaseline.getKV().get(key);
	}

	public int getIndex() {
		return index;
	}

	public int getClassID() {
		return classID;
	}

	public int getSerial() {
		return serial;
	}

	public Property getProperty() {
		return property;
	}

	public Property getClassBaseline() {
		return classBaseline;
	}

	public String getClassName() {
		return className;
	}

	public DataTable getFlatTable() {
		return flatTable;
	}
	
}
