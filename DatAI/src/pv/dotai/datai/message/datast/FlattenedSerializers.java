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
			DataTableProperty prop = new DataTableProperty(null, null);
			prop.setField(new DataTableField(this.proto.getSymbols(field.getVarNameSym()), this.proto.getSymbols(field.getVarTypeSym()), -1, field.getEncodeFlags(), field.getBitCount(), field.getLowValue(), field.getHighValue(), field.getFieldSerializerVersion(), null, build));
			this.propertySerializerTable.FillSerializer(prop.getField());
			if (field.hasVarEncoderSym()) {
				prop.getField().setEncoder(this.proto.getSymbols(field.getVarEncoderSym()));
			} else {
				if (this.build <= 990) {
					switch (prop.getField().getName()) {
						case "angExtraLocalAngles":
						case "angLocalAngles":
						case "m_angInitialAngles":
						case "m_angRotation":
						case "m_ragAngles":
						case "m_vLightDirection":
							if (table.getName().equals("CBodyComponentBaseAnimatingOverlay")) {
								prop.getField().setEncoder("qangle_pitch_yaw");
							} else {
								prop.getField().setEncoder("QAngle");
							}
							break;
						case "dirPrimary":
						case "localSound":
						case "m_flElasticity":
						case "m_location":
						case "m_poolOrigin":
						case "m_ragPos":
						case "m_vecEndPos":
						case "m_vecLadderDir":
						case "m_vecPlayerMountPositionBottom":
						case "m_vecPlayerMountPositionTop":
						case "m_viewtarget":
						case "m_WorldMaxs":
						case "m_WorldMins":
						case "origin":
						case "vecLocalOrigin":
							prop.getField().setEncoder("coord");
							break;
						case "m_vecLadderNormal":
							prop.getField().setEncoder("normal");
							break;
					}
				} else if (this.build >= 1016 && this.build <= 1027) {
					switch (prop.getField().getName()) {
						case "m_bItemWhiteList":
						case "m_bWorldTreeState":
						case "m_iPlayerIDsInControl":
						case "m_iPlayerSteamID":
						case "m_ulTeamBannerLogo":
						case "m_ulTeamBaseLogo":
						case "m_ulTeamLogo":
							prop.getField().setEncoder("fixed64");
							break;
					}
				}
			}
			
			if(field.hasFieldSerializerNameSym()) {
				String fieldName = this.proto.getSymbols(field.getFieldSerializerNameSym());
				int fieldVersion = field.getFieldSerializerVersion();
				DataTable dt = this.getSerializers().get(fieldName).get(fieldVersion);
				
				if(dt == null) {
					System.err.println("No serializer for "+fieldName+" v"+fieldVersion);
				}
				
				prop.setDataTable(dt);
			}
			
			if(prop.getField().getSerializer().isArray()) {
				DataTable tmp = new DataTable(prop.getField().getName(), 0);
				for(int j = 0; j < prop.getField().getSerializer().getLength(); j++) {
					String name = String.format("%04d", j);
					DataTableField dtf = new DataTableField(name, prop.getField().getSerializer().getName(), j, prop.getField().getFlags(), prop.getField().getBitCount(), prop.getField().getLowValue(), prop.getField().getHighValue(), prop.getField().getVersion(), prop.getField().getSerializer().getArraySerializer(), this.build);
					dtf.setEncoder(prop.getField().getEncoder());
					tmp.getProperties().add(new DataTableProperty(dtf, table));
					
					if(prop.getDataTable() != null) {
						DataTable nTable = new DataTable(prop.getDataTable());
						nTable.setName(String.format("%04d", j));
						tmp.getProperties().get(tmp.getProperties().size() - 1).setDataTable(nTable);
					}
				}
				
				prop.setDataTable(tmp);
			}
			
			table.getProperties().add(prop);
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
