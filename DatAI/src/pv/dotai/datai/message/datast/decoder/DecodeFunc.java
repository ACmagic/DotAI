package pv.dotai.datai.message.datast.decoder;

import pv.dotai.datai.message.datast.DataTableField;
import pv.dotai.datai.util.BitStream;

/**
 * Interface to implement when making a decoder
 * @author Thomas Ibanez
 * @since  1.0
 */
public interface DecodeFunc {
	Object decode(BitStream bs, DataTableField dtf);
}
