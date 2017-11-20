package pv.dotai.datai.message;

import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_PacketEntities;
import pv.dotai.datai.util.BitStream;

public class SVCPacketEntitiesHandler implements MessageHandler<CSVCMsg_PacketEntities> {	
	
	@Override
	public void handle(CSVCMsg_PacketEntities m) {
		BitStream bs = new BitStream(m.getEntityData().asReadOnlyByteBuffer());
		int idx = -1;
		for (int i = 0; i < m.getUpdatedEntries(); i++) {
			int delta = bs.getBitVar();
			idx += delta + 1;
			
			int a = bs.readBits(1);
			int b = bs.readBits(1);
			
			updateType type;
			
			if(a == 1 && b == 1) {
				type = updateType.DELETE;
			} else if(a == 1 && b == 0) {
				type = updateType.MYSTERY;
			} else if(a == 0 && b == 1) {
				type = updateType.CREATE;
			} else {
				type = updateType.UPDATE;
			}
			
			switch(type) {
				case CREATE:
					
					break;
				case DELETE:
					break;
				case MYSTERY:
					break;
				case UPDATE:
					break;
			}
		}
	}
	
	enum updateType {
		CREATE, UPDATE, DELETE, MYSTERY
	}

}
