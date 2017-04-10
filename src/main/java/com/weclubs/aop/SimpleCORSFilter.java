package com.weclubs.aop;

import com.google.gson.Gson;
import com.weclubs.model.response.WCResultData;
import com.weclubs.util.WCHttpStatus;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * servlet 拦截
 *
 * Created by fangzanpan on 2017/3/27.
 */
@Component
public class SimpleCORSFilter implements Filter {

    private Logger log = Logger.getLogger(SimpleCORSFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if ("http".equals(servletRequest.getScheme()) || "https".equals(servletRequest.getScheme())) {
            String method = ((HttpServletRequest) servletRequest).getMethod();
            log.info("servletRequest.toString = " + method + ";");
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-type");

            if (!"POST".equals(method) && !"post".equals(method)) {
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");

                WCHttpStatus check = WCHttpStatus.FAIL_REQUEST_UN_SUPPORT_METHOD;
                WCResultData resultData = WCResultData.getHttpStatusData(check, new HashMap<String, Object>());
                Gson gson = new Gson();
                String json = gson.toJson(resultData);

                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                    writer.append(json);
                    log.info("返回结果为：" + json);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
                return;
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
