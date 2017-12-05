package pv.dotai.datai.message.datast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_FlattenedSerializer;
import pv.dotai.datai.protobuf.Netmessages.ProtoFlattenedSerializerField_t;
import pv.dotai.datai.protobuf.Netmessages.ProtoFlattenedSerializer_t;

public class FlattenedSerializers {
	private Map<String, Map<Integer, DataTable>> serializers;
	private CSVCMsg_FlattenedSerializer proto;
	private PropertySerializerTable propertySerializerTable;
	private int build;
	
	
	public FlattenedSerializers(CSVCMsg_FlattenedSerializer proto, PropertySerializerTable propertySerializerTable, int build) {
		this.serializers = new HashMap<>();
		this.proto = proto;
		this.propertySerializerTable = propertySerializerTable;
		this.build = build;
	}

	public DataTable recurseTable(ProtoFlattenedSerializer_t s) {
		DataTable table = new DataTable(this.proto.getSymbols(s.getSerializerNameSym()), s.getSerializerVersion());
		List<ProtoFlattenedSerializerField_t> props = this.proto.getFieldsList();
		for (Integer i : s.getFieldsIndexList()) {
			ProtoFlattenedSerializerField_t field = props.get(i);
			DataTableProperty prop = new DataTableProperty();
			prop.setField(new DataTableField(this.proto.getSymbols(field.getVarNameSym()), this.proto.getSymbols(field.getVarTypeSym()), -1, field.getEncodeFlags(), field.getBitCount(), field.getLowValue(), field.getHighValue(), field.getFieldSerializerVersion(), null, build));
			this.propertySerializerTable.FillSerializer(prop.getField());
			
		}
		return table;
	}

	public Map<String, Map<Integer, DataTable>> getSerializers() {
		return serializers;
	}


	public CSVCMsg_FlattenedSerializer getProto() {
		return proto;
	}


	public PropertySerializerTable getPropertySerializerTable() {
		return propertySerializerTable;
	}


	public int getBuild() {
		return build;
	}
}
