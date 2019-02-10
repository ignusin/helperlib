package ig.helperlib.web.crud;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import ig.helperlib.crud.CrudService;
import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.Fetch;
import ig.helperlib.web.dto.PagingDTO;
import ig.helperlib.web.dto.SortingDTO;

public abstract class CrudController<TRequestDTO extends RequestDTO, TResponseDTO, TEntity extends IdEntity> {
	private final IdMapper<TRequestDTO, TResponseDTO, TEntity> mapper;
	private final CrudService<TEntity> crudService;
	
	protected CrudController(IdMapper<TRequestDTO, TResponseDTO, TEntity> mapper, CrudService<TEntity> crudService) {
		this.mapper = mapper;
		this.crudService = crudService;
	}

	protected Mapper<TRequestDTO, TResponseDTO, TEntity> getMapper() {
		return mapper;
	}

	protected CrudService<TEntity> getCrudService() {
		return crudService;
	}
	
	@GetMapping
	public @ResponseBody List<TResponseDTO> getAll(String filter,
		@Valid SortingDTO sorting, @Valid PagingDTO paging) {
		
		return crudService
			.findAll(Fetch.of(filter).sort(sorting.toSorting()).page(paging.toPaging()))
			.stream()
			.map(x -> mapper.toDTO(x))
			.collect(Collectors.toList());
	}
	
	@GetMapping("/count")
	public @ResponseBody long countAll(String filter) {
		return crudService.count(filter);
	}

	@GetMapping("/{id}")
	public @ResponseBody TResponseDTO getOne(@PathVariable long id) {
		final TEntity entity = crudService.findById(id);
		return entity == null ? null : mapper.toDTO(entity);
	}

	@PostMapping
	public @ResponseBody TResponseDTO createOrUpdate(@Valid @RequestBody TRequestDTO dto) {
		TEntity entity = crudService.save(mapper.toEntity(dto));
		return mapper.toDTO(entity);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void remove(@PathVariable long id) {
		crudService.removeById(id);
	}
}
