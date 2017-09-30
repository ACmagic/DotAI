package pv.DotAI.Decoder;

import pv.DotAI.Decoder.Dem.EDemoCommands;

public class Atom {

	private EDemoCommands type;
	private int tick;
	private int size;
	private byte[] message;
	
	public Atom(EDemoCommands type, int tick, int size, byte[] message) {
		this.type = type;
		this.tick = tick;
		this.size = size;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "TYPE: "+type.name()+" TICKS: "+tick+" SIZE: "+size+" MESSAGE: "+new String(message);
	}
}
