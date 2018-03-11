package pv.dotai.datai.message.datast.huffman;

/**
 * Abstract huffman tree representation
 * @author Thomas Ibanez
 * @since  1.0
 */
public interface HuffmanTree {
	int weight();
	boolean isLeaf();
	int value();
	HuffmanTree left();
	HuffmanTree right();
}
