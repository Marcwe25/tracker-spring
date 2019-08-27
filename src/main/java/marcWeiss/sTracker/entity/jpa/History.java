package marcWeiss.sTracker.entity.jpa;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class History {

	@Id
	@GeneratedValue
	long id;
	@ManyToOne	
	TrackedObject trackedObject;
	@DateTimeFormat(pattern="YYYY-MM-DD")
	LocalDateTime localDateTime;
	int value;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public TrackedObject getTrackedObject() {
		return trackedObject;
	}
	public void setTrackedObject(TrackedObject trackedObject) {
		this.trackedObject = trackedObject;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "History [id=" + id + ", trackedObject=" + trackedObject + ", localDateTime=" + localDateTime
				+ ", value=" + value + "]";
	}
	
	
	
}
