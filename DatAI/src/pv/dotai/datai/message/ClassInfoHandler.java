package pv.dotai.datai.message;

import pv.dotai.datai.ReplayBuilder;
import pv.dotai.datai.protobuf.Demo.CDemoClassInfo;

public class ClassInfoHandler implements MessageHandler<CDemoClassInfo> {

	@Override
	public void handle(CDemoClassInfo m) {
		for(CDemoClassInfo.class_t c : m.getClassesList()) {
			ReplayBuilder.getInstance().getClassInfo().put(c.getClassId(), c.getNetworkName());
			if(ReplayBuilder.getInstance().getSerializers().get(c.getNetworkName()) == null) {
				System.err.println("Unable to find table for class "+c.getNetworkName()+" ("+c.getClassId()+")");
			}
		}
		ReplayBuilder.getInstance().setClassInfoComplete(true);
		ReplayBuilder.getInstance().updateInstanceBaseline();
		
	}

}
