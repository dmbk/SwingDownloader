package com.downloader;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.CardLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Color;
import javax.swing.JProgressBar;

public class FileInfoInputInterface extends JFrame {
	private Downloader down;

	private JPanel contentPane;
	private JTextField txtUrl;
	private JTextField txtDest;
	private JButton btnDownload;
	private JLabel lblFileInfo;
	private JProgressBar progressBar;
	private JButton btnAdvanced;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public FileInfoInputInterface() {
		this.down = new Downloader();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					down.save(txtUrl.getText(), txtDest.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		txtUrl = new JTextField();
		txtUrl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String urlField = txtUrl.getText();

				try {
					URL url = new URL(urlField);
					String fileName = Downloader.Util.getFileName(url);
					long fileSize = Downloader.Util.getFileSize(url);
					String displayable = lblFileInfo.getText() + " " + fileName
							+ "   ";
					if (!"".equals(fileName)) {
						lblFileInfo.setText(displayable);

					}
					if (fileSize != -1) {
						lblFileInfo.setText(displayable + "" + fileSize + " "
								+ "bytes");

					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		txtUrl.setBounds(126, 113, 224, 20);
		contentPane.add(txtUrl);
		txtUrl.setColumns(10);

		JLabel lblEnterTheUrl = new JLabel("Enter the url:");
		lblEnterTheUrl.setBounds(50, 116, 80, 14);
		contentPane.add(lblEnterTheUrl);

		JLabel lblDownloadManager = new JLabel("Download Manager");
		lblDownloadManager.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblDownloadManager.setBounds(99, 11, 237, 59);
		contentPane.add(lblDownloadManager);

		btnDownload.setBounds(230, 175, 119, 23);
		contentPane.add(btnDownload);

		txtDest = new JTextField();
		txtDest.setBounds(126, 144, 224, 20);
		contentPane.add(txtDest);
		txtDest.setColumns(10);

		JLabel lblFileDestination = new JLabel("File Destination:");
		lblFileDestination.setBounds(36, 147, 88, 14);
		contentPane.add(lblFileDestination);

		lblFileInfo = new JLabel("File Info:");
		lblFileInfo.setBackground(Color.GRAY);
		lblFileInfo.setBounds(50, 205, 335, 34);
		contentPane.add(lblFileInfo);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int val = chooser.showSaveDialog(null);
				if (val == chooser.APPROVE_OPTION) {
					txtDest.setText(chooser.getSelectedFile().getAbsolutePath()
							.toString());

				}
			}
		});
		btnBrowse.setBounds(360, 144, 82, 20);
		contentPane.add(btnBrowse);

		progressBar = new JProgressBar();
		progressBar.setBounds(50, 286, 359, 34);
		contentPane.add(progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);

		JLabel lblProgress = new JLabel("Progress:");
		lblProgress.setBounds(49, 261, 81, 14);
		contentPane.add(lblProgress);

		btnAdvanced = new JButton("Advanced");
		btnAdvanced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SettingsWindow frame = new SettingsWindow(down);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnAdvanced.setBounds(320, 235, 89, 23);
		contentPane.add(btnAdvanced);

	}

	public void updateProgressbar(int progValue) {

		this.progressBar.setValue(progValue);
		this.progressBar.repaint();
	}
}
