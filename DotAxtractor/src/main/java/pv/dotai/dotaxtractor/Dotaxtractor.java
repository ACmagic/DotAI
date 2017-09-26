package pv.dotai.dotaxtractor;

import java.io.IOException;

import skadistats.clarity.Clarity;

public class Dotaxtractor {

	public static void main(String[] args) {
		try {
			System.out.println(Clarity.infoForFile(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
