package pv.DotAI.DotAxtractor;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ProtocolMessageEnum;

import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoClassInfo;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoConsoleCmd;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoCustomData;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoCustomDataCallbacks;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFileHeader;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFileInfo;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFullPacket;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoPacket;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoSendTables;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoStringTables;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoSyncTick;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoUserCmd;
import pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_BSPDecal;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_ClassInfo;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_ClearAllStringTables;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_CmdKeyValues;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_CreateStringTable;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_FlattenedSerializer;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_FullFrameSplit;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_GetCvarValue;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_HLTVStatus;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Menu;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PacketEntities;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PacketReliable;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PeerList;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Prefetch;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Print;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_ServerInfo;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_SetPause;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_SetView;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Sounds;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_SplitScreen;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_StopSound;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_UpdateStringTable;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_VoiceData;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_VoiceInit;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.SVC_Messages;
import pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.NET_Messages;

public class CommandInterpreter {

	public AbstractMessage getMessage(EDemoCommands type, byte[] msg) {
		AbstractMessage am = null;
		try {
			switch (type) {
				case DEM_ClassInfo:
					am = CDemoClassInfo.parseFrom(msg);
					break;
				case DEM_ConsoleCmd:
					am = CDemoConsoleCmd.parseFrom(msg);
					break;
				case DEM_CustomData:
					am = CDemoCustomData.parseFrom(msg);
					break;
				case DEM_CustomDataCallbacks:
					am = CDemoCustomDataCallbacks.parseFrom(msg);
					break;
				case DEM_Error:
					break;
				case DEM_FileHeader:
					am = CDemoFileHeader.parseFrom(msg);
					break;
				case DEM_FileInfo:
					am = CDemoFileInfo.parseFrom(msg);
					break;
				case DEM_FullPacket:
					am = CDemoFullPacket.parseFrom(msg);
					break;
				case DEM_IsCompressed:
					break;
				case DEM_Max:
					break;
				case DEM_SignonPacket:
				case DEM_Packet:
					am = CDemoPacket.parseFrom(msg);
					break;
				case DEM_SaveGame:
					//am = CDemoSaveGame.parseFrom(msg);
					break;
				case DEM_SendTables:
					am = CDemoSendTables.parseFrom(msg);
					break;
				case DEM_Stop:
					break;
				case DEM_StringTables:
					am = CDemoStringTables.parseFrom(msg);
					break;
				case DEM_SyncTick:
					am = CDemoSyncTick.parseFrom(msg);
					break;
				case DEM_UserCmd:
					am = CDemoUserCmd.parseFrom(msg);
					break;
				default:
					break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return am;
	}

	public EmbedData[] extractPacketData(CDemoPacket packet) {
		List<EmbedData> datas = new ArrayList<>();
		BitStream bs = new BitStream(packet.getData().asReadOnlyByteBuffer());
		while (bs.remaining() >= 8) {
 			
			int commandID = Decoder.getBitVar(bs);
			int size = Decoder.getVarInt(bs);			
			if (SVC_Messages.forNumber(commandID) != null) {
				ProtocolMessageEnum type = SVC_Messages.forNumber(commandID);
				datas.add(new EmbedData(type, false, extractSVCMessageData(type, size, bs)));
			} else if (NET_Messages.forNumber(commandID) != null) {
				byte[] dummy = new byte[size];
				bs.get(dummy);
				datas.add(new EmbedData(NET_Messages.forNumber(commandID), true, null)); //Nobody cares about net messages
			} else {
				byte[] dummy = new byte[size];
				bs.get(dummy);
				System.out.println("Packet has unknown embed data id: "+commandID);
			}			
		}
		return datas.toArray(new EmbedData[datas.size()]);
	}

	private AbstractMessage extractSVCMessageData(ProtocolMessageEnum type, int size, BitStream bs) {
		SVC_Messages msg = (SVC_Messages) type;
		AbstractMessage am = null;
		byte[] data = new byte[size];
		bs.get(data);
		try {
			switch (msg) {
				case svc_BSPDecal:
					am = CSVCMsg_BSPDecal.parseFrom(data);
					break;
				case svc_ClassInfo:
					am = CSVCMsg_ClassInfo.parseFrom(data);
					break;
				case svc_CreateStringTable:
					am = CSVCMsg_CreateStringTable.parseFrom(data);
					break;
				case svc_GetCvarValue:
					am = CSVCMsg_GetCvarValue.parseFrom(data);
					break;
				case svc_Menu:
					am = CSVCMsg_Menu.parseFrom(data);
					break;
				case svc_PacketEntities:
					am = CSVCMsg_PacketEntities.parseFrom(data);
					break;
				case svc_PacketReliable:
					am = CSVCMsg_PacketReliable.parseFrom(data);
					break;
				case svc_Prefetch:
					am = CSVCMsg_Prefetch.parseFrom(data);
					break;
				case svc_Print:
					am = CSVCMsg_Print.parseFrom(data);
					break;
				case svc_ServerInfo:
					am = CSVCMsg_ServerInfo.parseFrom(data);
					break;
				case svc_SetPause:
					am = CSVCMsg_SetPause.parseFrom(data);
					break;
				case svc_SetView:
					am = CSVCMsg_SetView.parseFrom(data);
					break;
				case svc_Sounds:
					am = CSVCMsg_Sounds.parseFrom(data);
					break;
				case svc_SplitScreen:
					am = CSVCMsg_SplitScreen.parseFrom(data);
					break;
				case svc_UpdateStringTable:
					am = CSVCMsg_UpdateStringTable.parseFrom(data);
					break;
				case svc_VoiceData:
					am = CSVCMsg_VoiceData.parseFrom(data);
					break;
				case svc_VoiceInit:
					am = CSVCMsg_VoiceInit.parseFrom(data);
					break;
				case svc_CmdKeyValues:
					am = CSVCMsg_CmdKeyValues.parseFrom(data);
					break;
				case svc_ClearAllStringTables:
					am = CSVCMsg_ClearAllStringTables.parseFrom(data);
					break;
				case svc_FlattenedSerializer:
					am = CSVCMsg_FlattenedSerializer.parseFrom(data);
					break;
				case svc_FullFrameSplit:
					am = CSVCMsg_FullFrameSplit.parseFrom(data);
					break;
				case svc_HLTVStatus:
					am = CSVCMsg_HLTVStatus.parseFrom(data);
					break;
				case svc_PeerList:
					am = CSVCMsg_PeerList.parseFrom(data);
				case svc_StopSound:
					am = CSVCMsg_StopSound.parseFrom(data);
				default:
					break;
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return am;
	}

}
