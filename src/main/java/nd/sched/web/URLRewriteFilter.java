package nd.sched.web;

import java.io.InputStream;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.stereotype.Component;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@Component
public class URLRewriteFilter extends UrlRewriteFilter {

    private static final String CONFIG_LOCATION = "/urlrewrite.xml";

    @Override
    protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
		final InputStream is = URLRewriteFilter.class.getResourceAsStream(CONFIG_LOCATION);
		Conf conf = new Conf(filterConfig.getServletContext(), is, "urlrewrite.xml", "");
		checkConf(conf);
    }
}
