package svm.backend.web.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
    
    public static StringBuffer getBaseURL(HttpServletRequest request) {
        
        StringBuffer url = request.getRequestURL();
        String convertedUrl = url.toString();
        url.delete(convertedUrl.lastIndexOf(request.getRequestURI()), convertedUrl.length());
        
        return url;
        
    }
    
}
