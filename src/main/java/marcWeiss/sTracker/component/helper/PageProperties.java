package marcWeiss.sTracker.component.helper;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class PageProperties {

	private String currentList = "today";
	private String category = "all";
	private String type = "list";
	private boolean compact = true;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate from;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate to;
	private int pagingValue;
	private long usageid;
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToggleTo() {
		return currentList.equals("today")?"everyDay":"today";
	}

	public long getUsageid() {
		return usageid;
	}

	public void setUsageid(long usageid) {
		this.usageid = usageid;
	}

	public int getPagingValue() {
		return pagingValue;
	}

	public void setPagingValue(int pagingValue) {
		this.pagingValue = pagingValue;
	}

	public PageProperties() {
		super();
	}
	
	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

	public String getCurrentList() {
		return currentList;
	}

	public void setCurrentList(String currentList) {
		this.currentList = currentList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isCompact() {
		return compact;
	}

	public void setCompact(boolean compact) {
		this.compact = compact;
	}

	@Override
	public String toString() {
		return "PageProperties [currentList=" + currentList + ", category=" + category + ", type=" + type + ", compact="
				+ compact + ", from=" + from + ", to=" + to + ", pagingValue=" + pagingValue + ", usageid=" + usageid
				+ "]";
	}


}
