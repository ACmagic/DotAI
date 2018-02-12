package pv.dotai.datai.message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
		List<PendingMessage> pendings = new ArrayList<>();
		while (bs.remaining() >= 8) {
			int cmd = bs.readBitVar();
			int size = bs.readVarUInt32();
			byte[] data = new byte[size];
			bs.get(data);
			pendings.add(new PendingMessage(cmd, data));
		}
		
		pendings.sort(new Comparator<PendingMessage>() {
			@Override
			public int compare(PendingMessage a, PendingMessage b) {
				int p = 0;
				if(SVC_Messages.forNumber(a.getType()) == SVC_Messages.svc_CreateStringTable || SVC_Messages.forNumber(a.getType()) == SVC_Messages.svc_UpdateStringTable) {
					p--;
				}
				if(SVC_Messages.forNumber(b.getType()) == SVC_Messages.svc_CreateStringTable || SVC_Messages.forNumber(b.getType()) == SVC_Messages.svc_UpdateStringTable) {
					p++;
				}
				return p;
			}
		});
		
		for (PendingMessage pendingMessage : pendings) {
			if (SVC_Messages.forNumber(pendingMessage.getType()) != null) {
				handleSVC(pendingMessage.getType(), pendingMessage.getBuf());
			} else if (NET_Messages.forNumber(pendingMessage.getType()) != null) {
				handleNET(pendingMessage.getType(), pendingMessage.getBuf());
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

	class PendingMessage {
		
		private final int type;
		private final byte[] buf;
		
		public PendingMessage(int type, byte[] buf) {
			this.type = type;
			this.buf = buf;
		}
		
		public int getType() {
			return type;
		}
		
		public byte[] getBuf() {
			return buf;
		}
	}
}
