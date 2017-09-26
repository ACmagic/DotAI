package ch.hepia.DotAI.Decoder;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Decoder {

	public static void main(String[] args) {
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(args[0]));
			byte[] h1 = new byte[8];
			in.readFully(h1);
			System.out.println("Header: "+new String(h1));
			byte[] offset = new byte[4];
			in.readFully(offset);
			System.out.println("Offset: "+littleEndianInt(offset));
			in.close();
		} catch (IOException ignored) {
		    System.out.println("[EOF]");
		}
	}
	
	
	public static int littleEndianInt(byte[] b) {
		return (ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt());
	}
}
