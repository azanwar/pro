import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class ProPollServer {
	
	private static Vector<Session> sessionVector = new Vector<Session>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		sessionVector.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(message);
		try {
			for(Session s : sessionVector) {
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
		sessionVector.remove(session);
	}
	
	@OnError
		public void error(Throwable error) {
		System.out.println("Error!");
	}
	
	
	public static ArrayList<String> getAllResponses(String pollName){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * from Polls WHERE pollName=?");
			ps.setString(1, pollName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int pollID = rs.getInt("pollID");
			rs.close();
			ps.close();
			ps = conn.prepareStatement("SELECT * FROM UserPolls WHERE pollID=?");
			ps.setInt(1, pollID);
			rs = ps.executeQuery();
			ArrayList<String> response = new ArrayList<String>();
			ArrayList<Integer> userIDs = new ArrayList<Integer>();
			while(rs.next()) {
				userIDs.add(rs.getInt("userID"));
			}
			rs.close();
			ps.close();
			for (int i = 0; i<userIDs.size();i++) {
				ps = conn.prepareStatement("SELECT username FROM Users WHERE userID=?");
				ps.setInt(1, userIDs.get(i));
				rs = ps.executeQuery();
				rs.next();
				String username = rs.getString("username");
				response.add(username+":");
				ArrayList<Question> qs = getUserResponse(pollName,username);
				for (int j = 1;j<=qs.size();j++) {
					response.add("&nbsp;&nbsp;&nbsp;&nbsp;"+j+") "+ qs.get(j-1).toString()+" : "+qs.get(j-1).getResponseString(0));
				}
				rs.close();
				ps.close();
			}
			
			return response;
					
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<String> getInvitedPolls(String un){
		int userID = getUserID(un);
 		ArrayList<String> out = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM AllowedUsers WHERE userID=?");
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			System.out.println("got allowedusers");
			while (rs.next()) {
				PreparedStatement ps0 = conn.prepareStatement("SELECT * FROM UserPolls WHERE userID=? AND pollID=?");
				System.out.println("got polls");
				ps0.setInt(1, userID);
				int pollID = rs.getInt("pollID");
				ps0.setInt(2, pollID);
				ResultSet rs0 = ps0.executeQuery();
				if (!rs0.next()) {
					System.out.println("not done");
					PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Polls WHERE  pollID=?");
					ps1.setInt(1, pollID);
					ResultSet rs1 = ps1.executeQuery();
					rs1.next();
					out.add(rs1.getString("pollName"));
				}
			}
			return out;
		}
		catch(ClassNotFoundException | SQLException e) {
 			e.printStackTrace();
 			return null;
 		}
 		
 	}
	
	public static ArrayList<String> getJoinedPolls(String un) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT userID FROM Users WHERE username=?");
			ps.setString(1, un);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int userID = rs.getInt("userID");
			rs.close();
			ps.close();
			ps = conn.prepareStatement("SELECT pollID FROM UserPolls WHERE userID=?");
			ps.setInt(1,userID);
			rs = ps.executeQuery();
			ArrayList<Integer> pollIDs = new ArrayList<Integer>();
			while(rs.next()) {
				pollIDs.add(rs.getInt("pollID"));
			}
			rs.close();
			ps.close();
			ArrayList<String> names = new ArrayList<String>();
			for (int i = 0;i<pollIDs.size();i++) {
				ps = conn.prepareStatement("SELECT pollName FROM Polls WHERE pollID=?");
				ps.setInt(1, pollIDs.get(i));
				rs = ps.executeQuery();
				rs.next();
				names.add(rs.getString("pollName"));
			}
			rs.close();
			ps.close();
			conn.close();
			return names;
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean checkForResponse(int pollID,String un) {
		int userID = getUserID(un);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM UserPolls WHERE userID=? AND pollID=?");
			ps.setInt(1, userID);
			ps.setInt(2,pollID);
			ResultSet rs = ps.executeQuery();
			return (rs.next());
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public static int getUserID(String un) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT userID FROM Users WHERE username=?");
			ps.setString(1, un);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int userID = rs.getInt("userID");
			rs.close();
			ps.close();
			return userID;
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static ArrayList<String> getCreatedPolls(String un) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT userID FROM Users WHERE username=?");
			ps.setString(1, un);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int userID = rs.getInt("userID");
			rs.close();
			ps.close();
			ps = conn.prepareStatement("SELECT pollName FROM Polls WHERE creatorID=?");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			ArrayList<String> out = new ArrayList<String>();
			while(rs.next()) {
				out.add(rs.getString("pollName"));
			}
			return out;
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void createUser(String un, String pw) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Users (username,pw) VALUES (?, ?)");
			ps.setString(1, un);
			ps.setString(2, pw);
			ps.executeUpdate();
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		}
	
	public static boolean validateSignup(String un, String pw) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			ps.setString(1,un);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return false;
			
			return true;
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Poll getPollData(String pollName, String userID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Polls WHERE pollName=?");
			ps.setString(1,pollName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int pollID = rs.getInt("pollID");
			int creatorID = rs.getInt("creatorID");
			rs.close();
			ps.close();
			ps = conn.prepareStatement("SELECT username FROM Users WHERE userID=?");
			ps.setInt(1, creatorID);
			rs = ps.executeQuery();
			rs.next();
			String creator = rs.getString("username");
			rs.close();
			ps.close();
			Poll poll = new Poll(pollName, pollID, creator, null);
			ps = conn.prepareStatement("SELECT * FROM Questions WHERE pollID=?");
			ps.setInt(1, pollID);
			rs = ps.executeQuery();
			ArrayList<Integer> qIDs = new ArrayList<Integer>();
			while (rs.next()) {
				qIDs.add(rs.getInt("questionID"));
				poll.addQuestion(new Question(rs.getString("question")));
			}
			rs.close();
			ps.close();
			for (int i = 0;i<qIDs.size();i++) {
				ps = conn.prepareStatement("SELECT * FROM Responses WHERE questionID=?");
				ps.setInt(1, qIDs.get(i));
				rs = ps.executeQuery();
				while (rs.next()) {
					poll.getQuestion(i).addResponse(rs.getString("response"), rs.getInt("num"));
				}
				rs.close();
				ps.close();
			}
			conn.close();
			return poll;
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Question> getUserResponse(String pollName,String un) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			ps.setString(1,un);
			ResultSet rs = ps.executeQuery();
			int userID = 0;
			if (rs.next()) userID = rs.getInt("userID");
			rs.close();
			ps.close();
			ps = conn.prepareStatement("SELECT * FROM Polls WHERE pollName=?");
			ps.setString(1,pollName);
			rs = ps.executeQuery();
			int pollID = 0;
			if (rs.next()) pollID = rs.getInt("pollID");
			rs.close();
			ps.close();
			ps = conn.prepareStatement("SELECT * FROM UserResponses WHERE userID=? AND pollID=?");
			ps.setInt(1,userID);
			ps.setInt(2,pollID );
			rs = ps.executeQuery();
			ArrayList<Integer> questionIDs = new ArrayList<Integer>();
			ArrayList<Integer> responseIDs = new ArrayList<Integer>();
			while (rs.next()) {
				questionIDs.add(rs.getInt("questionID"));
				responseIDs.add(rs.getInt("responseID"));
			}
			rs.close();
			ps.close();
			ArrayList<Question> userQandA = new ArrayList<Question>();
			for (int i = 0; i< responseIDs.size();i++) {
				ps = conn.prepareStatement("SELECT * FROM Questions WHERE questionID=?");
				ps.setInt(1, questionIDs.get(i));
				rs = ps.executeQuery();
				rs.next();
				Question q = new Question(rs.getString("question"));
				rs.close();
				ps.close();
				ps = conn.prepareStatement("SELECT * FROM Responses WHERE responseID=?");
				ps.setInt(1, responseIDs.get(i));
				rs = ps.executeQuery();
				rs.next();
				q.addResponse(rs.getString("response"), 0);
				rs.close();
				ps.close();
				userQandA.add(q);
			}
			return userQandA;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean validateLogin(String un, String pw) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			ps.setString(1,un);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				if (pw.equals(rs.getString("pw"))) {
					rs.close();
					ps.close();
					conn.close();
					return true;
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static int addPoll(Poll poll, String username) {
		int id = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			
			int userID = 0;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				userID = rs.getInt("userID");
			}
			//insert into Polls
			PreparedStatement ps0 = conn.prepareStatement("INSERT INTO Polls (pollName, creatorID, private, active) VALUES(?, ?, ?, 1)", Statement.RETURN_GENERATED_KEYS);
			ps0.setString(1,poll.getName());
			ps0.setInt(2, userID);
			ps0.setBoolean(3, poll.getPollType());
			ps0.executeUpdate();
			ResultSet generatedKeys = ps0.getGeneratedKeys();
			if (generatedKeys.next()) {
				int pollID = generatedKeys.getInt(1);
				
				//insert into AllowedUsers
				if (poll.getPollType()==true&&poll.getPermittedUsers().size()!=0) {
					Iterator<String> p = poll.getPermittedUsers().iterator();
					while (p.hasNext()) {
						String un = p.next();
						System.out.println(un);
						int allowedID = getUserID(un);
						PreparedStatement ps1 = conn.prepareStatement("INSERT INTO AllowedUsers (userID, pollID) VALUES(?, ?)");
						ps1.setInt(1, allowedID); //dynamic
						ps1.setInt(2, pollID);
						ps1.executeUpdate();
						ps1.close();
					}
				}
				
				for (int i=0; i<poll.getQuestions().size(); i++) {
					//insert into Questions
					PreparedStatement ps1 = conn.prepareStatement("INSERT INTO Questions (pollID, question) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
					ps1.setInt(1, pollID);
					ps1.setString(2,poll.getQuestions().get(i).toString());
					ps1.executeUpdate();
					ResultSet generatedKeys2 = ps1.getGeneratedKeys();
					if (generatedKeys2.next()) {
						int questionID = generatedKeys2.getInt(1);
						//insert into Responses
						for (int j=0; j<poll.getQuestions().get(i).size(); j++) {
							PreparedStatement ps2 = conn.prepareStatement("INSERT INTO Responses (questionID, response, num) VALUES(?, ?, 0)");
							ps2.setInt(1, questionID);
							ps2.setString(2,poll.getQuestions().get(i).getResponseString(j));
							ps2.executeUpdate();
							ps2.close();
						}
					}
					ps1.close();
				}
			}
			
			ps0.close();
			rs.close();
			ps.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static Poll getPollInfo(int pollID) {
		Poll poll = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps10 = conn.prepareStatement("SELECT * FROM Polls WHERE pollID=?");
			ps10.setInt(1,pollID);
			ResultSet rs10 = ps10.executeQuery();
			int creatorID = 0;
			String name = "";
			boolean priv = false;
			if (rs10.next()) {
				name = rs10.getString("pollName");
				priv = rs10.getBoolean("private");
				creatorID = rs10.getInt("creatorID");
			}
			rs10.close();
			ps10.close();
			PreparedStatement ps0 = conn.prepareStatement("SELECT * FROM Users WHERE userID=?");
			ps0.setInt(1, creatorID);
			ResultSet rs0 = ps0.executeQuery();
			rs0.next();
			String creator = rs0.getString("username");
			poll = new Poll(name, pollID, creator, new HashSet<String>());
			poll.setPrivPoll(priv);
			//getting all the questions
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Questions WHERE pollID=?");
			ps1.setInt(1,pollID);
			ResultSet rs1 = ps1.executeQuery();
			int i = 0;
			while (rs1.next()) {
				Question q = new Question(rs1.getString("question"));
				//getting all the responses for each question
				PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM Responses WHERE questionID=?");
				ps2.setInt(1, rs1.getInt("questionID"));
				ResultSet rs2 = ps2.executeQuery();
				int j=0;
				while (rs2.next()) {
					q.addResponse(rs2.getString("response"), Integer.parseInt(rs2.getString("num")));
					j++;
				}
				poll.addQuestion(q);
				i++;
				rs2.close();
				ps2.close();
			}
			rs1.close();
			ps1.close();
			rs0.close();
			ps0.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			return poll;
		}
		return poll;
	}
	
	public static boolean checkAllowed(String un, int pollID) {
		int userID = getUserID(un);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM AllowedUsers WHERE userID=? AND pollID=?");
			ps.setInt(1,userID);
			ps.setInt(2,pollID);
			ResultSet rs = ps.executeQuery();
			return (rs.next());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void updateResponse(int pollID, ArrayList<Integer> responsenum, String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ProPollData?user=root&password=root&useSSL=false");
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Questions WHERE pollID=?");
			ps1.setInt(1,pollID);
			ResultSet rs1 = ps1.executeQuery();
			int i = 0;
			int userID = -20;
			if (username!=null) {
				PreparedStatement ps = conn.prepareStatement("SELECT userID FROM Users WHERE username=?");
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				rs.next();
				userID = rs.getInt("userID");
				rs.close();
				ps.close();
				ps = conn.prepareStatement("INSERT INTO UserPolls (userID,pollID) VALUES (?,?);");
				ps.setInt(1, userID);
				ps.setInt(2, pollID);
				ps.executeUpdate();
			}
			while (rs1.next()) {
				//getting all the responses for each question
				PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM Responses WHERE questionID=?");
				ps2.setInt(1, rs1.getInt("questionID"));
				ResultSet rs2 = ps2.executeQuery();
				for (int j=0; j<responsenum.get(i)+1; j++) {
					rs2.next();
				}
				int responseid = rs2.getInt("responseID");
				int responsen = rs2.getInt("num");
				PreparedStatement ps3 = conn.prepareStatement("UPDATE Responses SET num=? WHERE responseID=?");
				ps3.setInt(1, responsen+1);
				ps3.setInt(2, responseid);
				ps3.executeUpdate();
				
				
				if (username!=null) {
					PreparedStatement ps4 = conn.prepareStatement("INSERT INTO UserResponses(userID, questionID, responseID, pollID) VALUES(?,?,?,?)");
					ps4.setInt(1, userID);
					ps4.setInt(2, rs1.getInt("questionID"));
					ps4.setInt(3, responseid);
					ps4.setInt(4, pollID);
					ps4.executeUpdate();
					
					ps4.close();
				}
				ps3.close();
				rs2.close();
				ps2.close();
			}
			rs1.close();
			ps1.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
