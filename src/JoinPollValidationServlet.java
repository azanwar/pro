

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
		if (request.getParameter("pollID")==null||request.getParameter("pollID").equals("")) {
			PrintWriter pw = response.getWriter();
			pw.println("Please enter a valid poll ID!");
			pw.flush();
			return;
		}
		int pollID = Integer.parseInt(request.getParameter("pollID"));
		Poll poll = ProPollServer.getPollInfo(pollID);
		if (poll==null) {
			PrintWriter pw = response.getWriter();
			pw.println("Please enter a valid poll ID!");
			pw.flush();
			return;
		}
		String un = (String) request.getSession().getAttribute("loggedOn");
 		if (poll.getPrivPoll()&&(!ProPollServer.checkAllowed(un, pollID))) {
			PrintWriter pw = response.getWriter();
			pw.println("This is a private poll that you were not invited to!");
			pw.flush();
		}
	}
	
}
