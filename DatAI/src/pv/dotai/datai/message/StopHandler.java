package pv.dotai.datai.message;

import pv.dotai.datai.protobuf.Demo.CDemoStop;

/**
 * Handler for CDemoStop messages (DEM_Stop)
 * @author Thomas Ibanez
 * @since  1.0
 */
public class StopHandler implements MessageHandler<CDemoStop> {

	@Override
	public void handle(CDemoStop message) {
		System.out.println("Replay Stop");
	}

}
