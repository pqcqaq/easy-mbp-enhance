package online.zust.qcqcqc.easymbpenhance.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.zust.qcqcqc.services.entity.dto.Result;
import online.zust.qcqcqc.services.entity.dto.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author qcqcqc
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
        response.setContentType("application/json;charset=UTF-8");
        String msg = "Insufficient permissions to access system resources";
        Result<String> error = Result.error(ResultCode.FORBIDDEN.getCode(), msg, null);
        String s = new ObjectMapper().writeValueAsString(error);
        response.getWriter().println(s);
    }
}
