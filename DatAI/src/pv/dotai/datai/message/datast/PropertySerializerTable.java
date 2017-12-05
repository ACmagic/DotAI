package pv.dotai.datai.message.datast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pv.dotai.datai.message.datast.decoder.DecodeFunc;
import pv.dotai.datai.message.datast.decoder.Decoders;

public class PropertySerializerTable {

	private Map<String, PropertySerializer> propertySerializers;
	private static Pattern arrPat = Pattern.compile("([^\\[\\]]+)\\[(\\d+)\\]");
	private static Pattern vecPat = Pattern.compile("CUtlVector\\<\\s(.*)\\s>$");

	public PropertySerializerTable() {
		this.propertySerializers = new HashMap<>();
	}

	public void FillSerializer(DataTableField dtf) {
		switch (dtf.getName()) {
			case "m_flSimulationTime":
				dtf.setSerializer(new PropertySerializer(Decoders::decodeSimTime, null, false, 0, null, "unknown"));
				return;
			case "m_flAnimTime":
				dtf.setSerializer(new PropertySerializer(Decoders::decodeSimTime, null, false, 0, null, "unknown"));
				return;
		}

		if (dtf.getBuild() < 955) {
			switch (dtf.getName()) {
				case "m_flMana":
				case "m_flMaxMana":
					dtf.setHighValue(8192.0f);
					dtf.setLowValue(Float.NaN);
					break;
			}
		}
		dtf.setSerializer(this.getPropertySerializerByName(dtf.getType()));
	}

	private PropertySerializer getPropertySerializerByName(String name) {
		if (this.propertySerializers.get(name) != null) {
			return this.propertySerializers.get(name);
		}

		DecodeFunc decoder = null, decoderContainer = null;
		switch (name) {
			case "float32":
				decoder = Decoders::decodeFloat;
				break;
			case "int8":
			case "int16":
			case "int32":
			case "int64":
				decoder = Decoders::decodeSigned;
				break;
			case "uint8":
			case "uint16":
			case "uint32":
			case "uint64":
			case "Color":
				decoder = Decoders::decodeUnsigned;
				break;
			case "char":
			case "CUtlString":
			case "CUtlSymbolLarge":
				decoder = Decoders::decodeString;
				break;
			case "Vector":
				decoder = Decoders::decodeFVector;
				break;
			case "bool":
				decoder = Decoders::decodeBoolean;
				break;
			case "CNetworkedQuantizedFloat":
				decoder = Decoders::decodeQuantized;
				break;
			case "CRenderComponent":
			case "CPhysicsComponent":
			case "CBodyComponent":
				decoder = Decoders::decodeComponent;
				break;
			case "QAngle":
				decoder = Decoders::decodeQAngle;
				break;
			case "CGameSceneNodeHandle":
				decoder = Decoders::decodeHandle;
				break;
			case "Vector2D":
				decoder = Decoders::decodeVector2D;
				break;
			default:
				// check for specific types
				if (name.startsWith("CHandle"))
					decoder = Decoders::decodeHandle;
				else if (name.startsWith("CStrongHandle"))
					decoder = Decoders::decodeUnsigned;
				else if (name.startsWith("CUtlVector< ")) {
					Matcher m = vecPat.matcher(name);
					if (m.find()) {
						decoderContainer = Decoders::decodeVector;
						System.out.println(m.group(1));
						decoder = this.getPropertySerializerByName(m.group(1)).getDecode();
					} else {
						System.err.println("Unable to read vector type for " + name);
					}
				} else {
					System.err.println("no decoder for type " + name);
				}
				break;
		}
		
		if(name.endsWith("*")) {
			decoder = Decoders::decodeBoolean;
		}
		
		Matcher m = arrPat.matcher(name);
		if(m.find()) {
			String typeName = m.group(1);
			int size = Integer.parseInt(m.group(2));
			
			PropertySerializer serializer = this.propertySerializers.get(typeName);
			if(this.propertySerializers.get(typeName) == null) {
				serializer = getPropertySerializerByName(typeName);
				this.propertySerializers.put(typeName, serializer);
			}
			
			PropertySerializer ps = new PropertySerializer(serializer.getDecode(), decoderContainer, true, size, serializer, typeName);
			this.propertySerializers.put(name, ps);
			return ps;
		}
		
		m = vecPat.matcher(name);
		if(m.find()) {
			PropertySerializer ps = new PropertySerializer(decoder, decoderContainer, true, 1024, null, name);
			this.propertySerializers.put(name, ps);
			return ps;
		}
		
		if(name.equals("C_DOTA_ItemStockInfo[MAX_ITEM_STOCKS]")) {
			String typeName = "C_DOTA_ItemStockInfo";
			PropertySerializer serializer = this.propertySerializers.get(typeName);
			if(serializer == null) {
				serializer = getPropertySerializerByName(typeName);
				this.propertySerializers.put(typeName, serializer);
			}
			
			PropertySerializer ps = new PropertySerializer(serializer.getDecode(), decoderContainer, true, 8, serializer, typeName);
			this.propertySerializers.put(name, ps);
			return ps;
		}
		
		if(name.equals("CDOTA_AbilityDraftAbilityState[MAX_ABILITY_DRAFT_ABILITIES]")) {
			String typeName = "CDOTA_AbilityDraftAbilityState";
			
			PropertySerializer serializer = this.propertySerializers.get(typeName);
			if(serializer == null) {
				serializer = getPropertySerializerByName(typeName);
				this.propertySerializers.put(typeName, serializer);
			}
			
			PropertySerializer ps = new PropertySerializer(serializer.getDecode(), decoderContainer, true, 48, serializer, typeName);
			this.propertySerializers.put(name, ps);
			return ps;
		}
		
		if(name.equals("m_SpeechBubbles")) {
			PropertySerializer ps = new PropertySerializer(decoder, decoderContainer, true, 5, null, name);
			this.propertySerializers.put(name, ps);
			return ps;
		}
		
		if(name.equals("DOTA_PlayerChallengeInfo") || name.equals("DOTA_CombatLogQueryProgress")) {
			PropertySerializer ps = new PropertySerializer(decoder, decoderContainer, true, 1024, null, name);
			this.propertySerializers.put(name, ps);
			return ps;
		}
		
		return new PropertySerializer(decoder, decoderContainer, false, 0, null, name);
	}
}
