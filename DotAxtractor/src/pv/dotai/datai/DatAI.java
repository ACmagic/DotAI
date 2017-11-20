package pv.dotai.datai;

public class DatAI {
	
	public static void main(String[] args) {	
		try {
			new ReplayReader().readFile(args[0]);
		} catch (ReplayException e) {
			e.printStackTrace();
		}
	}
}
