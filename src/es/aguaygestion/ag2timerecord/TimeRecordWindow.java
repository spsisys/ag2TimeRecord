package es.aguaygestion.ag2timerecord;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import es.aguaygestion.ag2timerecord.MySQLAccess;

public class TimeRecordWindow {

	public JFrame frmAgtimerecordMarcajes;
	public MySQLAccess dao = new MySQLAccess();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeRecordWindow window = new TimeRecordWindow();
					window.frmAgtimerecordMarcajes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimeRecordWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmAgtimerecordMarcajes = new JFrame();
		frmAgtimerecordMarcajes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAgtimerecordMarcajes.setTitle("ag2TimeRecord | Marcajes");
		frmAgtimerecordMarcajes.setResizable(false);
		frmAgtimerecordMarcajes.setBounds(100, 100, 450, 300);
		frmAgtimerecordMarcajes.getContentPane().setLayout(null);
		
		JButton btnRegistrarMarcaje = new JButton("Registrar Marcaje");
		btnRegistrarMarcaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dao.writeTimeRecord();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnRegistrarMarcaje.setBounds(6, 6, 152, 29);
		frmAgtimerecordMarcajes.getContentPane().add(btnRegistrarMarcaje);
	}

}
