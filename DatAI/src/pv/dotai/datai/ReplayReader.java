package pv.dotai.datai;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.xerial.snappy.Snappy;

import pv.dotai.datai.message.FileHeaderHandler;
import pv.dotai.datai.message.MessageRouter;
import pv.dotai.datai.message.PacketHandler;
import pv.dotai.datai.message.StopHandler;
import pv.dotai.datai.protobuf.Demo.CDemoClassInfo;
import pv.dotai.datai.protobuf.Demo.CDemoConsoleCmd;
import pv.dotai.datai.protobuf.Demo.CDemoCustomData;
import pv.dotai.datai.protobuf.Demo.CDemoCustomDataCallbacks;
import pv.dotai.datai.protobuf.Demo.CDemoFileHeader;
import pv.dotai.datai.protobuf.Demo.CDemoFileInfo;
import pv.dotai.datai.protobuf.Demo.CDemoFullPacket;
import pv.dotai.datai.protobuf.Demo.CDemoPacket;
import pv.dotai.datai.protobuf.Demo.CDemoSendTables;
import pv.dotai.datai.protobuf.Demo.CDemoStop;
import pv.dotai.datai.protobuf.Demo.CDemoStringTables;
import pv.dotai.datai.protobuf.Demo.CDemoSyncTick;
import pv.dotai.datai.protobuf.Demo.CDemoUserCmd;
import pv.dotai.datai.protobuf.Demo.EDemoCommands;

public class ReplayReader {

	public FileInputStream file;
	private final String EXPECTED_HEADER = "PBDEMS2\0";
	private ReplayBuilder replayBuilder;
	private MessageRouter router;
	
	public ReplayReader() {
		this.replayBuilder = new ReplayBuilder();
		this.router = new MessageRouter();
		
		this.router.registerHandler(CDemoFileHeader.class, new FileHeaderHandler());
		this.router.registerHandler(CDemoPacket.class, new PacketHandler());
		this.router.registerHandler(CDemoStop.class, new StopHandler());

	}
	
	public void readFile(String path) throws ReplayException {
		try {
			this.file = new FileInputStream(path);
			DataInputStream dis = new DataInputStream(file);
			byte[] header = new byte[8]; //7 char + nul
			dis.read(header);
			if(!new String(header).equals(this.EXPECTED_HEADER)) {
				throw new ReplayException("File isn't a DotA 2 (Source 2) Demo File Standard Header");
			}
			//Two mysterious integers...
			dis.readInt();
			dis.readInt();
			//Read all atoms
			while(dis.available() > 0) {
				int commandID = getVarInt(dis);
				boolean compressed = (commandID & EDemoCommands.DEM_IsCompressed.getNumber()) == EDemoCommands.DEM_IsCompressed.getNumber();
				commandID &= ~EDemoCommands.DEM_IsCompressed.getNumber();
				int tick = getVarInt(dis);
				int size = getVarInt(dis);
				byte[] msg = new byte[size];
				dis.read(msg, 0, size);
				if(compressed) {
					msg = Snappy.uncompress(msg);
					size = msg.length;
				}
				
				EDemoCommands cmd = EDemoCommands.forNumber(commandID);
				switch (cmd) {
					case DEM_ClassInfo:
						router.sendMessage(CDemoClassInfo.parseFrom(msg));
						break;
					case DEM_ConsoleCmd:
						router.sendMessage(CDemoConsoleCmd.parseFrom(msg));
						break;
					case DEM_CustomData:
						router.sendMessage(CDemoCustomData.parseFrom(msg));
						break;
					case DEM_CustomDataCallbacks:
						router.sendMessage(CDemoCustomDataCallbacks.parseFrom(msg));
						break;
					case DEM_Error:
						break;
					case DEM_FileHeader:
						router.sendMessage(CDemoFileHeader.parseFrom(msg));
						break;
					case DEM_FileInfo:
						router.sendMessage(CDemoFileInfo.parseFrom(msg));
						break;
					case DEM_FullPacket:
						router.sendMessage(CDemoFullPacket.parseFrom(msg).getPacket());
						break;
					case DEM_IsCompressed:
						break;
					case DEM_Max:
						break;
					case DEM_SignonPacket:
					case DEM_Packet:
						router.sendMessage(CDemoPacket.parseFrom(msg));
						break;
					case DEM_SaveGame:
						break;
					case DEM_SendTables:
						router.sendMessage(CDemoSendTables.parseFrom(msg));
						break;
					case DEM_Stop:
						router.sendMessage(CDemoStop.parseFrom(msg));
						break;
					case DEM_StringTables:
						router.sendMessage(CDemoStringTables.parseFrom(msg));
						break;
					case DEM_SyncTick:
						router.sendMessage(CDemoSyncTick.parseFrom(msg));
						break;
					case DEM_UserCmd:
						router.sendMessage(CDemoUserCmd.parseFrom(msg));
						break;
					default:
						break;

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}	
	
	public int getVarInt(InputStream is) throws IOException {
		int result = 0;
		int position = 0;
		int i = 0;
		do {
			i = is.read();
			result |= (i & 0x7F) << (position * 7); //remove msb
			position++;
		} while((i & 0x80) != 0); //while the msb != 0
		return result;
	}
}
