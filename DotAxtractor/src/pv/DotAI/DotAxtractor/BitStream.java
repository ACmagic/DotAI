package pv.DotAI.DotAxtractor;

import java.nio.ByteBuffer;

public class BitStream {
	
	private int position;
	private int bitposition;
	private ByteBuffer buffer;
	
	public BitStream(ByteBuffer b) {
		this.buffer = b;
		this.position = 0;
		this.bitposition = 7;
	}
	
	public int nextBit() {
		if(position >= buffer.capacity()) {
			throw new IndexOutOfBoundsException("Buffer ended");
		}
		int bit = ((buffer.get(position)) & (1 << (bitposition))) >> bitposition;
		bitposition--;
		if(bitposition == -1) {
			bitposition = 7;
			position++;
		}
		return bit;
	}
	
	public int readBits(int n) {
		if(n > 32) {
			throw new IllegalArgumentException("Cannot read more than 32 bits at a time !");
		}
		int result = 0;
		for (int i = 0; i < n; i++) {
			int t = nextBit();
			result |= t << (i);
		}
		return result;
	}
	
	public void get(byte[] buffer) {
		for (int i = 0; i < buffer.length; i++) {
			if(remaining() > 8) {
				buffer[i] = (byte) readBits(8);
			} else {
				buffer[i] = (byte) readBits(remaining());
			}
		}
	}
	
	public int remaining() {
		return (buffer.capacity() - position) * 8 - (7 - bitposition);
	}

}
