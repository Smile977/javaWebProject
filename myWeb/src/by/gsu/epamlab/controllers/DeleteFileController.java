package by.gsu.epamlab.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.DaoException;
import by.gsu.epamlab.ifaces.TaskDao;
import by.gsu.epamlab.model.factories.TaskFactory;
import by.gsu.epamlab.utilities.FileOperation;

@WebServlet("/deleteFile")
public class DeleteFileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DeleteFileController.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idTaskStr = request.getParameter(ConstantsJSP.ID_TASK_NAME);
		String view = request.getParameter(ConstantsJSP.VIEW_NAME);
		int idTask = Integer.parseInt(idTaskStr);
		TaskDao taskDao = TaskFactory.getClassFromFactory();
		try {
			FileOperation.deleteFileById(idTask);
			taskDao.deleteFile(idTask);
			response.sendRedirect(request.getContextPath() + ConstantsJSP.MAIN_VIEW_URL + view);
		} catch (DaoException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			request.setAttribute(ConstantsJSP.ERROR_MESSAGE_NAME, ConstantsJSP.CONNECTION_ERROR_MESSAGE);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(ConstantsJSP.ERROR_PAGE_URL);
			rd.forward(request, response);
		}		
	}
}