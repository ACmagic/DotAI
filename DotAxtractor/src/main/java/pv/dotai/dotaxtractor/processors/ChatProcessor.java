package pv.dotai.dotaxtractor.processors;

import skadistats.clarity.processor.reader.OnMessage;
import skadistats.clarity.processor.runner.Context;
import skadistats.clarity.wire.s2.proto.S2UserMessages;

public class ChatProcessor {

	@OnMessage(S2UserMessages.CUserMessageSayText2.class)
	public void onMessage(Context ctx, S2UserMessages.CUserMessageSayText2 message) {
		System.out.format("%s: %s\n", message.getParam1(), message.getParam2());
	}
	
}
