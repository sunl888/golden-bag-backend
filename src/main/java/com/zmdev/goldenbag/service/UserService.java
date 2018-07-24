package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface UserService {
    Page<User> findAllByPage(Pageable pageable);
    Optional<User> findById(Long id);
    void save(User user);
    void deleteById(Long id);
    List<User> findAll();
}
