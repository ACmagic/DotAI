package pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.DotAxtractor.Dem.EDemoCommands;

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
		return "TYPE: " + type.name() + " TICKS: " + tick + " SIZE: " + size + " MESSAGE: " + (message == null ? "null" : message.toString());
	}
}
