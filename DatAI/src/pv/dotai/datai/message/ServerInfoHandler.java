package pv.dotai.datai.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_ServerInfo;

/**
 * Handler for CSVCMsg_ServerInfo messages (svc_ServerInfo)
 * @author Thomas Ibanez
 * @since  1.0
 */
public class ServerInfoHandler implements MessageHandler<CSVCMsg_ServerInfo> {

	@Override
	public void handle(CSVCMsg_ServerInfo m) {
		ReplayBuilder.getInstance().CLASSID_SIZE = (int) Math.floor((Math.log(m.getMaxClasses()) /  Math.log(2))) + 1;
		Matcher matcher = Pattern.compile("/dota_v(\\d+)/").matcher(m.getGameDir());
		System.out.println(m.getGameDir());
		if(matcher.find()) {
			ReplayBuilder.getInstance().GAME_BUILD = Integer.parseInt(matcher.group(1));
		}
	}

}
