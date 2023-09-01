package br.tech.gtech.spring.standardizedresponses.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import br.tech.gtech.spring.standardizedresponses.core.service.ResponseService;


@Configuration
@ConditionalOnClass(ResponseService.class)
@EnableConfigurationProperties(ResponseProperties.class)
public class ResponseAutoConfiguration {
}
