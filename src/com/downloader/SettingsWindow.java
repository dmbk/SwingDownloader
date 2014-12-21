package com.downloader;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtHost;
	private JTextField txtPort;
	private JTextField txtUsername;
	private JPasswordField pswdProxy;
	private Downloader downloader;
	private JCheckBox chkHttp;
	private JCheckBox chkFtp;
	private JCheckBox chkHttps;

	/**
	 * Create the frame.
	 */
	public SettingsWindow(Downloader down) {
		this.downloader = down;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(26, 49, 568, 276);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Proxy", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Host");
		lblNewLabel.setBounds(55, 44, 46, 14);
		panel.add(lblNewLabel);

		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(182, 44, 46, 14);
		panel.add(lblPort);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(309, 44, 75, 14);
		panel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(450, 44, 75, 14);
		panel.add(lblPassword);

		txtHost = new JTextField();
		txtHost.setBounds(10, 69, 132, 20);
		panel.add(txtHost);
		txtHost.setColumns(10);

		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(152, 69, 86, 20);
		panel.add(txtPort);

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(264, 69, 133, 20);
		panel.add(txtUsername);

		chkHttp = new JCheckBox("http");
		chkHttp.setBounds(45, 110, 97, 23);
		panel.add(chkHttp);

		chkFtp = new JCheckBox("ftp");
		chkFtp.setBounds(152, 110, 97, 23);
		panel.add(chkFtp);

		chkHttps = new JCheckBox("https");
		chkHttps.setBounds(242, 110, 97, 23);
		panel.add(chkHttps);

		pswdProxy = new JPasswordField();
		pswdProxy.setBounds(407, 69, 146, 20);
		panel.add(pswdProxy);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String host = txtHost.getText().trim();
				String port = txtPort.getText().trim();
				String username = txtUsername.getText().trim();
				String password = pswdProxy.getText().trim();

				if (chkHttp.isSelected()) {
					downloader.setHttpProxySettings(host, port, username,
							password);

				}
				if (chkFtp.isSelected()) {
					downloader.setFtpProxySettings(host, port, username,
							password);

				}
				if (chkHttps.isSelected()) {
					downloader.setHttpsProxySettings(host, port, username,
							password);

				}
				
			}
		});
		btnSave.setBounds(436, 190, 89, 23);
		panel.add(btnSave);
	}
}
