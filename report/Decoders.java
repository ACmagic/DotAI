public class Decoders {

	public static double decodeSimTime(BitStream bs, DataTableField dtf) {
		return bs.readVarUInt32() * (1.0 / 30.0);
	}
	
	public static float decodeFloat(BitStream bs, DataTableField dtf) {
		if(dtf.getEncoder() != null && dtf.getEncoder().equals("coord")) {
			return bs.readCoord();
		}
		
		if(dtf.getBitCount() <= 0 || dtf.getBitCount() >= 32) {
			return decodeFloatNoscale(bs, dtf);
		}
		
		return decodeQuantized(bs, dtf);
	}
	
	public static float decodeFloatNoscale(BitStream bs, DataTableField dtf) {
		return Float.intBitsToFloat(bs.readBits(32));
	}
	
	public static float decodeQuantized(BitStream bs, DataTableField dtf) {
		return new FloatQuantizedDecoder(dtf.getName(), dtf.getBitCount(), dtf.getFlags(), dtf.getLowValue(), dtf.getHighValue()).decode(bs);
	}
	
	public static int decodeSigned(BitStream bs, DataTableField dtf) {
		return bs.readVarUInt32();
	}
	
	public static long decodeUnsigned(BitStream bs, DataTableField dtf) {
		if(dtf.getEncoder() != null && dtf.getEncoder().equals("fixed64")) {
			return bs.readLittleEndian64();
		}
		return bs.readVarUInt64();
	}
	
	public static long decodeLEint64(BitStream bs, DataTableField dtf) {
		return bs.readLittleEndian64();
	}
	
	public static String decodeString(BitStream bs, DataTableField dtf) {
		return bs.readString();
	}
	
	public static float[] decodeFVector(BitStream bs, DataTableField dtf) {
		if(dtf.getEncoder() != null && dtf.getEncoder().equals("normal")) {
			return bs.read3fNormal();
		}
		return new float[] {decodeFloat(bs, dtf), decodeFloat(bs, dtf), decodeFloat(bs, dtf)};
	}
	
	public static boolean decodeBoolean(BitStream bs, DataTableField dtf) {
		return bs.nextBit() == 1;
	}
	
	public static int decodeComponent(BitStream bs, DataTableField dtf) {
		return bs.nextBit();
	}
	
	public static float[] decodeQAngle(BitStream bs, DataTableField dtf) {
		float[] q = new float[3];
		if(dtf.getEncoder() != null && dtf.getEncoder().equals("qangle_pitch_yaw")) {
			q[0] = bs.readAngle(dtf.getBitCount());
			q[1] = bs.readAngle(dtf.getBitCount());
			return q;
		}
		
		if(dtf.getBitCount() == 32) {
			throw new ReplayException("Unknow angle type");
		} else if(dtf.getBitCount() != 0) {
			q[0] = bs.readAngle(dtf.getBitCount());
			q[1] = bs.readAngle(dtf.getBitCount());
			q[2] = bs.readAngle(dtf.getBitCount());
			return q;
		} else {
			boolean rX = bs.nextBit() == 1;
			boolean rY = bs.nextBit() == 1;
			boolean rZ = bs.nextBit() == 1;
			
			if(rX)
				q[0] = bs.readCoord();
			if(rY)
				q[1] = bs.readCoord();
			if(rZ)
				q[2] = bs.readCoord();
			
			return q;
		}
	}
	
	public static int decodeHandle(BitStream bs, DataTableField dtf) {
		return bs.readVarUInt32();
	}
	
	public static float[] decodeVector2D(BitStream bs, DataTableField dtf) {
		return new float[] {bs.readFloat(), bs.readFloat()};
	}
	
	public static int decodeVector(BitStream bs, DataTableField dtf) {
		return bs.readVarUInt32();
	}
