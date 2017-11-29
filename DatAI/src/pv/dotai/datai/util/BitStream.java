package pv.dotai.datai.util;

import java.nio.ByteBuffer;

public class BitStream {
	
	private int position;
	private int bitposition;
	private ByteBuffer buffer;
	
	public BitStream(ByteBuffer b) {
		this.buffer = b;
		this.position = 0;
		this.bitposition = 0;
	}
	
	public int nextBit() {
		if(position >= buffer.capacity()) {
			throw new IndexOutOfBoundsException("Buffer ended");
		}
		int bit = ((buffer.get(position)) & (1 << (bitposition))) >> bitposition;
		bitposition++;
		if(bitposition == 8) {
			bitposition = 0;
			position++;
		}
		return bit;
	}
	
	public int readBits(int n) {
		if(n > 32) {
			throw new IllegalArgumentException("Cannot read more than 32 bits at a time !");
		}
		int tmp = 0;
		for (int i = 0; i < n; i++) {
			int t = nextBit();
			tmp |= t << (i);
		}
		return tmp;
	}
	
	public byte[] readBitsAsBytes(int nbits) {
		byte[] bytes = new byte[(int) Math.round(Math.ceil(nbits / 8.0))];
		int writeIdx = 0;
		for(; nbits >= 8; nbits -= 8) {
			bytes[writeIdx] = (byte) this.readBits(8);
			writeIdx++;
		}
		if(nbits > 0) {
			bytes[writeIdx] = (byte) this.readBits(nbits);
		}
		return bytes;
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
	
	public String readString() {
		StringBuilder sb = new StringBuilder();
		int b = this.readBits(8);
		while(b != 0) {
			sb.append((char)b);
			b = this.readBits(8);
		}
		return sb.toString();
	}
	
	public int getBitVar() {
		int base = this.readBits(6);
		if((base & 0x30) == 0x30) {
			base = (base & 0xF) | (this.readBits(28) << 4);
		} else if((base & 0x30) == 0x20) {
			base = (base & 0xF) | (this.readBits(8) << 4); 
		} else if((base & 0x30) == 0x10) {
			base = (base & 0xF) | (this.readBits(4) << 4); 
		}
		return base;
	}
	
	public int getVarInt() {
		int result = 0;
		int position = 0;
		int i = 0;
		do {
			i = this.readBits(8);
			result |= (i & 0x7F) << (position * 7); //remove msb
			position++;
		} while((i & 0x80) != 0); //while the msb != 0
		return result;
	}
	
	public int remaining() {
		return (buffer.capacity() - position) * 8 - bitposition;
	}

}
