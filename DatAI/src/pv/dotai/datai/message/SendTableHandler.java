package pv.dotai.datai.message;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.InvalidProtocolBufferException;

import pv.dotai.datai.protobuf.Demo.CDemoSendTables;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_FlattenedSerializer;

public class SendTableHandler implements MessageHandler<CDemoSendTables>{

	private MessageRouter router;
	
	public SendTableHandler(MessageRouter router) {
		this.router = router;
	}
	
	@Override
	public void handle(CDemoSendTables m) {
		CodedInputStream cis = CodedInputStream.newInstance(m.getData().asReadOnlyByteBuffer());
		try {
			CSVCMsg_FlattenedSerializer protoMessage = CSVCMsg_FlattenedSerializer.parseFrom(cis.readRawBytes(cis.readRawVarint32()));
			router.sendMessage(protoMessage);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
