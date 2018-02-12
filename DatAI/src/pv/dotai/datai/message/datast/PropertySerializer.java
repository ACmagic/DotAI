package pv.dotai.datai.message.datast;

import pv.dotai.datai.message.datast.decoder.DecodeFunc;

public class PropertySerializer {
	private DecodeFunc decode;
	private DecodeFunc decodeContainer;
	private boolean isArray;
	private int length;
	private PropertySerializer arraySerializer;
	private String name;
	
	public PropertySerializer(DecodeFunc decode, DecodeFunc decodeContainer, boolean isArray, int length, PropertySerializer arraySerializer, String name) {
		this.decode = decode;
		this.decodeContainer = decodeContainer;
		this.isArray = isArray;
		this.length = length;
		this.arraySerializer = arraySerializer;
		this.name = name;
	}

	public DecodeFunc getDecode() {
		return decode;
	}

	public void setDecode(DecodeFunc decode) {
		this.decode = decode;
	}

	public DecodeFunc getDecodeContainer() {
		return decodeContainer;
	}

	public void setDecodeContainer(DecodeFunc decodeContainer) {
		this.decodeContainer = decodeContainer;
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public PropertySerializer getArraySerializer() {
		return arraySerializer;
	}

	public void setArraySerializer(PropertySerializer arraySerializer) {
		this.arraySerializer = arraySerializer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
