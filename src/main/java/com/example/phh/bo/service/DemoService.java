package com.example.phh.bo.service;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.naming.ServiceUnavailableException;

import com.example.phh.bo.mapper.MybatisMapper;
import com.example.phh.bo.model.Todo;
import com.mysql.cj.exceptions.UnableToConnectException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.undertow.util.BadRequestException;

@Service
public class DemoService {
    private final MybatisMapper MybatisMapper;

    public DemoService(MybatisMapper MybatisMapper) {
        this.MybatisMapper = MybatisMapper;
    }

    public String ccbo_api(String param1, String param2) {
        return param1 + "\n" + param2;
    }

    public List<Todo> findAll() {
        return MybatisMapper.findAll();
    }

    public Todo findById(String id) {
        Todo result = MybatisMapper.findById(id);
        System.out.println(result);
        return result;
    }

    public Todo add(Todo todo) {
    	MybatisMapper.add(todo);
        return MybatisMapper.findById(String.valueOf(todo.getId()));
    }

    public Todo modify(Todo todo) {
    	MybatisMapper.modify(todo);
        return MybatisMapper.findById(String.valueOf(todo.getId()));
    }

    public int remove(String id) throws Exception {
    	int result = MybatisMapper.remove(id);
    	if (result < 1) {
    		throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR.toString());
    	}
        return result;
    }

    public ResponseEntity<Object> error_api(String code) throws Exception {
        if(code.equals("400"))
            return new ResponseEntity<>(new BadRequestException(), HttpStatus.BAD_REQUEST);
        else if(code.equals("401"))
            return new ResponseEntity<>(new UnableToConnectException(), HttpStatus.UNAUTHORIZED);
        else if(code.equals("403"))
            return new ResponseEntity<>(new Exception(), HttpStatus.FORBIDDEN);
        else if(code.equals("404"))
            return new ResponseEntity<>(new Exception(), HttpStatus.NOT_FOUND);
        else if(code.equals("500"))
            return new ResponseEntity<>(new InternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        else if(code.equals("502"))
            return new ResponseEntity<>(new Exception(), HttpStatus.BAD_GATEWAY);
        else if(code.equals("503"))
            return new ResponseEntity<>(new ServiceUnavailableException(), HttpStatus.SERVICE_UNAVAILABLE);
        else if(code.equals("504")) {
            Thread.sleep(3000);
            return new ResponseEntity<>(new TimeoutException(), HttpStatus.GATEWAY_TIMEOUT);
        }            
        else
            return new ResponseEntity<>("200 그런_에러는_안남", HttpStatus.OK);
    }
}