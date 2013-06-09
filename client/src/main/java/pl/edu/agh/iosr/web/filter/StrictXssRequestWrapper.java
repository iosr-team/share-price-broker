package pl.edu.agh.iosr.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;


public class StrictXssRequestWrapper extends XssRequestWrapper  {

    public StrictXssRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    protected String stripXss(String value) {
        if (value != null) {

            value = super.stripXss(value);

            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("'", "&#39;").replaceAll("\"", "&quot;");
            value = value.replaceAll("&", "&amp;");

          /*  value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

            value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
            value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
            value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
            value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
            //value = value.replaceAll("<script>", "");
            //value = value.replaceAll("</script>", "");
            */
        }
        return value;
    }
}
