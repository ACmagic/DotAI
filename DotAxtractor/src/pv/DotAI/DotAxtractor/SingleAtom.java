package pv.DotAI.DotAxtractor;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Descriptors.FieldDescriptor;

import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoFileHeader;
import pv.DotAI.DotAxtractor.protobuf.Demo.CDemoSendTables;
import pv.DotAI.DotAxtractor.protobuf.Demo.EDemoCommands;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.CSVCMsg_FlattenedSerializer;
import pv.DotAI.DotAxtractor.protobuf.Netmessages.ProtoFlattenedSerializer_t;

public class SingleAtom extends Atom {

	public SingleAtom(EDemoCommands type, int tick, int size, AbstractMessage message) {
		super(type, tick, size, message);
	}

	@Override
	public void parseAtom(ReplayBuilder b) {
		switch (this.getType()) {
			case DEM_ClassInfo:
				break;
			case DEM_ConsoleCmd:
				break;
			case DEM_CustomData:
				break;
			case DEM_CustomDataCallbacks:
				break;
			case DEM_Error:
				break;
			case DEM_FileHeader:
				b.setHeader(((CDemoFileHeader)this.getMessage()));
				break;
			case DEM_FileInfo:
				break;
			case DEM_Max:
				break;
			case DEM_SendTables:
				//decodeSendTable((CDemoSendTables)this.getMessage());
				break;
			case DEM_SpawnGroups:
				break;
			case DEM_Stop:
				break;
			case DEM_StringTables:
				break;
			case DEM_SyncTick:
				break;
			case DEM_UserCmd:
				break;
			default:
				break;
			
		}
	}
	
	private void decodeSendTable(CDemoSendTables tables) {
		BitStream bs = new BitStream(ByteBuffer.wrap(tables.getData().toByteArray()));
		int size = Decoder.getVarInt(bs);
		byte[] data = new byte[size];
		bs.get(data);
		Map<String, ProtoFlattenedSerializer_t> fs = new HashMap<>();
		try {
			CSVCMsg_FlattenedSerializer msg = CSVCMsg_FlattenedSerializer.parseFrom(data);
			Map<FieldDescriptor, Object> fields = msg.getAllFields();
			for (ProtoFlattenedSerializer_t s : msg.getSerializersList()) {
				String name = msg.getSymbols(s.getSerializerNameSym());
				int version = s.getSerializerVersion();
				
				if(fs.get(name) == null) {
					//TODO
				}
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

}
