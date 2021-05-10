package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{

	public enum EventType{ //definisco costanti: nuovo_cliente=0 ritorno_auto=1 ....
		NUOVO_CLIENTE,
		RITORNO_AUTO
	}
	private LocalTime time; //instante di tempo in cui avviene questo evento
	private EventType type;
	//posso avere type=NUOVO_CLIENTE o type=RITORNO_AUTO che equivale a type=0 e type=1

	public Event(LocalTime time, EventType type) {
		this.time = time;
		this.type = type;
	}
	
	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	
}
