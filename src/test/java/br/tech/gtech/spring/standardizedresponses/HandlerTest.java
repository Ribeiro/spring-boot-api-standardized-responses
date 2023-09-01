package br.tech.gtech.spring.standardizedresponses;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import br.tech.gtech.spring.standardizedresponses.autoconfig.ResponseProperties;
import br.tech.gtech.spring.standardizedresponses.autoconfig.ResponseProperties.ExceptionProperties;
import br.tech.gtech.spring.standardizedresponses.autoconfig.ResponseProperties.SuccessProperties;
import br.tech.gtech.spring.standardizedresponses.controller.DemoApi;
import br.tech.gtech.spring.standardizedresponses.core.advice.ExceptionAdvice;
import br.tech.gtech.spring.standardizedresponses.core.advice.ResponseAdvice;
import br.tech.gtech.spring.standardizedresponses.core.service.ResponseService;


class HandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ResponseService responseService = new ResponseService();
        ResponseProperties responseProperties = getResponseProperties();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi())
                .setControllerAdvice(
                        new ExceptionAdvice(responseProperties),
                        new ResponseAdvice(responseService, responseProperties))
                .build();
    }

    @Test
    void api_test() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.code", equalTo(200)))
                .andExpect(jsonPath("$.message", equalTo("OK")))
                .andExpect(jsonPath("$.data.value", equalTo("hi")))
        ;
    }

    @Test
    void api2_test() throws Exception {
        mockMvc.perform(get("/2"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.code", equalTo(200)))
                .andExpect(jsonPath("$.message", equalTo("OK")))
                .andExpect(jsonPath("$.data.value", equalTo("hi")))
        ;
    }

    @Test
    void new_api_test() throws Exception {
        mockMvc.perform(get("/exception"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("Custom Message")))
                .andExpect(jsonPath("$.data", equalTo("RuntimeException")))
        ;
    }

    @Test
    void 요청파라미터누락_test() throws Exception {
        mockMvc.perform(get("/3"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("Bad Request")))
                .andExpect(jsonPath("$.data", equalTo("Required String parameter 'param' is not present")))
        ;
    }

    @Test
    void http_method_test() throws Exception {
        mockMvc.perform(post("/exception"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(405)))
                .andExpect(jsonPath("$.message", equalTo("Method Not Allowed")))
                .andExpect(jsonPath("$.data", equalTo("Request method 'POST' not supported")))
        ;
    }

    @Test
    void http_status_test() throws Exception {
        ResponseService responseService = new ResponseService();
        ResponseProperties responseProperties = getResponseProperties2();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi())
                .setControllerAdvice(
                        new ExceptionAdvice(responseProperties),
                        new ResponseAdvice(responseService, responseProperties))
                .build();

        mockMvc.perform(get("/exception"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(4000)))
                .andExpect(jsonPath("$.message", equalTo("Runtime Exception")))
                .andExpect(jsonPath("$.data", equalTo("RuntimeException")))
        ;
    }

    @Test
    void test() throws Exception {
        ResponseService responseService = new ResponseService();
        ResponseProperties responseProperties = getResponseProperties3();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi())
                .setControllerAdvice(
                        new ExceptionAdvice(responseProperties),
                        new ResponseAdvice(responseService, responseProperties))
                .build();

        mockMvc.perform(get("/exception"))
                .andDo(print())
                .andExpect(status().isNotImplemented())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(501)))
                .andExpect(jsonPath("$.message", equalTo("Unhandled Exception")))
                .andExpect(jsonPath("$.data", equalTo("RuntimeException")))
        ;
    }

    @Test
    void valid_test() throws Exception {
        ResponseService responseService = new ResponseService();
        ResponseProperties responseProperties = getResponseProperties3();

        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi())
                .setControllerAdvice(
                        new ExceptionAdvice(responseProperties),
                        new ResponseAdvice(responseService, responseProperties))
                .build();

        mockMvc.perform(get("/valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", equalTo(false)))
                .andExpect(jsonPath("$.code", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("Bad Request")))
        ;
    }

    private ResponseProperties getResponseProperties() {
        ResponseProperties responseProperties = new ResponseProperties();
        responseProperties.setSuccess(getSuccessProperties());

        responseProperties.setExceptions(getExceptions());

        return responseProperties;
    }

    private ResponseProperties getResponseProperties2() {
        ResponseProperties responseProperties = new ResponseProperties();
        responseProperties.setSuccess(getSuccessProperties());

        responseProperties.setExceptions(getExceptions2());

        return responseProperties;
    }

    private ResponseProperties getResponseProperties3() {
        ResponseProperties responseProperties = new ResponseProperties();
        responseProperties.setSuccess(getSuccessProperties());

        responseProperties.setExceptions(getExceptions3());

        return responseProperties;
    }

    private Map<String, ExceptionProperties> getExceptions() {
        ExceptionProperties exceptionProperties = new ExceptionProperties();
        exceptionProperties.setCode(400);
        exceptionProperties.setMessage("Custom Message");
        exceptionProperties.setType(RuntimeException.class);

        Map<String, ExceptionProperties> map = new HashMap<>();
        map.put("err", exceptionProperties);
        return map;
    }

    private Map<String, ExceptionProperties> getExceptions2() {
        ExceptionProperties exceptionProperties = new ExceptionProperties();
        exceptionProperties.setCode(4000);
        exceptionProperties.setMessage("Runtime Exception");
        exceptionProperties.setType(RuntimeException.class);

        Map<String, ExceptionProperties> map = new HashMap<>();
        map.put("err", exceptionProperties);
        return map;
    }

    private Map<String, ExceptionProperties> getExceptions3() {
        return new HashMap<>();
    }


    private SuccessProperties getSuccessProperties() {
        SuccessProperties successProperties = new SuccessProperties();
        successProperties.setCode(200);
        successProperties.setMessage("OK");
        return successProperties;
    }
}

