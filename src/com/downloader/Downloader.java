package com.downloader;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.Iterator;
import java.util.Observable;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;










import org.apache.commons.io.FilenameUtils;

import us.monoid.web.Resty;

public class Downloader extends Observable {

	private File f;
	private static FileInfoInputInterface frame;

	public void saveMap(String url) {
		Resty r = new Resty();
		try {
			f = r.bytes(url).save(
					File.createTempFile("google", ".png", new File(
							"C:/Users/Bandara/Desktop/java downs")));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	public void setHttpProxySettings(String proxyUrl,String proxyPort, final String authUser, final String authPassword){
		Properties sysProperties=System.getProperties();
		
		sysProperties.setProperty("http.proxyHost", proxyUrl);
		sysProperties.setProperty("http.proxyPort", proxyPort);
		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);

		Authenticator.setDefault(
		  new Authenticator() {
		    public PasswordAuthentication getPasswordAuthentication() {
		      return new PasswordAuthentication(authUser, authPassword.toCharArray());
		    }
		  }
		);
	}
	public void setFtpProxySettings(String proxyUrl,String proxyPort,final String authUser,final String authPassword){
		Properties sysProperties=System.getProperties();
		
		sysProperties.setProperty("ftp.proxyHost", proxyUrl);
		sysProperties.setProperty("ftp.proxyPort", proxyPort);
		System.setProperty("ftp.proxyUser", authUser);
		System.setProperty("ftp.proxyPassword", authPassword);

		Authenticator.setDefault(
		  new Authenticator() {
		    public PasswordAuthentication getPasswordAuthentication() {
		      return new PasswordAuthentication(authUser, authPassword.toCharArray());
		    }
		  }
		);
	}
	public void setHttpsProxySettings(String proxyUrl,String proxyPort,final String authUser,final String authPassword){
		Properties sysProperties=System.getProperties();
		
		sysProperties.setProperty("https.proxyHost", proxyUrl);
		sysProperties.setProperty("https.proxyPort", proxyPort);
		System.setProperty("https.proxyUser", authUser);
		System.setProperty("https.proxyPassword", authPassword);

		Authenticator.setDefault(
		  new Authenticator() {
		    public PasswordAuthentication getPasswordAuthentication() {
		      return new PasswordAuthentication(authUser, authPassword.toCharArray());
		    }
		  }
		);
		
	}
	
	public void save(final String urlString, final String fileDestination)
			throws IOException {

		Thread downloaderThread = new Thread(new Runnable() {
			URL url = new URL(urlString);
			InputStream is = url.openStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			byte[] b = new byte[2048];
			int length = -2;
			long totalFileSize = Util.getFileSize(url);
			float currentFileSize = 0;
			long startTime;
			long endTime;
			int accLength;

			@Override
			public void run() {
				try {
					//startTime = System.currentTimeMillis();
					while ((length = is.read(b)) != -1) {
					//	if (accLength >= 10000) {
						//	endTime = System.currentTimeMillis();
						//	Util.getDownloadSpeed(length, startTime, endTime);
						//	accLength = 0;
					//	} else
					//		accLength += length;
						//System.out.println(length + "sadas" + accLength);
						os.write(b, 0, length);
						currentFileSize += length;

						frame.updateProgressbar((int) (currentFileSize
								/ totalFileSize * 100));
					//	if (accLength == 0) {
						//	startTime = System.currentTimeMillis();
						//}
					}
					is.close();
					os.close();
					byte[] response = os.toByteArray();
					FileOutputStream fos = new FileOutputStream(fileDestination
							+ "\\" + Util.getFileName(url));
					fos.write(response);
					fos.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		downloaderThread.setPriority(10);
		downloaderThread.start();
	}

	public static class Util {
		public static float getDownloadSpeed(float byteLength, long startTime,
				long endTime) {
			float speed = (float) byteLength / (endTime - startTime) * 1000
					/ 1024;
			System.out.println(speed);
			return speed;

		}

		public static String getFileName(URL filrUrl) {

			String[] arrayOfFields = filrUrl.getFile().trim().split("&");
			if (arrayOfFields != null) {
				for (int i = 0; i < arrayOfFields.length; i++) {

					if (arrayOfFields[i].startsWith("title=")) {
						return arrayOfFields[i].substring(6).replaceAll("[+]",
								" ");

					}
				}

			}
			return "untitled.File";

		}

		public static String getFileExtension(String url) {
			String extension = FilenameUtils.getExtension(url);
			if (extension != null) {

				return extension;
			}
			return "File";
		}

		public static long getFileSize(URL url) {
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("HEAD");
				conn.getInputStream();
				return conn.getContentLengthLong();
			} catch (IOException e) {
				return -1;
			} finally {
				conn.disconnect();
			}
		}

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new FileInfoInputInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
