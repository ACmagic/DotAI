package pv.DotAI.DotAxtractor;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.xerial.snappy.Snappy;

import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFullPacket;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoPacket;
import pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;

public class ReplayReader {

	public FileInputStream file;
	private final String EXPECTED_HEADER = "PBDEMS2\0";
	private CommandInterpreter commandInterpreter;
	private ReplayBuilder replayBuilder;
	
	public ReplayReader() {
		this.commandInterpreter = new CommandInterpreter();
		this.replayBuilder = new ReplayBuilder();
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
				int commandID = Decoder.getVarInt(dis);
				boolean compressed = (commandID & EDemoCommands.DEM_IsCompressed.getNumber()) == EDemoCommands.DEM_IsCompressed.getNumber();
				commandID &= ~EDemoCommands.DEM_IsCompressed.getNumber();
				int tick = Decoder.getVarInt(dis);
				int size = Decoder.getVarInt(dis);
				byte[] message = new byte[size];
				dis.read(message, 0, size);
				if(compressed) {
					message = Snappy.uncompress(message);
					size = message.length;
				}
				
				EDemoCommands cmd = EDemoCommands.forNumber(commandID);
				Atom a = null;
				
				if(cmd == EDemoCommands.DEM_Packet || cmd == EDemoCommands.DEM_SignonPacket) {
					CDemoPacket packet = (CDemoPacket) commandInterpreter.getMessage(cmd, message);
					a = new EmbeddedDataAtom(cmd, tick, size, packet, commandInterpreter.extractPacketData(packet));	
				} else if(cmd == EDemoCommands.DEM_FullPacket){
					CDemoFullPacket packet = (CDemoFullPacket) commandInterpreter.getMessage(cmd, message);
					a = new DoubleAtom(cmd, tick, size, packet, new EmbeddedDataAtom(EDemoCommands.DEM_Packet, tick, packet.getPacket().getData().size(), packet.getPacket(), commandInterpreter.extractPacketData(packet.getPacket())));
				} else {
					a = new SingleAtom(cmd, tick, size, commandInterpreter.getMessage(cmd, message));
				}
				this.replayBuilder.parseAtom(a);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}	
}
