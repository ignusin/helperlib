package ig.helperlib.entities;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@MappedSuperclass
public class IdEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false)
	private Long id;
	
	public IdEntity(Long id) {
		this.id = id;
	}
		
	public boolean hasKey() {
		return id != null;
	}
}
