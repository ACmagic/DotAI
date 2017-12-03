package pv.dotai.datai.message.datast.decoder;

import pv.dotai.datai.message.datast.DataTableField;
import pv.dotai.datai.util.BitStream;

public interface DecodeFunc {
	Object decode(BitStream bs, DataTableField dtf);
}
