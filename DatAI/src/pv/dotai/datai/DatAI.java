package pv.dotai.datai;

import pv.dotai.datai.example.ReplayRunner;

/**
 * Main class for the analyzer's example
 * @author Thomas Ibanez
 * @since  1.0
 */
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
