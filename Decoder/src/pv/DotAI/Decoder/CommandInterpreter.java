package pv.DotAI.Decoder;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.Decoder.Dem.CDemoClassInfo;
import pv.DotAI.Decoder.Dem.CDemoConsoleCmd;
import pv.DotAI.Decoder.Dem.CDemoCustomData;
import pv.DotAI.Decoder.Dem.CDemoCustomDataCallbacks;
import pv.DotAI.Decoder.Dem.CDemoFileHeader;
import pv.DotAI.Decoder.Dem.CDemoFileInfo;
import pv.DotAI.Decoder.Dem.CDemoFullPacket;
import pv.DotAI.Decoder.Dem.CDemoPacket;
import pv.DotAI.Decoder.Dem.CDemoSaveGame;
import pv.DotAI.Decoder.Dem.CDemoSendTables;
import pv.DotAI.Decoder.Dem.CDemoStringTables;
import pv.DotAI.Decoder.Dem.CDemoSyncTick;
import pv.DotAI.Decoder.Dem.CDemoUserCmd;
import pv.DotAI.Decoder.Dem.EDemoCommands;

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
					am = CDemoSaveGame.parseFrom(msg);
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

}
