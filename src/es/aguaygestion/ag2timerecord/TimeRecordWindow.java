package es.aguaygestion.ag2timerecord;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Color;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
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
import javax.swing.SwingConstants;

import org.eclipse.wb.swing.FocusTraversalOnArray;

@SuppressWarnings("unused")
public class TimeRecordWindow {

	// DAO
	public MySQLAccess dao = new MySQLAccess();
	// WindowBuilder
	public JFrame frmAgtimerecordMarcajes;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	// System Tray
	final TrayIcon trayIcon = new TrayIcon(createImage(Global.appPath
			+ "/img/ag2TimeRecord128.png", "ag2TimeRecord"));
	final SystemTray tray = SystemTray.getSystemTray();

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
		frmAgtimerecordMarcajes.setFocusCycleRoot(false);
		frmAgtimerecordMarcajes.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmAgtimerecordMarcajes.setTitle("ag2TimeRecord | Marcajes");
		frmAgtimerecordMarcajes.setResizable(false);
		frmAgtimerecordMarcajes.setBounds(100, 100, 378, 176);
		// frmAgtimerecordMarcajes.setIconImage(Toolkit.getDefaultToolkit().getImage(Global.appPath
		// + "/img/ag2TimeRecord128.png"));
		frmAgtimerecordMarcajes.setIconImage(createImage(Global.appPath
				+ "/img/ag2TimeRecord128.png", "ag2TimeRecord"));

		JButton btnRegistrarMarcaje = new JButton("Registrar Marcaje");
		btnRegistrarMarcaje.setBounds(16, 106, 344, 38);
		btnRegistrarMarcaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dao.writeTimeRecord();
					Global.window.frmAgtimerecordMarcajes.setVisible(false);
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
		panel_1.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "C\u00F3digo",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		frmAgtimerecordMarcajes.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		final JComboBox comboBox = new JComboBox(Global.codesList.toArray());
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					TimeRecordCode item = (TimeRecordCode) comboBox
							.getSelectedItem();
					Global.timerecord_code_id = item.getId();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		comboBox.setBounds(6, 22, 220, 27);
		panel_1.add(comboBox);

		JPanel panel = new JPanel();
		panel.setBounds(6, 34, 124, 60);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null), "Tipo", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		frmAgtimerecordMarcajes.getContentPane().add(panel);
		panel.setLayout(null);

		// Timerecord_types radio buttons
		int x = 16;
		for (int i = 0; i < Global.typesList.size(); i++) {
			final JRadioButton rdbtn = new JRadioButton(Global.typesList.get(i)
					.getName());
			rdbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						int _id = 0;
						for (int j = 0; j < Global.typesList.size(); j++) {
							if (rdbtn.getText() == Global.typesList.get(j)
									.getName()) {
								_id = Global.typesList.get(j).getId();
								break;
							}
						}
						Global.timerecord_type_id = _id;
						System.out.println(Global.timerecord_type_id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			rdbtn.setBounds(x, 22, 39, 23);
			panel.add(rdbtn);
			buttonGroup.add(rdbtn);
			x += 51;
		}

		// JRadioButton rdbtnE = new JRadioButton("E");
		// rdbtnE.setBounds(16, 22, 39, 23);
		// panel.add(rdbtnE);
		// buttonGroup.add(rdbtnE);
		//
		// JRadioButton rdbtnS = new JRadioButton("S");
		// rdbtnS.setBounds(67, 22, 39, 23);
		// panel.add(rdbtnS);
		// buttonGroup.add(rdbtnS);

		// JLabel lblUsernameWorkerlastname = new
		// JLabel("Rodr\u00EDguez Maldonado, N\u00E9stor Fabi\u00E1n (nestor)");
		JLabel lblUsernameWorkerlastname = new JLabel(Global.worker_name + " ("
				+ Global.current_user + ")");
		lblUsernameWorkerlastname.setBounds(6, 6, 366, 16);
		lblUsernameWorkerlastname.setHorizontalAlignment(SwingConstants.CENTER);
		frmAgtimerecordMarcajes.getContentPane().add(lblUsernameWorkerlastname);
		frmAgtimerecordMarcajes.getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] {
						lblUsernameWorkerlastname, panel, panel_1, comboBox,
						btnRegistrarMarcaje }));
		
		// Display trayIcon
		trayIcon.setImageAutoSize(true);
		try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
		trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				Global.window.frmAgtimerecordMarcajes.setVisible(true);
            }
        });
	}

	// Obtain the image URL
	protected static Image createImage(String path, String description) {
		if (!Global.fileExists(path)) {
			return null;
		} else {
			return (new ImageIcon(path, description)).getImage();
		}
	}
}
