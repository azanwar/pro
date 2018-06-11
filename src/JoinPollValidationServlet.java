

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoinPollValidationServlet
 */
@WebServlet("/JoinPollValidationServlet")
public class JoinPollValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinPollValidationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pollID = Integer.parseInt(request.getParameter("pollID"));
		Poll poll = ProPollServer.getPollInfo(pollID, (String)request.getSession().getAttribute("loggedOn"));
		if (!(poll.getPrivPoll()&&(String)request.getSession().getAttribute("loggedOn")!=null&&poll.getPermittedUsers().contains((String)request.getSession().getAttribute("loggedOn")))) {
			PrintWriter pw = response.getWriter();
			pw.println("This is a private poll that you were not invited to!");
			pw.flush();
		}
	}
	
}
