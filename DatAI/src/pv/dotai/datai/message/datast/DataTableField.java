package pv.dotai.datai.message.datast;

public class DataTableField {
	private String name;
	private String encoder;
	private String type;
	private int index;
	
	private int flags;
	private int bitCount;
	private float lowValue;
	private float highValue;
	
	private int version;
	//TODO property serializer
	private Object serializer;
	
	private int build;

	public DataTableField(String name, String type, int index, int flags, int bitCount, float lowValue, float highValue, int version, Object serializer, int build) {
		this.name = name;
		this.type = type;
		this.index = index;
		this.flags = flags;
		this.bitCount = bitCount;
		this.lowValue = lowValue;
		this.highValue = highValue;
		this.version = version;
		this.serializer = serializer;
		this.build = build;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncoder() {
		return encoder;
	}

	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getBitCount() {
		return bitCount;
	}

	public void setBitCount(int bitCount) {
		this.bitCount = bitCount;
	}

	public float getLowValue() {
		return lowValue;
	}

	public void setLowValue(float lowValue) {
		this.lowValue = lowValue;
	}

	public float getHighValue() {
		return highValue;
	}

	public void setHighValue(float highValue) {
		this.highValue = highValue;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Object getSerializer() {
		return serializer;
	}

	public void setSerializer(Object serializer) {
		this.serializer = serializer;
	}

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		this.build = build;
	}
	
	
}
