package pv.dotai.datai.message.datast;

import java.util.HashMap;
import java.util.Map;

import pv.dotai.datai.util.BitStream;

public class Property {

	private Map<String, Object> KV;
	
	public Property() {
		KV = new HashMap<>();
	}
	
	public void readProperties(BitStream bs, DataTable ser) {
		FieldPath fp = new FieldPath(ser);
		fp.walk(bs);
		
		for (FieldPathField field : fp.getFields()) {
			if(field.getField().getSerializer() == null) {
				System.err.println("Null serializer");
				continue;
			}
			if(field.getField().getSerializer().getDecodeContainer() != null) {
				this.KV.put(field.getName(), field.getField().getSerializer().getDecodeContainer().decode(bs, field.getField()));
			} else if(field.getField().getSerializer().getDecode() == null) {
				this.KV.put(field.getName(), bs.readVarUInt32());
				continue;
			} else {
				this.KV.put(field.getName(), field.getField().getSerializer().getDecode().decode(bs, field.getField()));
			}
			
			System.out.println("Decoding name="+field.getName()+" type="+field.getField().getType()+" value="+KV.get(field.getName()));
		}
	}
}
