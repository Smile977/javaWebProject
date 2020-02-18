package by.gsu.epamlab.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.model.View;
import by.gsu.epamlab.model.beans.User;

@WebFilter({ "/main"})
public class MainFilter implements Filter {
	
	private static final Logger LOGGER = Logger.getLogger(MainFilter.class.getName());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute(ConstantsJSP.USER_NAME);
		//Restrict access to application for guests
		if (user == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + ConstantsJSP.INDEX_PAGE_URL);
			return;
		}
		String viewStr = request.getParameter(ConstantsJSP.VIEW_NAME);
		// redirect to the default main page, if there is no view in the url
		if (viewStr == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + ConstantsJSP.MAIN_PAGE_URL);	
			return;
		}
		try {
			View view = View.valueOf(viewStr.toUpperCase());
		// redirect to the default main page, if user typed wrong view in the url
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.WARNING, e.toString(), e);
			httpResponse.sendRedirect(httpRequest.getContextPath() + ConstantsJSP.MAIN_PAGE_URL);	
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
