package pv.dotai.datai.message;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.protobuf.Demo.CDemoStop;

public class StopHandler implements MessageHandler<CDemoStop> {

	@Override
	public void handle(CDemoStop message) {
		ReplayBuilder.getInstance().finish();
		System.out.println("Replay Stop");
	}

}
