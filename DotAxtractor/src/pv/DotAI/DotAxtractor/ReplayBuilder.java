package pv.DotAI.DotAxtractor;

import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFileHeader;

public class ReplayBuilder {

	private CDemoFileHeader header;
	
	public void parseAtom(Atom a) {
		a.parseAtom(this);
	}

	public CDemoFileHeader getHeader() {
		return header;
	}

	public void setHeader(CDemoFileHeader header) {
		this.header = header;
	}
}
