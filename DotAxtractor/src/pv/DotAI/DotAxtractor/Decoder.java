package pv.DotAI.DotAxtractor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

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
	
	public static int getVarInt(ByteBuffer b) {
		int result = 0;
		int position = 0;
		byte i = 0;
		do {
			i = b.get();
			result |= (i & 0x7F) << (position * 7); //remove msb
			position++;
		} while((i & 0x80) != 0); //while the msb != 0
		return result;
	}
}
