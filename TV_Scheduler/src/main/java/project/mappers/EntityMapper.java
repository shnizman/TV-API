package project.mappers;

public interface EntityMapper<S, T> {

    S mapToEntity(T object);

    T mapToModel(S object);
}
