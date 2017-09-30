package pv.DotAI.Decoder;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.xerial.snappy.Snappy;

import pv.DotAI.Decoder.Dem.EDemoCommands;

public class Decoder {
	
	public FileInputStream file;
	private final String EXPECTED_HEADER = "PBDEMS2\0";

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
				Atom a = new Atom(EDemoCommands.forNumber(commandID), tick, size, message);
				System.out.println("Read atom "+a.toString());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
	
	public static void main(String[] args) {
		try {
			new Decoder().readFile(args[0]);
		} catch (ReplayException e) {
			e.printStackTrace();
		}
	}
}
