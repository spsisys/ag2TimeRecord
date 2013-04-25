package es.aguaygestion.ag2timerecord;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class TimeRecordWindow {

	public JFrame frmAgtimerecordMarcajes;

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
	}

}
