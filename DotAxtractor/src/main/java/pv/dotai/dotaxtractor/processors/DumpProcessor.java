package pv.dotai.dotaxtractor.processors;

import com.google.protobuf.GeneratedMessage;

import skadistats.clarity.processor.reader.OnMessage;
import skadistats.clarity.wire.s1.proto.S1NetMessages;
import skadistats.clarity.wire.s2.proto.S2NetMessages;

public class DumpProcessor {

	@OnMessage(GeneratedMessage.class)
	public void onMessage(GeneratedMessage m) {
		if(m instanceof S1NetMessages.CSVCMsg_VoiceData || m instanceof S2NetMessages.CSVCMsg_VoiceData) {
			return;
		}
		System.out.println(m.getClass().getName()+":: "+m.toString());
	}
}
