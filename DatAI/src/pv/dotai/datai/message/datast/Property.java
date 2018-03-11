package pv.dotai.datai.message.datast;

import java.util.HashMap;
import java.util.Map;

import pv.dotai.datai.util.BitStream;

/**
 * Container for properties that we read
 * @author Thomas Ibanez
 * @since  1.0
 */
public class Property {

	private final Map<String, Object> KV;
	
	public Property() {
		KV = new HashMap<>();
	}

	/**
	 * Reads each properties on a stream, using the related serializer
	 * @param bs The bit stream to read infos from
	 * @param ser The DataTable of the type we are decoding
	 */
	public void readProperties(BitStream bs, DataTable ser) {
		FieldPath fp = new FieldPath(ser);
		fp.walk(bs);
		
		for (FieldPathField field : fp.getFields()) {
			if(field.getField().getSerializer().getDecodeContainer() != null) {
				this.KV.put(field.getName(), field.getField().getSerializer().getDecodeContainer().decode(bs, field.getField()));
			} else if(field.getField().getSerializer().getDecode() == null) {
				this.KV.put(field.getName(), bs.readVarUInt32());
				continue;
			} else {
				this.KV.put(field.getName(), field.getField().getSerializer().getDecode().decode(bs, field.getField()));
			}
		}
	}

	public Map<String, Object> getKV() {
		return KV;
	}
}
