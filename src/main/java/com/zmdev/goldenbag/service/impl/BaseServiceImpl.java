package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID, REPOSITORY extends JpaRepository<T, ID>> implements BaseService<T, ID> {

    @Autowired
    protected REPOSITORY repository;

    public void save(T model) {
        repository.save(model);
    }

    public void save(List<T> models) {
        repository.saveAll(models);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public void deleteByIds(String ids) {
        //todo 实现该方法
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public List<T> findByIds(String ids) {
        //todo 实现该方法
        return null;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAllByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
