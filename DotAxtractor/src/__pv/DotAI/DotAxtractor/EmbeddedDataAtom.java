package __pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;

import __pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_PacketEntities;
import __pv.DotAI.DotAxtractor.protobuf.Netmessages.SVC_Messages;
import __pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.NET_Messages;

public class EmbeddedDataAtom extends Atom {

	private EmbedData[] data;

	public EmbeddedDataAtom(EDemoCommands type, int tick, int size, AbstractMessage message, EmbedData[] data) {
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

	public EmbedData[] getData() {
		return data;
	}

	@Override
	public void parseAtom(ReplayBuilder b) {
		switch (this.getType()) {
			case DEM_SignonPacket:
			case DEM_Packet:
				for (EmbedData embedData : data) {
					this.parseEmbeddedData(embedData);
				}
				break;
			default:
				break;

		}
	}

	private void parseEmbeddedData(EmbedData data) {
		if (data.isNetMessage()) {
			NETParser.parseNET(data);
		} else {
			SVCParser.parseSVC(data);
		}
	}
}
