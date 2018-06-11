import java.util.ArrayList;

import javafx.util.Pair;

public class Question {
	private String question;
	private ArrayList<Pair<String,Integer>> responses;
	public Question(String question) {
		this.question = question;
		responses = new ArrayList<Pair<String,Integer>>();
	}
	
	public int size() {
		return responses.size();
	}
	public void addResponse(String response, int count) {
		responses.add(new Pair<String,Integer>(response,count));
	}
	
	public String getResponseString(int i){
		if (i>=0 && i<responses.size()) return responses.get(i).getKey();
		return null;
	}
	
	public int getResponseCount(int i){
		if (i>=0 && i<responses.size()) return responses.get(i).getValue();
		return -1;
	}
	
	public String toString() {
		return question;
	}
}
