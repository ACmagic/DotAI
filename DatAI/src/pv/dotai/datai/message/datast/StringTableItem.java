package pv.dotai.datai.message.datast;

/**
 * Internal representation of a String table's item
 * @author Thomas Ibanez
 * @since  1.0
 */
public class StringTableItem {
	private int index;
	private String key;
	private byte[] value;

	public StringTableItem(int index, String key, byte[] value) {
		this.index = index;
		this.key = key;
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}
