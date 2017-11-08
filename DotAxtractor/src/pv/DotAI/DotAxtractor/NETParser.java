package pv.DotAI.DotAxtractor;

import pv.DotAI.DotAxtractor.protobuf.Networkbasetypes.NET_Messages;

public class NETParser {

	public static void parseNET(EmbedData data) {
		switch((NET_Messages)data.getType()) {
			case net_Disconnect:
				break;
			case net_NOP:
				break;
			case net_SetConVar:
				break;
			case net_SignonState:
				break;
			case net_SpawnGroup_Load:
				break;
			case net_SpawnGroup_LoadCompleted:
				break;
			case net_SpawnGroup_ManifestUpdate:
				break;
			case net_SpawnGroup_SetCreationTick:
				break;
			case net_SpawnGroup_Unload:
				break;
			case net_SplitScreenUser:
				break;
			case net_StringCmd:
				break;
			case net_Tick:
				break;
			default:
				break;
			
		}
	}
	
}
