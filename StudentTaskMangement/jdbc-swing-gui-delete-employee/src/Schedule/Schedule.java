package schedule;

import java.math.BigDecimal;

/**
 * 
 * @author shakil
 *
 */
public class Schedule {

	private int id;
	private String time;
	private String date;
	private String task;


	public Schedule(String time, String date, String task) {

		this(0, time, date, task);
	}
	
	public Schedule(int id, String time, String date, String task) {
		super();
		this.id = id;
		this.time = time;
		this.date = date;
		this.task = task;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setLastName(String lastName) {
		this.time = lastName;
	}

	public String getDate() {
		return date;
	}

	public void setFirstName(String firstName) {
		this.date = firstName;
	}

	public String getTask() {
		return task;
	}

	public void setEmail(String Task) {
		this.task = Task;
	}

	
         //Converting to int to string if any 
	@Override
	public String toString() {
		return String
				.format("Employee [id=%s, lastName=%s, firstName=%s, Task=%s]",
						id, time, date, task);
	}
	
	
		
}
