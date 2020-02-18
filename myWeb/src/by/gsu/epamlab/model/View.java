package by.gsu.epamlab.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import by.gsu.epamlab.exceptions.DaoException;
import by.gsu.epamlab.ifaces.TaskDao;
import by.gsu.epamlab.model.beans.Link;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.factories.TaskFactory;
import by.gsu.epamlab.utilities.Utilities;

public enum View {

	TODAY("Today", t -> t.getDate().before(Utilities.getTomorrow()) & t.getStatus() == Task.TO_DO),
	TOMORROW("Tomorrow", t -> t.getDate().equals(Utilities.getTomorrow()) & t.getStatus() == Task.TO_DO),
	SOMEDAY("Someday", t -> t.getDate().after(Utilities.getTomorrow()) & t.getStatus() == Task.TO_DO),
	FIXED("Fixed", t -> t.getStatus() == Task.FIXED), 
	BIN("Recycle Bin", t -> t.getStatus() == Task.BIN);

	private String title;
	private Predicate<Task> tasksFilter;

	View(String title, Predicate<Task> tasksFilter) {
		this.title = title;
		this.tasksFilter = tasksFilter;
	}

	// selecting tasks by date or status from all user's tasks
	public List<Task> getTasks(int userId) throws DaoException {
		List<Task> selectedTasks = taskDao.getTasksByUserId(userId).stream().filter(tasksFilter)
				.collect(Collectors.toList());
		selectedTasks.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
		return selectedTasks;
	}

	public String getViewTitle() {
		String viewTitle = title;
		if (this == TODAY) {
			viewTitle += " " + util.getTodayDate();
		}
		if (this == TOMORROW) {
			viewTitle += " " + util.getTomorrowDate();
		}
		return viewTitle;
	}

	// prepare list of top view links for main page
	public List<Link> getViewLinks() {
		List<Link> viewLinks = new ArrayList<Link>();
		for (View v : View.values()) {
			String url = "/main?view=" + v.name().toLowerCase();
			String text = v.getViewTitle();
			String linkClass = (v == this) ? "activeViewLink" : "viewLink";
			viewLinks.add(new Link(url, text, linkClass));
		}
		return viewLinks;
	}

	// prepare map of bottom operation links for main page
	public static Map<String, List<Link>> getOperationLinks() {
		Map<String, List<Link>> operationLinks = new HashMap<String, List<Link>>();
		for (View v : View.values()) {
			switch (v) {
			case FIXED:
				operationLinks.put(v.name().toLowerCase(), Arrays.asList(new Link("javascript:doOperation('remove')", "Remove")));
				break;
			case BIN:
				operationLinks.put(v.name().toLowerCase(),
						Arrays.asList(new Link("javascript:doOperation('restore')", "Restore"),
								new Link("javascript:doOperation('delete')", "Delete"),
								new Link("javascript:emptyBin()", "Empty Bin")));
				break;
			default:
				operationLinks.put(v.name().toLowerCase(),
						Arrays.asList(new Link("add.jsp?view=" + v.name().toLowerCase(), "Add Task"),
								new Link("javascript:doOperation('fix')", "Fix"),
								new Link("javascript:doOperation('remove')", "Remove")));

			}
		}
		return operationLinks;
	}

	private static TaskDao taskDao = TaskFactory.getClassFromFactory();
	private static Utilities util = new Utilities();
}