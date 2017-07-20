package schedule;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.math.BigDecimal;

import schedule.Schedule;

/**
 * 
 * @author shakil
 *
 */
public class ScheduleDAO {
         public String name;
	private Connection myConn;
	
	public ScheduleDAO() throws Exception {
		
		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
		
		System.out.println("DB connection successful to: " + dburl);
	}
	
	public void deleteSchedule(int scheduleId) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from schedule where id=?");
			
			// set param
			myStmt.setInt(1, scheduleId);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
               
        public void value(String x)
        {
            name=x;
        }
	
	public void updateSchedule(Schedule theSchedule,String name) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update schedule"
					+ " set Time=?, Date=?, Task=?,username=?"
					+ " where id=?");
			
			// set params
			myStmt.setString(1, theSchedule.getDate());
			myStmt.setString(2, theSchedule.getTime());
			myStmt.setString(3, theSchedule.getTask());
                        myStmt.setString(4, name);
		
			myStmt.setInt(5, theSchedule.getId());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public void addSchedule(Schedule theSchedule,String name) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into schedule"
					+ " (Time, Date, Task,username)"
					+ " values (?, ?, ?,?)");
			
			// set params
			myStmt.setString(1, theSchedule.getDate());
			myStmt.setString(2, theSchedule.getTime());
			myStmt.setString(3, theSchedule.getTask());
			myStmt.setString(4, name);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	
	public List<Schedule> getAllSchedule() throws Exception { //sschedule all the item in the table 
		List<Schedule> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from schedule order by Date");
			
			while (myRs.next()) { //looping by the dates 
				Schedule tempSchedule = convertRowToEmployee(myRs);
				list.add(tempSchedule);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Schedule> searchSchedule(String lastName) throws Exception { 
		List<Schedule> list = new ArrayList<>(); //arrray list 

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			
                        //seacheing everything related to specific username .
			myStmt = myConn.prepareStatement("select * from schedule where username like ?  order by username");
			
			myStmt.setString(1, lastName);
			
			myRs = myStmt.executeQuery();
			
                        //looping until it find the specific username 
			while (myRs.next()) {
				Schedule tempSchedule = convertRowToEmployee(myRs); //converting row object 
				list.add(tempSchedule); //adding everything to the table 
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	private Schedule convertRowToEmployee(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id"); //getting the id
		String lastName = myRs.getString("Date");//getting the data 
		String firstName = myRs.getString("Time");//getting the Time 
		String Task = myRs.getString("Task");//getting the task 
		
                
		
		Schedule tempEmployee = new Schedule(id, lastName, firstName, Task);
		
		return tempEmployee;
	}

	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException { //exception

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}

	private void close(Statement myStmt) throws SQLException {
		close(null, myStmt, null);		
	}
	
	public static void main(String[] args) throws Exception {
		
		ScheduleDAO dao = new ScheduleDAO();
		System.out.println(dao.searchSchedule("thom"));

		System.out.println(dao.getAllSchedule());
	}

}
