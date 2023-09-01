package br.tech.gtech.spring.standardizedresponses.autoconfig;

import org.springframework.context.annotation.Import;
import br.tech.gtech.spring.standardizedresponses.core.advice.ExceptionAdvice;
import br.tech.gtech.spring.standardizedresponses.core.advice.ResponseAdvice;
import br.tech.gtech.spring.standardizedresponses.core.config.WebConfig;
import br.tech.gtech.spring.standardizedresponses.core.service.ResponseService;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ResponseService.class, ExceptionAdvice.class, ResponseAdvice.class, WebConfig.class})
public @interface EnableResponse {

}
