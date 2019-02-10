package ig.helperlib.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ig.helperlib.fetch.Sorting;
import ig.helperlib.fetch.SortingDirection;

@Getter @Setter
@NoArgsConstructor
public class SortingDTO {
	public static final int ASC = 0;
	public static final int DESC = 1;
	
	private String sortProperty;
	private int sortDirection;

	public Sorting toSorting() {
		if ((sortDirection == 0 || sortDirection == 1) && sortProperty != null) {
			return new Sorting(sortProperty, sortDirection == 0 ? SortingDirection.ASC : SortingDirection.DESC);
		}
		
		return null;
	}
}
