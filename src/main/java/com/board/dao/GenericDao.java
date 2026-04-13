package com.board.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T, K> {
    void create(T entity) throws SQLException;
    T findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    void update(T entity) throws SQLException;
    void delete(K id) throws SQLException;
}