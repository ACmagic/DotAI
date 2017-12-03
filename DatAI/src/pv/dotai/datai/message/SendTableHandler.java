package pv.dotai.datai.message;

import com.google.protobuf.InvalidProtocolBufferException;

import pv.dotai.datai.protobuf.Demo.CDemoSendTables;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_FlattenedSerializer;
import pv.dotai.datai.util.BitStream;

public class SendTableHandler implements MessageHandler<CDemoSendTables>{

	private MessageRouter router;
	
	public SendTableHandler(MessageRouter router) {
		this.router = router;
	}
	
	@Override
	public void handle(CDemoSendTables m) {
		BitStream bs = new BitStream(m.getData().asReadOnlyByteBuffer());
		int size = bs.getVarInt();
		byte[] bserializer = new byte[size];
		bs.get(bserializer);
		try {
			router.sendMessage(CSVCMsg_FlattenedSerializer.parseFrom(bserializer));
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

}
