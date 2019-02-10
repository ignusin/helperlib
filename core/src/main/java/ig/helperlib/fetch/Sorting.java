package ig.helperlib.fetch;

public class Sorting {
	public static Sorting asc(String field) {
		return new Sorting(field, SortingDirection.ASC);
	}
	
	public static Sorting desc(String field) {
		return new Sorting(field, SortingDirection.DESC);
	}
	
	private final String field;
	private final SortingDirection direction;
	
	public Sorting(String field, SortingDirection direction) {
		this.field = field;
		this.direction = direction;
	}
	
	public String getField() {
		return field;
	}
	
	public SortingDirection getDirection() {
		return direction;
	}
}
