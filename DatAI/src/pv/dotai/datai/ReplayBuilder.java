package pv.dotai.datai;

import java.util.HashMap;
import java.util.Map;

import pv.dotai.datai.replay.Entity;

public class ReplayBuilder {
	public static int CLASSID_SIZE;
	
	private final Map<Integer, Entity> entities;
	
	public ReplayBuilder() {
		entities = new HashMap<>();
	}
	
	private static ReplayBuilder instance;
	public static ReplayBuilder getInstance() {
		if(instance == null) instance = new ReplayBuilder();
		return instance;
	}
}
