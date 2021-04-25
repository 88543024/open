package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import play.db.jpa.Model;

@Entity
@Table(name="T_ALARM")
@AccessType("field")
public class Alarm extends Model{
	/** IP或主机名 */
	private String hostOrIp;
	/** 告警级别 */
	private String alarmLevel;
	/** 告警时间 */
	private String alarmTime;
	/** 告警信息 */
	private String alarmMessage;
	
	public String getHostOrIp() {
		return hostOrIp;
	}
	public void setHostOrIp(String hostOrIp) {
		this.hostOrIp = hostOrIp;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getAlarmMessage() {
		return alarmMessage;
	}
	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}
	
}
