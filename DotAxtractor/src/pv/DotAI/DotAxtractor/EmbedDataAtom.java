package pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;

public class EmbedDataAtom extends Atom {

	private EmbedData[] data;

	public EmbedDataAtom(EDemoCommands type, int tick, int size, AbstractMessage message, EmbedData[] data) {
		super(type, tick, size, message);
		this.data = data;
	}

	@Override
	public String toString() {
		String embedded = "";
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				embedded += data[i].toString() + " ";
			}
		} else {
			embedded = "null";
		}
		return super.toString() + " EMBEDDED DATA: " + embedded;
	}
}
