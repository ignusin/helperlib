package ig.helperlib.fetch;

public class Paging {
	private final int startIndex;
	private final int size;
	
	public Paging(int startIndex, int size) {
		this.startIndex = startIndex;
		this.size = size;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getSize() {
		return size;
	}

	public static Paging of(int startIndex, int size) {
		return new Paging(startIndex, size);
	}
}
