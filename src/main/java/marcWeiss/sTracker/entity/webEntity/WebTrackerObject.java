package marcWeiss.sTracker.entity.webEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import marcWeiss.sTracker.entity.jpa.History;

public class WebTrackerObject{
	
	private Long id;
	@NotBlank
	private String name;
	@NotNull
	private Integer value;
	private String category;
	private int complete;
	private int target;
	private boolean important;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate lastRecurrence;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate nextRecurrence;
	private int frequencyType;
	private String[][] frequency;
	@Pattern(regexp="^\\d{1,}$")
	private String amount;
	private List<History> histories;

	@Override
	public String toString() {
		return "WebTrackerObject [id=" + id + ", name=" + name + ", value=" + value + ", category=" + category
				+ ", complete=" + complete + ", target=" + target + ", important=" + important + ", lastRecurrence="
				+ lastRecurrence + ", nextRecurrence=" + nextRecurrence + ", frequencyType=" + frequencyType
				+ ", frequency=" + Arrays.toString(frequency) + ", amount=" + amount + "]";
	}

	public WebTrackerObject() {
		super();
		setFrequencyType(0);
		frequency = new String[3][];
		frequency[0] = new String[]{"1","DAYS"};
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public LocalDate getLastRecurrence() {
		return lastRecurrence;
	}

	public void setLastRecurrence(LocalDate lastRecurrence) {
		this.lastRecurrence = lastRecurrence;
	}

	public LocalDate getNextRecurrence() {
		return nextRecurrence;
	}

	public void setNextRecurrence(LocalDate nextRecurrence) {
		this.nextRecurrence = nextRecurrence;
	}

	public int getFrequencyType() {
		return frequencyType;
	}

	public void setFrequencyType(int frequencyType) {
		this.frequencyType = frequencyType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String[][] getFrequency() {
		return frequency;
	}

	public void setFrequency(String[][] frequency) {
		this.frequency = frequency;
	}

	public List<History> getHistories() {
		return histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}
	
	public static class WebTrackerObjectList{
		
		List<WebTrackerObject> webTrackerObjects;
		String[] choosen;
		

		public String[] getChoosen() {
			return choosen;
		}

		public void setChoosen(String[] choosen) {
			this.choosen = choosen;
		}

		public WebTrackerObjectList() {
			super();
		}
		
		public WebTrackerObjectList(List<WebTrackerObject> webTrackerObjects) {
			super();
			this.webTrackerObjects = webTrackerObjects;
		}

		public List<WebTrackerObject> getWebTrackerObjects() {
			return webTrackerObjects;
		}

		public void setWebTrackerObjects(List<WebTrackerObject> webTrackerObjects) {
			this.webTrackerObjects = webTrackerObjects;
		}
		
		
	}
}


