package pv.dotai.datai;

import pv.dotai.datai.example.ReplayRunner;

public class DatAI {
	
	public static void main(String[] args) {	
		try {
			ReplayRunner frame = new ReplayRunner();
			frame.setVisible(true);
			ReplayBuilder.getInstance().registerListener(frame);
			new ReplayReader().readFile(args[0]);
		} catch (ReplayException e) {
			e.printStackTrace();
		}
	}
}
