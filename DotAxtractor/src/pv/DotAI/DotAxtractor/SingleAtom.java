package pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;

public class SingleAtom extends Atom {

	public SingleAtom(EDemoCommands type, int tick, int size, AbstractMessage message) {
		super(type, tick, size, message);
	}

}
