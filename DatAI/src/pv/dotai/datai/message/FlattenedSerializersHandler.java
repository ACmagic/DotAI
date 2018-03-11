package pv.dotai.datai.message;

import java.util.HashMap;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.message.datast.FlattenedSerializers;
import pv.dotai.datai.message.datast.PropertySerializerTable;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_FlattenedSerializer;
import pv.dotai.datai.protobuf.Netmessages.ProtoFlattenedSerializer_t;

/**
 * Handler for CSVCMsg_FlattenedSerializer messages (svc_FlattenedSerializer)
 * @author Thomas Ibanez
 * @since  1.0
 */
public class FlattenedSerializersHandler implements MessageHandler<CSVCMsg_FlattenedSerializer>{

	@Override
	public void handle(CSVCMsg_FlattenedSerializer m) {
		FlattenedSerializers fs = new FlattenedSerializers(m, new PropertySerializerTable(), ReplayBuilder.getInstance().GAME_BUILD);
		
		for (ProtoFlattenedSerializer_t s : m.getSerializersList()) {
			String name = m.getSymbols(s.getSerializerNameSym());
			int ver = s.getSerializerVersion();
						
			if(fs.getSerializers().get(name) == null) {
				fs.getSerializers().put(name, new HashMap<>());
			}
			fs.getSerializers().get(name).put(ver, fs.recurseTable(s));
		}
		
		ReplayBuilder.getInstance().setSerializers(fs.getSerializers());
	}
}
