

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoinPollServlet
 */
@WebServlet("/JoinPollServlet")
public class JoinPollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinPollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pollID = Integer.parseInt(request.getParameter("pollID"));
		request.getSession().setAttribute("pollID", pollID);
		Poll poll = ProPollServer.getPollInfo(pollID, (String)request.getSession().getAttribute("loggedOn"));
		request.setAttribute("name", poll.getName());
		for (int i=0; i<poll.size(); i++) {
			request.setAttribute("question"+Integer.toString(i), poll.getQuestion(i));
			for (int j=0; j< poll.getQuestion(i).size(); j++) {
				request.setAttribute("response"+Integer.toString((i*10)+j), poll.getQuestion(i).getResponseString(j));
			}
		}
		request.getSession().setAttribute("numquestions", poll.getQuestions().size());
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/JoinPoll.jsp");
		dispatch.forward(request, response);
	}

}
