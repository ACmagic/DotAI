for (Entity entity : entities) {
				String name = entity.getClassName();
				if (entity.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellX") != null) {
					if(name.startsWith("CDOTA_Unit_Hero_")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						if((int)entity.fetchProperty("m_iHealth") > 0) {
        						g.fillArc(getMapX(entity) - 6, getMapY(entity) - 6, 12, 12, 0, 360);
        						g.setColor(color(name));
        						g.fillArc(getMapX(entity) - 5, getMapY(entity) - 5, 10, 10, 0, 360);
						} else {
							g.drawChars(new char[] {'X'}, 0, 1, getMapX(entity), getMapY(entity));
						}
					} else if(name.equals("CDOTA_BaseNPC_Creep_Lane") || name.equals("CDOTA_BaseNPC_Creep_Siege")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						g.fillArc(getMapX(entity) - 3, getMapY(entity) - 3, 6, 6, 0, 360);
					} else if(name.equals("CDOTA_BaseNPC_Creep_Neutral")) {
						g.setColor(Color.cyan);
						g.fillArc(getMapX(entity) - 4, getMapY(entity) - 4, 8, 8, 0, 360);
					} else if(name.equals("CDOTA_BaseNPC_Tower") || name.equals("CDOTA_BaseNPC_Barracks")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						g.fillRect(getMapX(entity) - 4, getMapY(entity) - 4, 8, 8);
					} else if(name.equals("CDOTA_BaseNPC_Fort")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						g.fillPolygon(new int[] {getMapX(entity) - 8, getMapX(entity), getMapX(entity) + 8}, new int[] {getMapY(entity) + 8, getMapY(entity) - 8, getMapY(entity) + 8}, 3);					
					} else if(name.equals("CDOTA_Unit_Roshan")){
						g.setColor(Color.ORANGE);
						g.fillArc(getMapX(entity) - 5, getMapY(entity) - 5, 10, 10, 0, 360);
					}
				}
			}
