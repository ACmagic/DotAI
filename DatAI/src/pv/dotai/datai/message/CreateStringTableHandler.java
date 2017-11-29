package pv.dotai.datai.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.xerial.snappy.Snappy;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.message.datast.StringTable;
import pv.dotai.datai.message.datast.StringTableItem;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_CreateStringTable;

public class CreateStringTableHandler implements MessageHandler<CSVCMsg_CreateStringTable> {

	@Override
	public void handle(CSVCMsg_CreateStringTable m) {
		StringTable t = new StringTable(ReplayBuilder.getInstance().getStringTables().getNextIndex(), m.getName(), m.getUserDataFixedSize(), m.getUserDataSize());
		ReplayBuilder.getInstance().getStringTables().setNextIndex(ReplayBuilder.getInstance().getStringTables().getNextIndex()+1);
		ByteBuffer b = m.getStringData().asReadOnlyByteBuffer();
		if(m.getDataCompressed()) {
			byte[] compression = new byte[4];
			b.get(compression);
			b.position(0);
			if(new String(compression).equals("LZSS")) {
				//TODO support LZSS compression for old replay
				throw new RuntimeException("LZSS compression not supported");
			} else {
				try {
					byte[] backarray = new byte[b.capacity()];
					b.get(backarray, 0, backarray.length);
					b = ByteBuffer.wrap(Snappy.uncompress(backarray));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		List<StringTableItem> items = StringTable.parseStringTable(b, m.getNumEntries(), t.isUserDataFixedSize(), t.getUserDataSize());
		
		for (StringTableItem item : items) {
			t.getItems().put(item.getIndex(), item);
		}
		
		ReplayBuilder.getInstance().getStringTables().getTables().put(t.getIndex(), t);
		ReplayBuilder.getInstance().getStringTables().getNameIndex().put(t.getName(), t.getIndex());
		
		if(t.getName() == "instancebaseline") {
			ReplayBuilder.getInstance().updateInstanceBaseline();
		}
	}

}
