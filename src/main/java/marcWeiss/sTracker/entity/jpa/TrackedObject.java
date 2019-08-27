package marcWeiss.sTracker.entity.jpa;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import marcWeiss.sTracker.component.helper.recurrenceIterator.RecurrenceIterator;
import marcWeiss.sTracker.component.helper.recurrenceIterator.RecurrrenceIteratorFactory;

@Entity
public class TrackedObject{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String name;
	private Integer value;
	private String category;
	private int complete;
	private int target;
	private boolean important;
	private LocalDate lastRecurrence;
	private LocalDate nextRecurrence;
	private int frequencyType;
	private String[] frequency;
	@OneToMany(fetch=FetchType.EAGER,mappedBy="trackedObject")
	private List<History> histories;
	@Transient
	private RecurrenceIterator recurrenceIterator;
	
	
	public TrackedObject() {
	}
	
	

	public TrackedObject(Long id, String username, String name, Integer value, String category, int complete, int target,
			boolean important, LocalDate lastRecurrence, LocalDate nextRecurrence, int frequencyType,
			String[] frequency) {
		
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.value = value;
		this.category = category;
		this.complete = complete;
		this.target = target;
		this.important = important;
		this.lastRecurrence = lastRecurrence;
		this.nextRecurrence = nextRecurrence;
		this.frequencyType = frequencyType;
		this.frequency = frequency;
		
	}

	public TrackedObject(TrackedObject trackedObject) {
		super();
		this.id				 = trackedObject.id;
		this.username		 =  trackedObject.username;
		this.name			 =  trackedObject.name;
		this.value			 =  trackedObject.value;
		this.category		 =  trackedObject.category;
		this.complete		 =  trackedObject.complete;
		this.target			 =  trackedObject.target;
		this.important		 =  trackedObject.important;
		this.lastRecurrence	 =  trackedObject.lastRecurrence;
		this.nextRecurrence	 =  trackedObject.nextRecurrence;
		this.frequencyType	 =  trackedObject.frequencyType;
		this.frequency		 =  trackedObject.frequency;
	}


	@PostLoad
	public void postLoad() throws Exception{
		setRecurrenceIterator(RecurrrenceIteratorFactory.getRecurrenceIterator(this));		
	}
	
	public List<History> getHistories() {
		return histories;
	}



	public void setHistories(List<History> histories) {
		this.histories = histories;
	}



	public int getComplete() {
		return complete;
	}


	public void setComplete(int complete) {
		this.complete = complete;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
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

	public String[] getFrequency() {
		return frequency;
	}


	public void setFrequency(String[] frequency) {
		this.frequency = frequency;
	}



	public int getFrequencyType() {
		return frequencyType;
	}



	public void setFrequencyType(int frequencyType) {
		this.frequencyType = frequencyType;
	}



	public RecurrenceIterator getRecurrenceIterator() {
		return recurrenceIterator;
	}



	public void setRecurrenceIterator(RecurrenceIterator recurrenceIterator) {
		this.recurrenceIterator = recurrenceIterator;
	}


	@Override
	public String toString() {
		return "TrackedObject [id=" + id + ", username=" + username + ", name=" + name + ", value=" + value
				+ ", category=" + category + ", complete=" + complete + ", target=" + target + ", important="
				+ important + ", lastRecurrence=" + lastRecurrence + ", nextRecurrence=" + nextRecurrence
				+ ", frequencyType=" + frequencyType + ", frequency=" + Arrays.toString(frequency)
				+ ", recurrenceIterator=" + recurrenceIterator + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public LocalDate getNextRecurrence() {
		return nextRecurrence;
	}
	
	public void setNextRecurrence(LocalDate nextRecurrence) {
		this.nextRecurrence = nextRecurrence;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackedObject other = (TrackedObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	


	public static void main(String[] args) {}

}
