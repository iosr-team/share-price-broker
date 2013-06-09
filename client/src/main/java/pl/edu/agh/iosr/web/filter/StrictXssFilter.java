package pl.edu.agh.iosr.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class StrictXssFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(StrictXssFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new StrictXssRequestWrapper((HttpServletRequest) request), response);
    }

}
