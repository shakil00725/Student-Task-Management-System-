package schedule;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import java.util.List;
import javax.swing.JComponent;

import schedule.Schedule;
import schedule.ScheduleDAO;

public class ScheduleSearchApp extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;

	private ScheduleDAO employeeDAO;
	private JPanel panel_1;
	private JButton btnAddEmployee;
	private JButton btnUpdateEmployee;
	private JButton btnDeleteEmployee;
	public String name;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheduleSearchApp frame = new ScheduleSearchApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void value(String x)
        {
            name=x;
            System.out.println(name);
        }
	public ScheduleSearchApp() {
		
		// create the DAO
		try {
			employeeDAO = new ScheduleDAO();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		setTitle("Schedule");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 this.setSize(854, 604);
                 this.setResizable(false);

	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
	
		
		lastNameTextField = new JTextField();
                
                lastNameTextField.setText(name);
		lastNameTextField.setColumns(10);
		
		btnSearch = new JButton("View");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get last name from the text field

				// Call DAO and get schedule for the last name

				// If last name is empty, then get all schedule

				// Print out schedule				
				
				try {
					String lastName = name;
                                        System.out.println(lastName);

					List<Schedule> schedule = null;

					if (lastName != null && lastName.trim().length() > 0) {
						schedule = employeeDAO.searchSchedule(lastName);
					} else {
						schedule = employeeDAO.getAllSchedule();
					}
					
					// create the model and update the "table"
					ScheduleTableModel model = new ScheduleTableModel(schedule);
					
					table.setModel(model);
					
					/*
					for (Schedule temp : schedule) {
						System.out.println(temp);
					}
					*/
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(ScheduleSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
			}
		});
		panel.add(btnSearch);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btnAddEmployee = new JButton("Add Schedule");
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create dialog
				ScheduleDialog dialog = new ScheduleDialog(ScheduleSearchApp.this, employeeDAO,name);

				// show dialog
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnAddEmployee);
		
		btnUpdateEmployee = new JButton("Update Schedule");
		btnUpdateEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// get the selected item
				int row = table.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(ScheduleSearchApp.this, "You must select an employee", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current employee
				Schedule tempSchedule = (Schedule) table.getValueAt(row, ScheduleTableModel.OBJECT_COL);
				
				// create dialog
				ScheduleDialog dialog = new ScheduleDialog(ScheduleSearchApp.this, employeeDAO, 
															tempSchedule, true,name);

				// show dialog
				dialog.setVisible(true);
			
			}
		});
		panel_1.add(btnUpdateEmployee);
		
		btnDeleteEmployee = new JButton("Delete Schedule");
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					// get the selected row
					int row = table.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(ScheduleSearchApp.this, 
								"You must select an Schedule", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(ScheduleSearchApp.this, "Delete this Schedule?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current employee
					Schedule tempSchedule = (Schedule) table.getValueAt(row, ScheduleTableModel.OBJECT_COL);

					// delete the employee
					employeeDAO.deleteSchedule(tempSchedule.getId());

					// refresh GUI
					refreshEmployeesView();

					// show success message
					JOptionPane.showMessageDialog(ScheduleSearchApp.this,
							"Schedule deleted succesfully.", "Schedule Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(ScheduleSearchApp.this,
							"Error deleting Schedule: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				
			}
		});
		panel_1.add(btnDeleteEmployee);
	}

	public void refreshEmployeesView() {

		try {
			List<Schedule> schedule = employeeDAO.getAllSchedule();

			// create the model and update the "table"
			ScheduleTableModel model = new ScheduleTableModel(schedule);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
