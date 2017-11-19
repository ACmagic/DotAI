package __pv.DotAI.DotAxtractor;

import java.nio.ByteBuffer;

import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PacketEntities;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.SVC_Messages;
import pv.dotai.datai.util.BitStream;

public class SVCParser {

	public static void parseSVC(EmbedData data) {
		switch((SVC_Messages)data.getType()) {
			case svc_BSPDecal:
				break;
			case svc_ClassInfo:
				break;
			case svc_ClearAllStringTables:
				break;
			case svc_CmdKeyValues:
				break;
			case svc_CreateStringTable:
				break;
			case svc_FlattenedSerializer:
				break;
			case svc_FullFrameSplit:
				break;
			case svc_GetCvarValue:
				break;
			case svc_HLTVStatus:
				break;
			case svc_Menu:
				break;
			case svc_PacketEntities:
				onPacketEntities((CSVCMsg_PacketEntities) data.getMessage());
				break;
			case svc_PacketReliable:
				break;
			case svc_PeerList:
				break;
			case svc_Prefetch:
				break;
			case svc_Print:
				break;
			case svc_ServerInfo:
				break;
			case svc_SetPause:
				break;
			case svc_SetView:
				break;
			case svc_Sounds:
				break;
			case svc_SplitScreen:
				break;
			case svc_StopSound:
				break;
			case svc_UpdateStringTable:
				break;
			case svc_VoiceData:
				break;
			case svc_VoiceInit:
				break;
			default:
				break;
			
		}
	}
	
	private static void onPacketEntities(CSVCMsg_PacketEntities e) {
		BitStream bs = new BitStream(ByteBuffer.wrap(e.getEntityData().toByteArray()));
		int idx = -1;
		for (int i = 0; i < e.getUpdatedEntries(); i++) {
			int delta = Decoder.getBitVar(bs);
			idx += delta + 1;
			
			boolean x = bs.readBits(1) == 1;
			boolean y = bs.readBits(1) == 1;
			
			char type = '?';
			
			if(x && y) {
				type = 'D';
			} else if(!x && y) {
				type = 'C';
			} else if(x && !y) {
				type = '?';
			} else if(!x && !y) {
				type = 'U';
			}
			
			switch(type) {
				case 'C':
					
					break;
				case 'U':
					break;
				case 'D':
					break;
			}
			
		}
	}
	
}
