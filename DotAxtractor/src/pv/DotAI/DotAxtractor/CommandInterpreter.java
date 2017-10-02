package pv.DotAI.DotAxtractor;

import java.nio.ByteBuffer;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ProtocolMessageEnum;

import pv.DotAI.DotAxtractor.Dem.CDemoClassInfo;
import pv.DotAI.DotAxtractor.Dem.CDemoConsoleCmd;
import pv.DotAI.DotAxtractor.Dem.CDemoCustomData;
import pv.DotAI.DotAxtractor.Dem.CDemoCustomDataCallbacks;
import pv.DotAI.DotAxtractor.Dem.CDemoFileHeader;
import pv.DotAI.DotAxtractor.Dem.CDemoFileInfo;
import pv.DotAI.DotAxtractor.Dem.CDemoFullPacket;
import pv.DotAI.DotAxtractor.Dem.CDemoPacket;
import pv.DotAI.DotAxtractor.Dem.CDemoSendTables;
import pv.DotAI.DotAxtractor.Dem.CDemoStringTables;
import pv.DotAI.DotAxtractor.Dem.CDemoSyncTick;
import pv.DotAI.DotAxtractor.Dem.CDemoUserCmd;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_BSPDecal;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_ClassInfo;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_CreateStringTable;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_CrosshairAngle;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_FixAngle;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_FullFrameSplit;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_GameEvent;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_GameEventList;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_GetCvarValue;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_Menu;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_PacketEntities;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_PacketReliable;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_Prefetch;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_Print;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_SendTable;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_ServerInfo;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_SetPause;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_SetView;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_Sounds;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_SplitScreen;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_TempEntities;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_UpdateStringTable;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_UserMessage;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_VoiceData;
import pv.DotAI.DotAxtractor.Dem.CSVCMsg_VoiceInit;
import pv.DotAI.DotAxtractor.Dem.EDemoCommands;
import pv.DotAI.DotAxtractor.Dem.NET_Messages;
import pv.DotAI.DotAxtractor.Dem.SVC_Messages;

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

	public EmbedData extractPacketData(CDemoPacket packet) throws ReplayException {
		ByteBuffer b = packet.getData().asReadOnlyByteBuffer();
		while (b.hasRemaining()) {
			int commandID = Decoder.getVarInt(b);
			int size = Decoder.getVarInt(b);

			if (SVC_Messages.forNumber(commandID) != null) {
				ProtocolMessageEnum type = SVC_Messages.forNumber(commandID);
				new EmbedData(type, false, extractSVCMessageData(type, size, packet.getData().asReadOnlyByteBuffer()));
			} else if (NET_Messages.forNumber(commandID) != null) {
				new EmbedData(NET_Messages.forNumber(commandID), true, null); //Nobody cares about net messages
			} else {
				System.out.println("Packet has unknown embed data id: "+commandID);
				//throw new ReplayException("Packet has unknown embed data id: "+commandID);
			}

		}
		return null;
	}

	private AbstractMessage extractSVCMessageData(ProtocolMessageEnum type, int size, ByteBuffer buffer) {
		SVC_Messages msg = (SVC_Messages) type;
		AbstractMessage am = null;
		byte[] data = new byte[size];
		buffer.get(data);
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
				case svc_CrosshairAngle:
					am = CSVCMsg_CrosshairAngle.parseFrom(data);
					break;
				case svc_EntityMessage:
					//TODO Find what's that
					break;
				case svc_FixAngle:
					am = CSVCMsg_FixAngle.parseFrom(data);
					break;
				case svc_FullFrameSplit:
					am = CSVCMsg_FullFrameSplit.parseFrom(data);
					break;
				case svc_GameEvent:
					am = CSVCMsg_GameEvent.parseFrom(data);
					break;
				case svc_GameEventList:
					am = CSVCMsg_GameEventList.parseFrom(data);
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
				case svc_SendTable:
					am = CSVCMsg_SendTable.parseFrom(data);
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
				case svc_TempEntities:
					am = CSVCMsg_TempEntities.parseFrom(data);
					break;
				case svc_UpdateStringTable:
					am = CSVCMsg_UpdateStringTable.parseFrom(data);
					break;
				case svc_UserMessage:
					am = CSVCMsg_UserMessage.parseFrom(data);
					break;
				case svc_VoiceData:
					am = CSVCMsg_VoiceData.parseFrom(data);
					break;
				case svc_VoiceInit:
					am = CSVCMsg_VoiceInit.parseFrom(data);
					break;
				default:
					break;
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return am;
	}

}
