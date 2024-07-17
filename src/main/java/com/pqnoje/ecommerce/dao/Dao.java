package com.pqnoje.ecommerce.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    
    Optional<T> get(int id) throws ClassNotFoundException, SQLException;
    
    List<T> getAll() throws ClassNotFoundException, SQLException ;
    
    T save(T t);
    
    void update(T t, int id);
    
    void delete(int t) throws SQLException;
}