package pv.DotAI.DotAxtractor;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.ProtocolMessageEnum;

public class EmbedData {

	private ProtocolMessageEnum type;
	private boolean netMessage;
	private AbstractMessage message;
	
	public EmbedData(ProtocolMessageEnum type, boolean netMessage, AbstractMessage am) {
		this.type = type;
		this.netMessage = netMessage;
		this.message = am;
	}
	
	@Override
	public String toString() {
		return type.toString()+" => "+(message != null ? message.toString() : "null");
	}
	
	
}
