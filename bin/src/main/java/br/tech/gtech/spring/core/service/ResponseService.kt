package br.tech.gtech.spring.core.service;

import br.tech.gtech.spring.core.model.SingleResult
import org.springframework.stereotype.Service;

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