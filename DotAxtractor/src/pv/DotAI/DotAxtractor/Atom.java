package pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFullPacket;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoPacket;
import pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;

public abstract class Atom {

	private EDemoCommands type;
	private int tick;
	private int size;
	private AbstractMessage message;

	public Atom(EDemoCommands type, int tick, int size, AbstractMessage message) {
		this.type = type;
		this.tick = tick;
		this.size = size;
		this.message = message;
	}

	@Override
	public String toString() {
		String base =  "TYPE: " + type.name() + " TICKS: " + tick + " SIZE: " + size + " MESSAGE: ";
		if(!(message instanceof CDemoPacket) && !(message instanceof CDemoFullPacket)) {
			base += message == null ? "null" : message;
		}
		return base;
	}
}
