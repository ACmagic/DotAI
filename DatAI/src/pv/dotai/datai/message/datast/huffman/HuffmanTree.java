package pv.dotai.datai.message.datast.huffman;

public interface HuffmanTree {
	int weight();
	boolean isLeaf();
	int value();
	HuffmanTree left();
	HuffmanTree right();
}
