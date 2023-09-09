package pl.ms.projectoverview.web.utils;

import jakarta.servlet.http.HttpServletRequest;

public class WebUtils {
    public static String getServerAddress(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getRequestURI(),"");
    }
}
