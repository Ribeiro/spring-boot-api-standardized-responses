package br.tech.gtech.spring.standardizedresponses.core.service;

import org.springframework.stereotype.Service;

import br.tech.gtech.spring.standardizedresponses.core.model.CommonResult;
import br.tech.gtech.spring.standardizedresponses.core.model.SingleResult;

@Service
public class ResponseService {
    public <T> CommonResult getResult(T data) {
        return getSingleResult(data);
    }

    private <T> CommonResult getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        return result;
    }
}