package pv.dotai.datai.message;

import pv.dotai.datai.protobuf.Demo.CDemoFileHeader;

public class FileHeaderHandler implements MessageHandler<CDemoFileHeader> {

	@Override
	public void handle(CDemoFileHeader message) {
		System.out.println("Header");
	}
	
}
