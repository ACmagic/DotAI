package pv.dotai.datai;

import java.util.HashMap;
import java.util.Map;

import pv.dotai.datai.replay.Entity;

public class ReplayBuilder {
	public int CLASSID_SIZE;
	
	private final Map<Integer, Entity> entities;
	private final Map<Integer, String> classInfo;
	private boolean classInfoComplete = false;
	
	public ReplayBuilder() {
		entities = new HashMap<>();
		this.classInfo = new HashMap<>();
	}
	
	public void updateInstanceBaseline() {
		if(!classInfoComplete) return;
		
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
}