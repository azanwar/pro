

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PollResults
 */
@WebServlet("/PollResults")
public class PollResults extends HttpServlet {
	private static final long serialVersionUID = 1;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pollName = (String)request.getParameter("pollName");
		if (pollName==null) {
			pollName = (String)request.getSession().getAttribute("pollName");
		}
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("loggedOn");
		Poll pollData = ProPollServer.getPollData(pollName, (String)request.getSession().getAttribute("loggedOn"));
		ArrayList<Question> userResponse = ProPollServer.getUserResponse(pollName, username);
		request.setAttribute("pollData",pollData);
		request.setAttribute("userResponse", userResponse);
		if (username!=null&&username.equals(pollData.getCreator())) {
			ArrayList<String> pollResponse = ProPollServer.getAllResponses(pollName);
			request.setAttribute("pollResponses", pollResponse);
		}
		System.out.println(userResponse);
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/pollresults.jsp");
		dispatch.forward(request, response);
	}
}
