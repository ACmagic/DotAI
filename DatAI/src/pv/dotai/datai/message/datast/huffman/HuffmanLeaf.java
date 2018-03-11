package pv.dotai.datai.message.datast.huffman;

/**
 * A leaf from a huffman tree
 * @author Thomas Ibanez
 * @since  1.0
 */
public class HuffmanLeaf implements HuffmanTree {

	private int weight;
	private int value;
	
	public HuffmanLeaf(int weight, int value) {
		super();
		this.weight = weight;
		this.value = value;
	}

	@Override
	public int weight() {
		return weight;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public int value() {
		return value;
	}

	@Override
	public HuffmanTree left() {
		throw new RuntimeException("Leaf doesn't have a left");
	}

	@Override
	public HuffmanTree right() {
		throw new RuntimeException("Leaf doesn't have a right");
	}
}
