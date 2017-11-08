package pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;

import pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;

public class DoubleAtom extends Atom {

	private Atom embed;

	public DoubleAtom(EDemoCommands type, int tick, int size, AbstractMessage message, Atom embed) {
		super(type, tick, size, message);
		this.embed = embed;
	}
	
	@Override
	public String toString() {
		return super.toString()+" EMBEDDED ATOM: "+(embed != null ? embed.toString() : "null");
	}

	public Atom getEmbed() {
		return embed;
	}

	@Override
	public void parseAtom(ReplayBuilder b) {
		this.embed.parseAtom(b);
	}
}
