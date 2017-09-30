package pv.dotai.dotaxtractor;

import java.io.IOException;

import pv.dotai.dotaxtractor.processors.ChatProcessor;
import pv.dotai.dotaxtractor.processors.DumpProcessor;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.source.Source;

public class Dotaxtractor {

	public static void main(String[] args) {
		try {
			Source src = new MappedFileSource(args[0]);
			new SimpleRunner(src).runWith(new DumpProcessor());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
