package online.zust.qcqcqc.easymbpenhance.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.entity.dto.Result;
import online.zust.qcqcqc.services.entity.dto.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author qcqcqc
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String message = authException.getMessage();
        if (message.contains("失效")) {
            response.setStatus(ResultCode.BANDED.getCode());
        } else {
            response.setStatus(ResultCode.UNAUTHORIZED.getCode());
        }
        String msg = "Authentication failed, unable to access system resources";
        response.setContentType("application/json;charset=UTF-8");
        Result<String> error = Result.error(ResultCode.UNAUTHORIZED.getCode(), msg, message);
        String s = new ObjectMapper().writeValueAsString(error);
        response.getWriter().println(s);
    }

}
