package pv.dotai.datai.message;

import java.util.List;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.message.datast.StringTable;
import pv.dotai.datai.message.datast.StringTableItem;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_UpdateStringTable;

/**
 * Handler for CSVCMsg_UpdateStringTable messages (svc_UpdateStringTable)
 * @author Thomas Ibanez
 * @since  1.0
 */
public class UpdateStringTableHandler implements MessageHandler<CSVCMsg_UpdateStringTable> {

	@Override
	public void handle(CSVCMsg_UpdateStringTable m) {
		StringTable t = ReplayBuilder.getInstance().getStringTables().getTables().get(m.getTableId());
		if(t == null) {
			System.err.println("Recieved update for a table that doesn't exists");
			return;
		}
		List<StringTableItem> items = StringTable.parseStringTable(m.getStringData().asReadOnlyByteBuffer(), m.getNumChangedEntries(), t.isUserDataFixedSize(), t.getUserDataSize());
		
		for (StringTableItem item : items) {
			int idx = item.getIndex();
			if(t.getItems().get(idx) != null) {
				if(!item.getKey().isEmpty() && !item.getKey().equals(t.getItems().get(idx).getKey())) {
					t.getItems().get(idx).setKey(item.getKey());
				}
				if(item.getValue() != null) {
					t.getItems().get(idx).setValue(item.getValue());
				}
			} else {
				t.getItems().put(idx, item);
			}
		}
		
		if(t.getName().equals("instancebaseline")) {
			ReplayBuilder.getInstance().updateInstanceBaseline();
		}
	}
}
