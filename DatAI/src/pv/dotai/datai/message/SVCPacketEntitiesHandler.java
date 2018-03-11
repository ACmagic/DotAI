package pv.dotai.datai.message;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.ReplayException;
import pv.dotai.datai.message.datast.DataTable;
import pv.dotai.datai.message.datast.Property;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_PacketEntities;
import pv.dotai.datai.replay.Entity;
import pv.dotai.datai.util.BitStream;

/**
 * Handler for CSVCMsg_PacketEntities messages (svc_PacketEntities)
 * @author Thomas Ibanez
 * @since  1.0
 */
public class SVCPacketEntitiesHandler implements MessageHandler<CSVCMsg_PacketEntities> {	
	
	private boolean full = false;
	
	@Override
	public void handle(CSVCMsg_PacketEntities m) {
		BitStream bs = new BitStream(m.getEntityData().asReadOnlyByteBuffer());
		
		if(!m.getIsDelta() && full) {
			return;
		}
		if(!m.getIsDelta()) {
			full = true;
		}
		
		int idx = -1;
		for (int i = 0; i < m.getUpdatedEntries(); i++) {
			int delta = bs.readBitVar();
			idx += delta + 1;
			
			int a = bs.nextBit();
			int b = bs.nextBit();
			
			updateType type;
			
			if(a == 1 && b == 1) {
				type = updateType.DELETE;
			} else if(a == 1 && b == 0) {
				type = updateType.LEAVE;
			} else if(a == 0 && b == 1) {
				type = updateType.CREATE;
			} else {
				type = updateType.UPDATE;
			}
			
			switch(type) {
				case CREATE:
					int classID = bs.readBits(ReplayBuilder.getInstance().CLASSID_SIZE);
					int serial = bs.readBits(17);
					bs.readVarUInt32(); //Unknown
					String className = ReplayBuilder.getInstance().getClassInfo().get(classID);
					Property classBaseline = ReplayBuilder.getInstance().getClassBaseline().get(classID);
					DataTable flatTable = ReplayBuilder.getInstance().getSerializers().get(className).get(0);
					Property property = new Property();
					property.readProperties(bs, flatTable);
					
					Entity ce = new Entity(idx, classID, serial, property, classBaseline, className, flatTable);
					
					ReplayBuilder.getInstance().getEntities().put(idx	, ce);
					break;
				case DELETE:
					ReplayBuilder.getInstance().getEntities().remove(idx);
					break;
				case LEAVE:
					break;
				case UPDATE:
					Entity ue = ReplayBuilder.getInstance().getEntities().get(idx);
					if(ue == null) {
						throw new ReplayException("Unable to find entity " + idx + " for update !");
					}
					ue.getProperty().readProperties(bs, ue.getFlatTable());
					break;
			}
		}
	}
	
	enum updateType {
		CREATE, UPDATE, DELETE, LEAVE
	}

}
