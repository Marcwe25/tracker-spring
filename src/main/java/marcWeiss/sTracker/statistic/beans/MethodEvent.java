package marcWeiss.sTracker.statistic.beans;

import java.time.LocalDateTime;

public class MethodEvent {

	MethodBean methodBean;
	LocalDateTime dateTime;
	long duration;
	String SessionId;
	String userid;
	String exceptionType;
	String exceptionMessage;
	String exceptionCause;
	
	public MethodEvent() {
		super();
	}
	public MethodBean getMethodBean() {
		return methodBean;
	}
	public void setMethodBean(MethodBean methodBean) {
		this.methodBean = methodBean;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getSessionId() {
		return SessionId;
	}
	public void setSessionId(String sessionId) {
		SessionId = sessionId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public String getExceptionCause() {
		return exceptionCause;
	}
	public void setExceptionCause(String exceptionCause) {
		this.exceptionCause = exceptionCause;
	}
	@Override
	public String toString() {
		return "MethodEvent [methodBean=" + methodBean + ", dateTime=" + dateTime + ", duration=" + duration
				+ ", SessionId=" + SessionId + ", userid=" + userid + ", exceptionType=" + exceptionType
				+ ", exceptionMessage=" + exceptionMessage + ", exceptionCause=" + exceptionCause + "]";
	}
	
	
}
