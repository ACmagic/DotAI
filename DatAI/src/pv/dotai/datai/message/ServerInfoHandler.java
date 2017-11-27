package pv.dotai.datai.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.protobuf.Netmessages.CSVCMsg_ServerInfo;

public class ServerInfoHandler implements MessageHandler<CSVCMsg_ServerInfo> {

	@Override
	public void handle(CSVCMsg_ServerInfo m) {
		ReplayBuilder.getInstance().CLASSID_SIZE = (int) Math.round((Math.log(m.getMaxClasses() / Math.log(2)) + 1));
		Matcher matcher = Pattern.compile("/dota_v(\\d+)/").matcher(m.getGameDir());
		System.out.println(m.getGameDir());
		while(matcher.find()) {
			System.out.println(matcher.group());
		}
	}

}
