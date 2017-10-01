package pv.DotAI.DotAxtractor;

import java.nio.ByteBuffer;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.DotAxtractor.Dem.CDemoClassInfo;
import pv.DotAI.DotAxtractor.Dem.CDemoConsoleCmd;
import pv.DotAI.DotAxtractor.Dem.CDemoCustomData;
import pv.DotAI.DotAxtractor.Dem.CDemoCustomDataCallbacks;
import pv.DotAI.DotAxtractor.Dem.CDemoFileHeader;
import pv.DotAI.DotAxtractor.Dem.CDemoFileInfo;
import pv.DotAI.DotAxtractor.Dem.CDemoFullPacket;
import pv.DotAI.DotAxtractor.Dem.CDemoPacket;
import pv.DotAI.DotAxtractor.Dem.CDemoSaveGame;
import pv.DotAI.DotAxtractor.Dem.CDemoSendTables;
import pv.DotAI.DotAxtractor.Dem.CDemoStringTables;
import pv.DotAI.DotAxtractor.Dem.CDemoSyncTick;
import pv.DotAI.DotAxtractor.Dem.CDemoUserCmd;
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
		EmbedData ed = null;
		while(b.hasRemaining()) {
			int commandID = Decoder.getVarInt(b);
			int size = Decoder.getVarInt(b);
			
			if(SVC_Messages.forNumber(commandID) != null) {
				return new EmbedData(SVC_Messages.forNumber(commandID));
			} else if(NET_Messages.forNumber(commandID) != null) {
				return new EmbedData(NET_Messages.forNumber(commandID));
			} else {
				throw new ReplayException("Packet has unknown embed data");
			}
		}
	}
	
	private void extractSVCMessageData(byte[])

}
