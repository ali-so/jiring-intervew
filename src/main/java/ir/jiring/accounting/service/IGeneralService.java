package ir.jiring.accounting.service;

import java.util.List;

public interface IGeneralService<T> {
    void flushSession();

    void clearSession();

    void add(T entity);

    List<T> getAll();

    boolean deleteById(Long entityId);

    T loadById(Long entityId);
}
