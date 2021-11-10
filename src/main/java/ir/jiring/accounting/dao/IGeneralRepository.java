package ir.jiring.accounting.dao;

import java.util.List;

public interface IGeneralRepository<T> {
    void add(T entity);

    void flushSession();

    void clearSession();

    List<T> getAll();

    void deleteById(Long entityId);

    T loadById(Long entityId);
}
