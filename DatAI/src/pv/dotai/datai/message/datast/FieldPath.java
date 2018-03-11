package pv.dotai.datai.message.datast;

import java.util.ArrayList;

import pv.dotai.datai.ReplayException;
import pv.dotai.datai.message.datast.huffman.HuffmanTree;
import pv.dotai.datai.message.datast.huffman.HuffmanUtil;
import pv.dotai.datai.util.BitStream;

/**
 * Field path to walk through in order to get which field to decode when decoding properties
 * @author Thomas Ibanez
 * @since  1.0
 */
public class FieldPath {

	private DataTable parent;
	public final ArrayList<FieldPathField> fields;
	public final ArrayList<Integer> path;
	public boolean finished;
	private static HuffmanTree fpHuf = HuffmanUtil.buildFieldPathHuffman();
	
	public FieldPath(DataTable parent) {
		this.parent = parent;
		this.fields = new ArrayList<>();
		this.path = new ArrayList<>();
		this.path.add(-1);
		this.finished = false;
	}
	
	public void walk(BitStream bs) {
		HuffmanTree node = fpHuf;
		HuffmanTree next = null;
		
		while(!this.finished) {
			if(bs.nextBit() == 1) {
				next = node.right();
			} else {
				next = node.left();
			}
			
			if(next.isLeaf()) {
				node = fpHuf;
				FieldPathOP.values()[next.value()].execute(this, bs);
				if(!this.finished) {
					this.addField();
				}
			} else {
				node = next;
			}
		}
	}
	
	private void addField() {
		DataTable cDT = this.parent;
		String name = "";
		int i = 0;
		for(i = 0; i < path.size() - 1; i++) {
			if(cDT.getProperties().get(this.path.get(i)).getDataTable() != null) {
				cDT = cDT.getProperties().get(this.path.get(i)).getDataTable();
				name += cDT.getName() + ".";
			} else {
				throw new ReplayException("Expected table for type "+ cDT.getName() +" fp properties: "+ cDT.getProperties().get(this.path.get(i)).getField().getName() +", "+cDT.getProperties().get(this.path.get(i)).getField().getType());
			}
		}
		this.fields.add(new FieldPathField(name + cDT.getProperties().get(this.path.get(i)).getField().getName(), cDT.getProperties().get(this.path.get(i)).getField()));
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public DataTable getParent() {
		return parent;
	}

	public ArrayList<FieldPathField> getFields() {
		return fields;
	}

	public ArrayList<Integer> getPath() {
		return path;
	}
	
	
}