package pv.dotai.datai.message.datast.huffman;

import java.util.Stack;

public class TreeHeap {

	private Stack<HuffmanTree> heap;
	
	public int size() {
		return heap.size();
	}
	
	public boolean less(int i, int j) {
		if(heap.get(i).weight() == heap.get(j).weight()) {
			return heap.get(i).value() >= heap.get(j).value();
		}
		return heap.get(i).weight() < heap.get(j).weight();
	}
	
	public void push(HuffmanTree element) {
		this.heap.push(element);
	}
	
	public HuffmanTree pop() {
		return this.heap.pop();
	}
	
	public void swap(int i, int j) {
		HuffmanTree tmp = this.heap.get(i);
		this.heap.set(i, this.heap.get(j));
		this.heap.set(j, tmp);
	}
}
