package br.tech.gtech.spring.standardizedresponses.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("spring.response")
public class ResponseProperties {
	private static final int HTTPS_STATUS_200 = 200;
	private static final String SUCCESS_MESSAGE = "SUCCESS";
	private static final String UNHANDLED_EXCEPTION = "Unhandled Exception";
	
    private SuccessProperties success = new SuccessProperties();
    private Map<String, ExceptionProperties> exceptions = new HashMap<>();
    private List<String> blackList = new ArrayList<>();

    public SuccessProperties getSuccess() {
        return success;
    }

    public void setSuccess(SuccessProperties success) {
        this.success = success;
    }

    public Map<String, ExceptionProperties> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Map<String, ExceptionProperties> exceptions) {
        this.exceptions = exceptions;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(final List<String> blackList) {
        this.blackList = blackList;
    }

    public static class SuccessProperties {
		private Integer code = HTTPS_STATUS_200;
        private String message = SUCCESS_MESSAGE;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String msg) {
            this.message = msg;
        }
    }

    public static class ExceptionProperties {
		public static final ExceptionProperties NOT_FOUND = new ExceptionProperties(HttpStatus.NOT_FOUND);
        public static final ExceptionProperties METHOD_NOT_ALLOWED = new ExceptionProperties(HttpStatus.METHOD_NOT_ALLOWED);
        public static final ExceptionProperties BAD_REQUEST = new ExceptionProperties(HttpStatus.BAD_REQUEST);
        public static final ExceptionProperties UNHANDLED = new ExceptionProperties(HttpStatus.NOT_IMPLEMENTED.value(), UNHANDLED_EXCEPTION);

        public ExceptionProperties() {
            this(HttpStatus.BAD_REQUEST);
        }

        private ExceptionProperties(HttpStatus httpStatus) {
            this(httpStatus.value(), httpStatus.getReasonPhrase());
        }

        private ExceptionProperties(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private Integer code;
        private String msg;
        private Class<RuntimeException> type;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return msg;
        }

        public void setMessage(String msg) {
            this.msg = msg;
        }

        public Class<RuntimeException> getType() {
            return type;
        }

        public void setType(Class<RuntimeException> type) {
            this.type = type;
        }
    }
}