package pv.dotai.datai.message;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.protobuf.Demo.CDemoClassInfo;

public class ClassInfoHandler implements MessageHandler<CDemoClassInfo> {

	@Override
	public void handle(CDemoClassInfo m) {
		for(CDemoClassInfo.class_t c : m.getClassesList()) {
			ReplayBuilder.getInstance().getClassInfo().put(c.getClassId(), c.getNetworkName());
			//TODO check if serializer exists
		}
		ReplayBuilder.getInstance().setClassInfoComplete(true);
		ReplayBuilder.getInstance().updateInstanceBaseline();
	}

}
