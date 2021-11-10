package ir.jiring.accounting.service;

import ir.jiring.accounting.dao.IGeneralRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class GeneralService<T> implements IGeneralService<T> {
    protected abstract IGeneralRepository<T> getGeneralRepository();

    @Override
    public void flushSession() {
        getGeneralRepository().flushSession();
    }

    @Override
    public void clearSession() {
        getGeneralRepository().clearSession();
    }

    @Override
    @Transactional
    public void add(T entity) {
        getGeneralRepository().add(entity);
    }

    @Override
    public List<T> getAll() {
        return getGeneralRepository().getAll();
    }

    @Override
    @Transactional
    public boolean deleteById(Long entityId) {
        getGeneralRepository().deleteById(entityId);
        return true;
    }

    @Override
    public T loadById(Long entityId) {
        return getGeneralRepository().loadById(entityId);
    }
}
