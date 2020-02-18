package by.gsu.epamlab.controllers;

import java.io.IOException;
import java.util.Optional;
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
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.factories.TaskFactory;
import by.gsu.epamlab.utilities.FileOperation;

import javax.servlet.annotation.MultipartConfig;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100) // 100 MB
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UploadController.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idTaskStr = request.getParameter(ConstantsJSP.ID_TASK_NAME);
		int idTask = Integer.parseInt(idTaskStr);
		TaskDao taskDao = TaskFactory.getClassFromFactory();
		try {
			Optional<Task> task = taskDao.getTaskById(idTask);
			request.setAttribute(ConstantsJSP.TASK_NAME, task.get());
			RequestDispatcher rd = getServletContext().getRequestDispatcher(ConstantsJSP.UPLOAD_PAGE_URL);
			rd.forward(request, response);
		} catch (DaoException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			request.setAttribute(ConstantsJSP.ERROR_MESSAGE_NAME, ConstantsJSP.CONNECTION_ERROR_MESSAGE);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(ConstantsJSP.ERROR_PAGE_URL);
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idTaskStr = request.getParameter(ConstantsJSP.ID_TASK_NAME);
		String view = request.getParameter(ConstantsJSP.VIEW_NAME);
		int idTask = Integer.parseInt(idTaskStr);
		String fileName = FileOperation.addPermanentFile(request, idTask);
		TaskDao taskDao = TaskFactory.getClassFromFactory();
		try {
			taskDao.addFile(idTask, fileName);
			response.sendRedirect(request.getContextPath() + ConstantsJSP.MAIN_VIEW_URL + view);
		} catch (DaoException e) {
			if (fileName != null) {
				FileOperation.deleteFile(idTask + "_" + fileName);
			}
			LOGGER.log(Level.SEVERE, e.toString(), e);
			request.setAttribute(ConstantsJSP.ERROR_MESSAGE_NAME, ConstantsJSP.CONNECTION_ERROR_MESSAGE);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(ConstantsJSP.ERROR_PAGE_URL);
			rd.forward(request, response);
		}
	}
}