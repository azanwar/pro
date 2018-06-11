

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoinPollResponsesServlet
 */
@WebServlet("/JoinPollResponsesServlet")
public class JoinPollResponsesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinPollResponsesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Integer> responsenum = new ArrayList<Integer>();
		for (int i=0; i<(Integer)request.getSession().getAttribute("numquestions")-1; i++) {
			responsenum.add(Integer.parseInt((String)request.getParameter("question"+Integer.toString(i))));
		}
		ProPollServer.updateResponse((Integer)request.getSession().getAttribute("pollID"), responsenum, (String)request.getSession().getAttribute("loggedOn"));
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/pollresults.jsp");
		dispatch.forward(request, response);
	}
}
