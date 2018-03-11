package pv.dotai.datai.message.datast.huffman;

import java.util.Comparator;
import java.util.PriorityQueue;

import pv.dotai.datai.message.datast.FieldPathOP;

/**
 * Utility class for Huffman tree building
 * @author Thomas Ibanez
 * @since  1.0
 */
public class HuffmanUtil {

	private HuffmanUtil() {}
	
	public static HuffmanTree buildFieldPathHuffman() {
		int[] huffmanlist = new int[FieldPathOP.values().length];
		for (int i = 0; i < FieldPathOP.values().length; i++) {
			huffmanlist[i] = FieldPathOP.values()[i].getWeight();
		}
		return buildHuffmanTree(huffmanlist);
	}
	
	public static HuffmanTree buildHuffmanTree(int[] symFreq) {
		//A priority queue is a binary heap
		PriorityQueue<HuffmanTree> heap = new PriorityQueue<>(new Comparator<HuffmanTree>() {
			public int compare(HuffmanTree a, HuffmanTree b) {
				if(a.weight() == b.weight()) {
					return a.value() >= b.value() ? -1 : 1;
				}
				return a.weight() < b.weight() ? -1 : 1;
			}
		});
		int n = 0;
		for(int i = 0; i < symFreq.length; i++) {
			int j = symFreq[i];
			if(j == 0) {
				j = 1;
			}
			heap.offer(new HuffmanLeaf(j, i));
			n++;
		}
		
		while (heap.size() > 1) {
			HuffmanTree a = heap.poll();
			HuffmanTree b = heap.poll();
			
			heap.offer(new HuffmanNode(a.weight() + b.weight(), n, a, b));
			n++;
		}
		return heap.peek();
	}
}
