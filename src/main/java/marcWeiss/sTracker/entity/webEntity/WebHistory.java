package marcWeiss.sTracker.entity.webEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

public class WebHistory {

	String name;
	int amount;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime datetime;
	
	public WebHistory() {
		super();
	}
	

	public WebHistory(String name, int amount, LocalDateTime datetime) {
		super();
		this.name = name;
		this.amount = amount;
		this.datetime = datetime;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public String getFormatedDatetime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	        return getDatetime().format(formatter);}
	
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	
}
