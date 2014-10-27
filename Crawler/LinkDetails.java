package Crawler;

public class LinkDetails {
	private String link;
	private int numberOfPeople;
	public LinkDetails(String link,int numberOfPeople){
		this.link=link;
		this.numberOfPeople=numberOfPeople;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	public String getLink() {
		return this.link;
	}
	public int getNumberOfPeople() {
		return numberOfPeople;
	}
}
