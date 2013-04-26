package es.aguaygestion.ag2timerecord;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

@SuppressWarnings("unused")
public class TimeRecordWindow {

	public JFrame frmAgtimerecordMarcajes;
	public MySQLAccess dao = new MySQLAccess();
	private final ButtonGroup buttonGroup = new ButtonGroup();

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
		frmAgtimerecordMarcajes.setBounds(100, 100, 378, 176);
		
		JButton btnRegistrarMarcaje = new JButton("Registrar Marcaje");
		btnRegistrarMarcaje.setBounds(16, 106, 344, 38);
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
		
		frmAgtimerecordMarcajes.getContentPane().setLayout(null);
		frmAgtimerecordMarcajes.getContentPane().add(btnRegistrarMarcaje);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(140, 34, 232, 60);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "C\u00F3digo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frmAgtimerecordMarcajes.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(6, 22, 220, 27);
		panel_1.add(comboBox);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 34, 124, 60);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Tipo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frmAgtimerecordMarcajes.getContentPane().add(panel);
		panel.setLayout(null);
		
		JRadioButton rdbtnE = new JRadioButton("E");
		rdbtnE.setBounds(16, 22, 39, 23);
		panel.add(rdbtnE);
		buttonGroup.add(rdbtnE);
		
		JRadioButton rdbtnS = new JRadioButton("S");
		rdbtnS.setBounds(67, 22, 39, 23);
		panel.add(rdbtnS);
		buttonGroup.add(rdbtnS);
		
		JLabel lblUsernameWorkerlastname = new JLabel("Rodr\u00EDguez Maldonado, N\u00E9stor Fabi\u00E1n (nestor)");
		lblUsernameWorkerlastname.setBounds(6, 6, 366, 16);
		lblUsernameWorkerlastname.setHorizontalAlignment(SwingConstants.CENTER);
		frmAgtimerecordMarcajes.getContentPane().add(lblUsernameWorkerlastname);
		frmAgtimerecordMarcajes.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblUsernameWorkerlastname, panel, rdbtnE, rdbtnS, panel_1, comboBox, btnRegistrarMarcaje}));
	}
}
