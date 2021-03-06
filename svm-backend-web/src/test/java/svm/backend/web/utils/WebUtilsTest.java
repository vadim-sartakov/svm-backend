package svm.backend.web.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockHttpServletRequest;

public class WebUtilsTest {
    
    @Test
    public void testGetBaseURL() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("somewebresource.com");
        request.setServerPort(5088);
        request.setQueryString("?page=0");
        String resultUrl = WebUtils.getBaseURL(request);
        assertEquals("http://somewebresource.com:5088", resultUrl);
    }
    
}
