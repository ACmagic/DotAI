package pv.dotai.datai.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.replay.Entity;

public class Writer {

	private PrintWriter pWriter;
	
	public Writer() {
		try {
			pWriter = new PrintWriter("out.log", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void write(int tick) {
		for (Entry<Integer, Entity> entity : ReplayBuilder.getInstance().getEntities().entrySet()) {
			String name = entity.getValue().getClassName();
			if(entity.getValue().fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellX") != null) {
				if((long)entity.getValue().fetchProperty("m_iTeamNum") == 2 || (long)entity.getValue().fetchProperty("m_iTeamNum") == 3) {
        				pWriter.print("T: "+tick+" "+name+" -> ");
        				pWriter.print("TEAM: "+entity.getValue().fetchProperty("m_iTeamNum")+" ; ");
        				if(name.equals("CDOTAPlayer") || name.startsWith("CDOTA_Unit_Hero_")) {
        					pWriter.print("PLAYER: "+entity.getValue().fetchProperty("m_iPlayerID")+" ; ");
        				}
        				pWriter.print("WX: "+getWorldX(entity.getValue())+" ; ");
        				pWriter.print("WY: "+getWorldY(entity.getValue()));
        				pWriter.println();
				}
			}
		}
	}
	
	private double getWorldX(Entity e) {
		long cellX = (long) e.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellX");
		float vecX = (float) e.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_vecX");
		
		return cellX * 128.0f + vecX;
	}
	
	private double getWorldY(Entity e) {
		long cellY = (long) e.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellY");
		float vecY = (float) e.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_vecY");
		
		return cellY * -128.0f - vecY + 32768.0f;
	}
	
	public void finish() {
		pWriter.close();
	}
}
