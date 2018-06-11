import java.util.ArrayList;
import java.util.HashSet;

public class Poll {
	private int pollID;
	private boolean privPoll;
	private String creator;
	private String name;
	private ArrayList<Question> questions;
	private HashSet<String> permittedUsers;
	
	public Poll(String name,int id,String creator,HashSet<String> permittedUsers) {
		this.name = name;
		this.pollID = id;
		this.creator = creator;
		questions = new ArrayList<Question>();
		this.permittedUsers = permittedUsers;
		if (permittedUsers!=null) this.privPoll = permittedUsers.size()==0? false : true;
	}
	
	public void setPrivPoll(boolean priv) {
		privPoll = priv;
	}
	
	public boolean getPrivPoll() {
		return privPoll;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public boolean getPollType() {
		return privPoll;
	}
	
	public HashSet<String> getPermittedUsers() {
		return permittedUsers;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public int size() {
		return questions.size(); 
	}
	
	public String getName() {
		return name;
	}
	
	public void addQuestion(Question q) {
		questions.add(q);
	}

	public Question getQuestion(int num) {
		return questions.get(num);
	}
	
}