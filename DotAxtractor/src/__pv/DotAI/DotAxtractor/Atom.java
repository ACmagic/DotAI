package __pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;

import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFullPacket;
import __pv.DotAI.DotAxtractor.protobuf.Demo.CDemoPacket;
import __pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;

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

	public abstract void parseAtom(ReplayBuilder b);
	
	public EDemoCommands getType() {
		return type;
	}

	public int getTick() {
		return tick;
	}

	public int getSize() {
		return size;
	}

	public AbstractMessage getMessage() {
		return message;
	}
}
