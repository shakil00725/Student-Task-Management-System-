package schedule;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import schedule.Schedule;
import schedule.ScheduleDAO;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class ScheduleDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField TaskTextField;
	private JTextField salaryTextField;

	private ScheduleDAO employeeDAO;
       

	private ScheduleSearchApp employeeSearchApp;

	private Schedule previousEmployee = null;
	private boolean updateMode = false;
        public String name;

	public ScheduleDialog(ScheduleSearchApp theEmployeeSearchApp,
			ScheduleDAO theEmployeeDAO, Schedule thePreviousEmployee, boolean theUpdateMode,String x) {
		this();
		employeeDAO = theEmployeeDAO;
		employeeSearchApp = theEmployeeSearchApp;

		previousEmployee = thePreviousEmployee;
		
		updateMode = theUpdateMode;
                name=x;

                //when update button is clickedd 
		if (updateMode) {
			setTitle("Update Schedule");
			
			populateGui(previousEmployee); //update gui 
		}
	}
        // for gui show case the txtfield is setting text to the update gui
	private void populateGui(Schedule theEmployee) {

		firstNameTextField.setText(theEmployee.getDate());
		lastNameTextField.setText(theEmployee.getTime());
		TaskTextField.setText(theEmployee.getTask());
		
	}
     
	public ScheduleDialog(ScheduleSearchApp theEmployeeSearchApp,
			ScheduleDAO theEmployeeDAO,String x) {
		this(theEmployeeSearchApp, theEmployeeDAO, null, false,x);
	}

	/**
	 * Create the dialog.
	 */
	public ScheduleDialog() {
		setTitle("Add Schedule ");   //Add schedule gui 
		setBounds(100, 100, 450, 234);      //bounds 
		getContentPane().setLayout(new BorderLayout()); //layout use borderlayout 
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));
                //label and textfield 
		{
			JLabel lblFirstName = new JLabel("Time");
			contentPanel.add(lblFirstName, "2, 2, right, default");
		}
		{
			firstNameTextField = new JTextField();
			contentPanel.add(firstNameTextField, "4, 2, fill, default");
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Date");
			contentPanel.add(lblLastName, "2, 4, right, default");
		}
		{
			lastNameTextField = new JTextField();
			contentPanel.add(lastNameTextField, "4, 4, fill, default");
			lastNameTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Schedule");
			contentPanel.add(lblNewLabel, "2, 6, right, default");
		}
		{
			TaskTextField = new JTextField();
			contentPanel.add(TaskTextField, "4, 6, fill, default");
			TaskTextField.setColumns(10);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveEmployee();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected BigDecimal convertStringToBigDecimal(String salaryStr) {

		BigDecimal result = null;

		try {
			double salaryDouble = Double.parseDouble(salaryStr);

			result = BigDecimal.valueOf(salaryDouble);
		} catch (Exception exc) {
			System.out.println("Invalid value. Defaulting to 0.0");
			result = BigDecimal.valueOf(0.0);
		}

		return result;
	}

	protected void saveEmployee() {

		//save the text when add and update button clicked 
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String Task = TaskTextField.getText();

	
	

		Schedule tempSchedule = null;

		if (updateMode) {
			tempSchedule = previousEmployee;
			
			tempSchedule.setLastName(lastName);
			tempSchedule.setFirstName(firstName);
			tempSchedule.setEmail(Task);
			
			
		} else {
			tempSchedule = new Schedule(lastName, firstName, Task);
		}

		try {
			// save to the database
			if (updateMode) {
				employeeDAO.updateSchedule(tempSchedule,name);
			} else {
				employeeDAO.addSchedule(tempSchedule,name);
			}

			// close dialog
			setVisible(false);
			dispose();

			

			// show success message
			JOptionPane.showMessageDialog(employeeSearchApp,
					"Schedule saved succesfully.", "Schedule Saved",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(employeeSearchApp,
					"Error saving Schedule: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
