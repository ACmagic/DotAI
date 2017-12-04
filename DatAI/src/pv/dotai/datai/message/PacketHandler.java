package pv.dotai.datai.message;

import com.google.protobuf.InvalidProtocolBufferException;

import pv.dotai.datai.protobuf.Demo.CDemoPacket;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_CreateStringTable;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_PacketEntities;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_ServerInfo;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_UpdateStringTable;
import pv.dotai.datai.protobuf.Netmessages.SVC_Messages;
import pv.dotai.datai.protobuf.Networkbasetypes.NET_Messages;
import pv.dotai.datai.util.BitStream;

public class PacketHandler implements MessageHandler<CDemoPacket> {

	private MessageRouter router;

	public PacketHandler(MessageRouter router) {
		this.router = router;
	}

	@Override
	public void handle(CDemoPacket p) {
		BitStream bs = new BitStream(p.getData().asReadOnlyByteBuffer());
		while (bs.remaining() >= 8) {
			int cmd = bs.readBitVar();
			int size = bs.readVarUInt32();
			byte[] data = new byte[size];
			bs.get(data);
			if (SVC_Messages.forNumber(cmd) != null) {
				handleSVC(cmd, data);
			} else if (NET_Messages.forNumber(cmd) != null) {
				handleNET(cmd, data);
			}
		}
	}

	private void handleSVC(int cmd, byte[] data) {
		try {
			switch (SVC_Messages.forNumber(cmd)) {
				case svc_BSPDecal:
					break;
				case svc_ClassInfo:
					break;
				case svc_ClearAllStringTables:
					break;
				case svc_CmdKeyValues:
					break;
				case svc_CreateStringTable:
					this.router.sendMessage(CSVCMsg_CreateStringTable.parseFrom(data));
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
					this.router.sendMessage(CSVCMsg_PacketEntities.parseFrom(data));
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
					this.router.sendMessage(CSVCMsg_ServerInfo.parseFrom(data));
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
					this.router.sendMessage(CSVCMsg_UpdateStringTable.parseFrom(data));
					break;
				case svc_VoiceData:
					break;
				case svc_VoiceInit:
					break;
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	private void handleNET(int cmd, byte[] data) {
		switch (NET_Messages.forNumber(cmd)) {
			case net_Disconnect:
				break;
			case net_NOP:
				break;
			case net_SetConVar:
				break;
			case net_SignonState:
				break;
			case net_SpawnGroup_Load:
				break;
			case net_SpawnGroup_LoadCompleted:
				break;
			case net_SpawnGroup_ManifestUpdate:
				break;
			case net_SpawnGroup_SetCreationTick:
				break;
			case net_SpawnGroup_Unload:
				break;
			case net_SplitScreenUser:
				break;
			case net_StringCmd:
				break;
			case net_Tick:
				break;
		}
	}

}
