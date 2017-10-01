package pv.DotAI.DotAxtractor;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.xerial.snappy.Snappy;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.DotAxtractor.Dem.EDemoCommands;

public class ReplayReader {

	public FileInputStream file;
	private final String EXPECTED_HEADER = "PBDEMS2\0";
	private CommandInterpreter commandInterpreter;
	
	public ReplayReader() {
		commandInterpreter = new CommandInterpreter();
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
				System.out.println(commandID);
				boolean compressed = (commandID & EDemoCommands.DEM_IsCompressed.getNumber()) != 0;
				commandID &= ~EDemoCommands.DEM_IsCompressed.getNumber();
				int tick = getVarInt(dis);
				int size = getVarInt(dis);
				byte[] message = new byte[size];
				dis.read(message);
				if(compressed) {
					message = Snappy.uncompress(message);
				}

				EDemoCommands cmd = EDemoCommands.forNumber(commandID);
				Atom a = new Atom(cmd, tick, size, commandInterpreter.getMessage(cmd, message));
				System.out.println("Read atom "+a.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	//Google varint decoding
	public int getVarInt(InputStream is) throws IOException {
		int result = 0;
		int position = 0;
		int i = 0;
		do {
			i = is.read();
			result |= (i & 0x7F) << (position * 7); //remove msb
			position++;
		} while((i & 0x80) != 0);
		return result;
	}
	
}
