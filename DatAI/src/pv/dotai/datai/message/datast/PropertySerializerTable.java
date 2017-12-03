package pv.dotai.datai.message.datast;

import java.util.HashMap;
import java.util.Map;

public class PropertySerializerTable {
	private Map<String, PropertySerializer> propertySerializers;
	
	public PropertySerializerTable() {
		this.propertySerializers = new HashMap<>();
	}
	
	public void FillSerializer(DataTableField dtf) {
		switch(dtf.getName()) {
			case "m_flSimulationTime":
				return;
			case "m_flAnimTime":
				return;
		}
	}
}
