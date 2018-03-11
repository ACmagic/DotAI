package pv.dotai.datai.message;

import pv.dotai.datai.protobuf.Demo.CDemoFileHeader;

/**
 * Handler for CDemoFileHeader messages (DEM_FileHeader)
 * @author Thomas Ibanez
 * @since  1.0
 */
public class FileHeaderHandler implements MessageHandler<CDemoFileHeader> {

	@Override
	public void handle(CDemoFileHeader message) {
		System.out.println(message.toString());
	}
	
}
