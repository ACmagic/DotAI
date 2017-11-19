package __pv.DotAI.DotAxtractor;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ProtocolMessageEnum;

import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoClassInfo;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoConsoleCmd;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoCustomData;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoCustomDataCallbacks;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFileHeader;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFileInfo;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFullPacket;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoPacket;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoSendTables;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoStringTables;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoSyncTick;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoUserCmd;
import __pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_BSPDecal;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_ClassInfo;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_ClearAllStringTables;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_CmdKeyValues;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_CreateStringTable;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_FlattenedSerializer;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_FullFrameSplit;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_GetCvarValue;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_HLTVStatus;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Menu;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PacketEntities;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PacketReliable;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PeerList;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Prefetch;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Print;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_ServerInfo;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_SetPause;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_SetView;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_Sounds;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_SplitScreen;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_StopSound;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_UpdateStringTable;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_VoiceData;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_VoiceInit;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.SVC_Messages;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_Disconnect;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_NOP;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SetConVar;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SignonState;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SpawnGroup_Load;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SpawnGroup_LoadCompleted;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SpawnGroup_ManifestUpdate;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SpawnGroup_SetCreationTick;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SpawnGroup_Unload;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_SplitScreenUser;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_StringCmd;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.CNETMsg_Tick;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.NET_Messages;
import pv.dotai.datai.util.BitStream;

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
				datas.add(new EmbedData(NET_Messages.forNumber(commandID), true, extractNETMessageData(NET_Messages.forNumber(commandID), size, bs)));
			} else {
				byte[] dummy = new byte[size];
				bs.get(dummy);
			}
		}
		return datas.toArray(new EmbedData[datas.size()]);
	}

	private AbstractMessage extractNETMessageData(ProtocolMessageEnum type, int size, BitStream bs) {
		NET_Messages msg = (NET_Messages) type;
		AbstractMessage am = null;
		byte[] data = new byte[size];
		bs.get(data);
		try {
			switch (msg) {
				case net_Disconnect:
					am = CNETMsg_Disconnect.parseFrom(data);
					break;
				case net_NOP:
					am = CNETMsg_NOP.parseFrom(data);
					break;
				case net_SetConVar:
					am = CNETMsg_SetConVar.parseFrom(data);
					break;
				case net_SignonState:
					am = CNETMsg_SignonState.parseFrom(data);
					break;
				case net_SpawnGroup_Load:
					am = CNETMsg_SpawnGroup_Load.parseFrom(data);
					break;
				case net_SpawnGroup_LoadCompleted:
					am = CNETMsg_SpawnGroup_LoadCompleted.parseFrom(data);
					break;
				case net_SpawnGroup_ManifestUpdate:
					am = CNETMsg_SpawnGroup_ManifestUpdate.parseFrom(data);
					break;
				case net_SpawnGroup_SetCreationTick:
					am = CNETMsg_SpawnGroup_SetCreationTick.parseFrom(data);
					break;
				case net_SpawnGroup_Unload:
					am = CNETMsg_SpawnGroup_Unload.parseFrom(data);
					break;
				case net_SplitScreenUser:
					am = CNETMsg_SplitScreenUser.parseFrom(data);
					break;
				case net_StringCmd:
					am = CNETMsg_StringCmd.parseFrom(data);
					break;
				case net_Tick:
					am = CNETMsg_Tick.parseFrom(data);
					break;
				default:
					break;
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return am;
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
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return am;
	}

}
