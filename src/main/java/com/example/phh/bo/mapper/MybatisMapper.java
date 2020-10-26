package com.example.phh.bo.mapper;

import java.util.List;

import com.example.phh.bo.model.Todo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MybatisMapper {
    public List<Todo> findAll();
    public Todo findById(String id);
    public int add(Todo model);
    public int modify(Todo model);
    public int remove(String id);
}