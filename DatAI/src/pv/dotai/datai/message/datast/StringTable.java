package pv.dotai.datai.message.datast;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pv.dotai.datai.util.BitStream;

public class StringTable {
	
	public static final int KEY_HISTORY_SIZE = 32;
	
	private int index;
	private String name;
	private final Map<Integer, StringTableItem> items;
	private boolean userDataFixedSize;
	private int userDataSize;
	
	public StringTable(int index, String name, boolean userDataFixedSize, int userDataSize) {
		this.index = index;
		this.name = name;
		this.userDataFixedSize = userDataFixedSize;
		this.userDataSize = userDataSize;
		this.items = new HashMap<>();
	}
	
	public static List<StringTableItem> parseStringTable(ByteBuffer rodata, int nUpdates, boolean userDataFixedSize, int userDataSize) {
		List<StringTableItem> items = new LinkedList<>();
		int idx = -1;
		List<String> keys = new ArrayList<>(KEY_HISTORY_SIZE);
		if(rodata.remaining() == 0) {
			return items;
		}
		BitStream bs = new BitStream(rodata);
		for (int i = 0; i < nUpdates; i++) {
			String key = "";
			byte[] value = null;
			
			//first boolean tells us if it's an increment or an absolute index
			if(bs.readBits(1) == 1) {
				idx++;
			} else {
				idx = bs.getVarInt() + 1;
			}
			
			//if it has a key
			if(bs.readBits(1) == 1) {
				//if use history
				if(bs.readBits(1) == 1) {
					int pos = bs.readBits(5);
					int size = bs.readBits(5);
										
					if(pos >= keys.size()) {
						key += bs.readString();
					} else {
						String s = keys.get(pos);
						if(size > s.length()) {
							key += s + bs.readString();
						} else {
							key += s.substring(0, size) + bs.readString();
						}
					}
					
				} else {
					key = bs.readString();
				}
				
				if(keys.size() >= KEY_HISTORY_SIZE) {
					//Replace old stuff with the new
					keys = new ArrayList<>(keys.subList(1, keys.size()));
				}
				keys.add(key);
			}
			
			//if entry has value
			if(bs.readBits(1) == 1) {
				if(userDataFixedSize) {
					value = bs.readBitsAsBytes(userDataSize);
				} else {
					int size = bs.readBits(14);
					bs.readBits(3); //Unknown 3 bits
					value = new byte[size];
					bs.get(value);
				}
			}
			items.add(new StringTableItem(idx, key, value));
		}
		return items;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<Integer, StringTableItem> getItems() {
		return items;
	}

	public boolean isUserDataFixedSize() {
		return userDataFixedSize;
	}
	public void setUserDataFixedSize(boolean userDataFixedSize) {
		this.userDataFixedSize = userDataFixedSize;
	}
	public int getUserDataSize() {
		return userDataSize;
	}
	public void setUserDataSize(int userDataSize) {
		this.userDataSize = userDataSize;
	}
}
