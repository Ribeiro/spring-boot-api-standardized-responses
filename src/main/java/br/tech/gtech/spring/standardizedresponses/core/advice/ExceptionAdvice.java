package br.tech.gtech.spring.standardizedresponses.core.advice;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.tech.gtech.spring.standardizedresponses.autoconfig.ResponseProperties;
import br.tech.gtech.spring.standardizedresponses.autoconfig.ResponseProperties.ExceptionProperties;
import br.tech.gtech.spring.standardizedresponses.core.model.SingleResult;

@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseProperties responseProperties;

    public ExceptionAdvice(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handle(Exception e) {
        return getResult(e, getExceptionProperties(e, ExceptionProperties.UNHANDLED));
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(Exception e) {
        return getResult(e, getExceptionProperties(e, ExceptionProperties.NOT_FOUND));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleNotSupportedMethodException(Exception e) {
        return getResult(e, getExceptionProperties(e, ExceptionProperties.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingRequestParameterException(Exception e) {
        return getResult(e, getExceptionProperties(e, ExceptionProperties.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationException(Exception e) {
        return getResult(e, getExceptionProperties(e, ExceptionProperties.BAD_REQUEST));
    }

    private ExceptionProperties getExceptionProperties(Exception e, ExceptionProperties unhandled) {
        ExceptionProperties exceptionModel = responseProperties.getExceptions()
												                .values().stream()
												                .filter(r -> r.getType().equals(e.getClass()))
												                .findFirst()
												                .orElse(unhandled);

        exceptionModel.setMessage(exceptionModel.getMessage());
        return exceptionModel;
    }

    private SingleResult<Object> getResult(Exception e, ExceptionProperties exceptionModel) {
        SingleResult<Object> result = new SingleResult<>();
        result.setSuccess(false);
        result.setCode(exceptionModel.getCode());
        result.setMessage(exceptionModel.getMessage());
        result.setData(e.getMessage());
        return result;
    }
    
}