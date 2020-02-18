package by.gsu.epamlab.controllers;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.InitException;
import by.gsu.epamlab.model.View;
import by.gsu.epamlab.model.beans.Link;
import by.gsu.epamlab.model.factories.TaskFactory;
import by.gsu.epamlab.model.factories.UserFactory;
import by.gsu.epamlab.utilities.FileOperation;
import by.gsu.epamlab.utilities.Utilities;

@WebServlet(urlPatterns = { "/start" }, initParams = {
		@WebInitParam(name = "propsName", value = "resources.web") }, loadOnStartup = 1)
public class StartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StartController.class.getName());

	public void init(ServletConfig sc) throws ServletException {
		super.init(sc);
		try {
			final String PROPERTIES = sc.getInitParameter("propsName");
			ResourceBundle rb = ResourceBundle.getBundle(PROPERTIES);
			UserFactory.init(rb);
			TaskFactory.init(rb);
			
			// Instance of Utilities class to get dates in JSP pages
			Utilities util = new Utilities();
			getServletContext().setAttribute(ConstantsJSP.UTILITY_NAME, util);
			
			// Map of operation links for the main page
			Map<String, List<Link>> operationLinks = View.getOperationLinks();
			getServletContext().setAttribute(ConstantsJSP.OPERATION_LINKS_NAME, operationLinks);
			
			// Path to project directory for file operations
			String applicationPath = getServletContext().getRealPath("");
			String uploadPath = applicationPath + File.separator + ConstantsJSP.UPLOAD_DIR;
			File fileUploadDirectory = new File(uploadPath);
			if (!fileUploadDirectory.exists()) {
				fileUploadDirectory.mkdirs();
			}
			FileOperation.setUploadPath(uploadPath);
		} catch (InitException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			throw new ServletException(e);
		}
	}
}