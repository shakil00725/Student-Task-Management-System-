package schedule;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import schedule.Schedule;

class ScheduleTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int TIME_COL = 0; //table 1st element 
	private static final int DATE_COL = 1; //2nd element 
	private static final int TASK_COL = 2; //3rd element 


	private String[] columnNames = { "Time", "Date", "Schedule"}; //table name 
	private List<Schedule> schedule;

	public ScheduleTableModel(List<Schedule> theEmployees) {
		schedule = theEmployees;
	}

        //getcolumn counted 
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
        //row count 
	@Override
	public int getRowCount() {
		return schedule.size();
	}
        //name of the column 
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
        
        //get the value from the column 
	@Override
	public Object getValueAt(int row, int col) {

		Schedule tempSchedule = schedule.get(row);

		switch (col) {
		case TIME_COL:
			return tempSchedule.getTime();  //time column taken 
		case DATE_COL:
			return tempSchedule.getDate();   //date column taken 
		case TASK_COL:
			return tempSchedule.getTask();   //task taken
		
		case OBJECT_COL:
			return tempSchedule;
		default:
			return tempSchedule.getTime();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
