package hr.luka.b2match.service.mapper;

public interface AbstractDTOMapper<E,D> {

    E fromDto(D dto);

    D fromEntity(E entity);

}
