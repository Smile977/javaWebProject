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
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.constants.ConstantsJSP;
import by.gsu.epamlab.exceptions.DaoException;
import by.gsu.epamlab.ifaces.UserDao;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.UserFactory;
import by.gsu.epamlab.utilities.Utilities;

@WebServlet("/registrate")
public class RegistrateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(RegistrateController.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String account = request.getParameter(ConstantsJSP.ACCOUNT_NAME);
		String password = request.getParameter(ConstantsJSP.PASSWORD_NAME);
		String passwordHash = Utilities.getPasswordHash(password);
		HttpSession session = request.getSession();
		UserDao userDao = UserFactory.getClassFromFactory();
		try {
			Optional<User> user = userDao.addAndGetUser(account, passwordHash);
			if (user.isPresent()) {
				session.setAttribute(ConstantsJSP.USER_NAME, user.get());
				response.sendRedirect(request.getContextPath() + ConstantsJSP.MAIN_PAGE_URL);
			} else {
				request.setAttribute(ConstantsJSP.REGISTRATE_ERROR_NAME, ConstantsJSP.ACCOUNT_EXISTS_MESSAGE);
				RequestDispatcher rd = getServletContext().getRequestDispatcher(ConstantsJSP.REGISTRATE_PAGE_URL);
				rd.forward(request, response);
			}
		} catch (DaoException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			request.setAttribute(ConstantsJSP.REGISTRATE_ERROR_NAME, ConstantsJSP.CONNECTION_ERROR_MESSAGE);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(ConstantsJSP.REGISTRATE_PAGE_URL);
			rd.forward(request, response);
		}
	}
}