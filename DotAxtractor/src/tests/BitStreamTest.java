package tests;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import pv.dotai.datai.util.BitStream;

public class BitStreamTest {

	@Test
	public void remainingTest() {
		byte[] data = {
				0, 2, 0, 1
		};
		BitStream bs = new BitStream(ByteBuffer.wrap(data));
		assertEquals(4*8, bs.remaining());
		for (int i = 0; i < 4*8; i++) {
			bs.nextBit();
			assertEquals(4*8-i-1, bs.remaining());
		}
		bs = new BitStream(ByteBuffer.wrap(data));
		bs.readBits(5);
		assertEquals(4*8-5, bs.remaining());
		bs.readBits(17);
		assertEquals(4*8-5-17, bs.remaining());
	}
	
	@Test
	public void nextBitTest() {
		byte[] data = {
				0xA, 0, (byte) 0xAA, 1
		};
		BitStream bs = new BitStream(ByteBuffer.wrap(data));
		while(bs.remaining() > 0) {
			System.out.print(bs.nextBit());
		}

	}
	
	@Test
	public void readBitsTest() {
		byte[] data = {
				0xA, 0, 0xF, 1
		};
		BitStream bs = new BitStream(ByteBuffer.wrap(data));
		for (int i = 0; i < data.length; i++) {
			assertEquals(data[i], bs.readBits(8));
		}
		bs = new BitStream(ByteBuffer.wrap(data));
		assertEquals(0, bs.readBits(4));
		assertEquals(0xA, bs.readBits(4));
		assertEquals(0, bs.readBits(6));
		//assertEquals(0xF, bs.readBits(10));
	}
	
	@Test
	public void combinedTest() {
		byte[] data = {
				(byte) 0x80, (byte) 0xFF
		};
		BitStream bs = new BitStream(ByteBuffer.wrap(data));
		assertEquals(1, bs.nextBit());
		assertEquals(0, bs.nextBit());
		assertEquals(1, bs.readBits(7));
	}

}
