package pv.dotai.datai.message;

import pv.dotai.datai.protobuf.Demo.CDemoFullPacket;

/**
 * Handler for CDemoFullPacket messages (DEM_FullPacket)
 * @author Thomas Ibanez
 * @since  1.0
 */
public class FullPacketHandler implements MessageHandler<CDemoFullPacket> {

	private MessageRouter router;
	
	public FullPacketHandler(MessageRouter router) {
		this.router = router;
	}
	
	@Override
	public void handle(CDemoFullPacket m) {
		this.router.sendMessage(m.getStringTable());
		this.router.sendMessage(m.getPacket());
	}

}
