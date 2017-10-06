package pv.DotAI.DotAxtractor;

import java.io.IOException;
import java.io.InputStream;

public class Decoder {
	
	public static void main(String[] args) {
		try {
			new ReplayReader().readFile(args[0]);
		} catch (ReplayException e) {
			e.printStackTrace();
		}
	}
	
	//Google varint decoding
	public static int getVarInt(InputStream is) throws IOException {
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
	
	public static int getVarInt(BitStream bs) {
		int result = 0;
		int position = 0;
		int i = 0;
		do {
			i = bs.readBits(8);
			result |= (i & 0x7F) << (position * 7); //remove msb
			position++;
		} while((i & 0x80) != 0); //while the msb != 0
		return result;
	}
	
	public static int getBitVar(BitStream bs) {
		int base = bs.readBits(6);
		if((base & 0x30) == 0x30) {
			base = (base & 0x15) | (bs.readBits(28) << 4);
		} else if((base & 0x30) == 0x20) {
			base = (base & 0x15) | (bs.readBits(8) << 4); 
		} else if((base & 0x30) == 0x10) {
			base = (base & 0x15) | (bs.readBits(4) << 4); 
		}
		return base;
	}
}
