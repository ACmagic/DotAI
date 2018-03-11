package pv.dotai.datai.message.datast.huffman;

/**
 * A node (has children) from a huffman tree
 * @author Thomas Ibanez
 * @since  1.0
 */
public class HuffmanNode implements HuffmanTree {

	private int weight;
	private int value;
	private HuffmanTree left, right;
	
	public HuffmanNode(int weight, int value, HuffmanTree left, HuffmanTree right) {
		super();
		this.weight = weight;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	@Override
	public int weight() {
		return weight;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public int value() {
		return value;
	}

	@Override
	public HuffmanTree left() {
		return left;
	}

	@Override
	public HuffmanTree right() {
		return right;
	}

}
