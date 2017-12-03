package pv.dotai.datai.message.datast;

import pv.dotai.datai.message.datast.decoder.DecodeFunc;

public class PropertySerializer {
	private DecodeFunc decode;
	private DecodeFunc decodeContainer;
	private boolean isArray;
	private int length;
	private PropertySerializer arraySerializer;
	private String name;
	
}
