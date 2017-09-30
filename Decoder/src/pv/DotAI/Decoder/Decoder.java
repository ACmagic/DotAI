package pv.DotAI.Decoder;

public class Decoder {
	
	public static void main(String[] args) {
		try {
			new ReplayReader().readFile(args[0]);
		} catch (ReplayException e) {
			e.printStackTrace();
		}
	}
}
