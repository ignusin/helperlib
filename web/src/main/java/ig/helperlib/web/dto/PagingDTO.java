package ig.helperlib.web.dto;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ig.helperlib.fetch.Paging;

@Getter @Setter
@NoArgsConstructor
public class PagingDTO {
	@Min(0)
	private int pageStart;
	
	@Min(0)
	private int pageSize;
	
	public Paging toPaging() {
		if (pageStart > 0 || pageSize > 0) {
			return new Paging(pageStart, pageSize);
		}
		
		return null;
	}
}
