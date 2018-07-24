package com.zmdev.goldenbag.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface BaseService<T, ID> {
    void save(T model); // 持久化

    void save(List<T> models); // 批量持久化

    void deleteById(ID id); // 通过主鍵刪除

    void deleteByIds(String ids); // 批量刪除 eg：ids -> “1,2,3,4”

    Optional<T> findById(ID id); // 通过ID查找

    List<T> findByIds(String ids); // 通过多个ID查找//eg：ids -> “1,2,3,4”

    List<T> findAll(); // 获取所有

    Page<T> findAllByPage(Pageable pageable); // 分页获取所有
}
