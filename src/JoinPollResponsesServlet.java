

import java.io.IOException;
import java.io.PrintWriter;
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
		for (int i=0; i<(Integer)request.getSession().getAttribute("numquestions"); i++) {
			if (request.getParameter("question"+Integer.toString(i))==null) {
				RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/JoinPollServlet?pollID="+request.getSession().getAttribute("pollID")+"&invalid=true");
				dispatch.forward(request, response);
				return;
			}
			responsenum.add(Integer.parseInt((String)request.getParameter("question"+Integer.toString(i))));
		}
		ProPollServer.updateResponse((Integer)request.getSession().getAttribute("pollID"), responsenum, (String)request.getSession().getAttribute("loggedOn"));
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/PollResults?pollName="+request.getSession().getAttribute("pollName"));
		dispatch.forward(request, response);
	}
}
