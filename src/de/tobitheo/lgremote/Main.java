package de.tobitheo.lgremote;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.http.client.ClientProtocolException;

public class Main {

	JFrame frame;
	private final Connection conn;
	
	public Main(final Connection conn) {
		this.conn = conn;

		// Create and set up the window.
		frame = new JFrame("LG Remote");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		Container pane = frame.getContentPane();

		JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object o = arg0.getSource();
				if (!(o instanceof KeyButton))
					throw new IllegalStateException(
							"That was not a key button. This ActionListener ist only for key buttons.");
				KeyButton kb = (KeyButton) o;
				try {
					conn.sendKeyCode(kb.getKey());
				} catch (Exception e) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					e.printStackTrace(new PrintStream(baos));
					JOptionPane.showMessageDialog(null,
						    "Exception: " + e.getMessage() + "\n" + baos.toString(),
						    "Exception while sending key code",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		};

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 10;
		c.gridheight = 1;
		c.insets = new Insets(2, 2, 2, 2);
		c.anchor = GridBagConstraints.CENTER;
		
		
		
		button = new KeyButton(Key.POWER, "Power");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 12;
		button.addActionListener(al);
		pane.add(button, c);


		c.gridy++;

		button = new KeyButton(Key.ENERGY, "Energy");
		c.gridx = 0;
		c.gridwidth = 3;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.AV_MODE, "AV Mode");
		c.gridx = 3;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.INPUT, "Input");
		c.gridx = 6;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.TV_RADIO, "TV/Radio");
		c.gridx = 9;
		button.addActionListener(al);
		pane.add(button, c);

		
		c.gridy++;

		button = new KeyButton(Key.NUM_1, "1");
		c.gridx = 0;
		c.gridwidth = 4;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.NUM_2, "2");
		c.gridx = 4;
		c.weightx = 1;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.NUM_3, "3");
		c.gridx = 8;
		c.weightx = 0;
		button.addActionListener(al);
		pane.add(button, c);

		
		c.gridy++;
		
		button = new KeyButton(Key.NUM_4, "4");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);
		
		button = new KeyButton(Key.NUM_5, "5");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.NUM_6, "6");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		

		
		c.gridy++;

		button = new KeyButton(Key.NUM_7, "7");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.NUM_8, "8");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);
		
		button = new KeyButton(Key.NUM_9, "9");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);

		
		c.gridy++;

		button = new KeyButton(Key.LIST, "List");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.NUM_0, "0");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.QUICK_VIEW, "Q.View");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);

		
		c.gridy++;

		button = new KeyButton(Key.VOLUME_UP, "Vol+");
		c.gridx = 0;
		c.gridwidth = 4;
		c.gridheight = 3;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.CHANNEL_UP, "Prog+");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.FAV_MARK_CHAR_NUM, "Mark/Fav");
		c.gridx = 4;
		c.gridheight = 2;
		button.addActionListener(al);
		pane.add(button, c);

		
		c.gridy += 2;
		
		button = new KeyButton(Key.THREE_D, "3D");
		button.addActionListener(al);
		pane.add(button, c);
		
		
		c.gridy++;
		
		button = new KeyButton(Key.VOLUME_DOWN, "Vol-");
		c.gridx = 0;
		c.gridheight = 3;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.CHANNEL_DOWN, "Prog-");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		

		
		c.gridy++;

		button = new KeyButton(Key.MUTE_DELETE, "Mute/Del");
		c.gridx = 4;
		c.gridheight = 2;
		button.addActionListener(al);
		pane.add(button, c);

		
		c.gridy += 2;
		
		button = new KeyButton(Key.PREMIUM, "Premium");
		c.gridx = 0;
		c.gridheight = 1;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.HOME, "Home");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.AT, "@");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);

		
		c.gridy++;
		
		button = new KeyButton(Key.KEYPAD_UP, "^");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);
		
		
		c.gridy++;

		button = new KeyButton(Key.KEYPAD_LEFT, "<");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);		
		
		button = new KeyButton(Key.KEYPAD_ENTER, "OK");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);		
		
		button = new KeyButton(Key.KEYPAD_RIGHT, ">");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		
		

		c.gridy++;
		
		button = new KeyButton(Key.KEYPAD_DOWN, "v");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);
		

		c.gridy++;
		
		button = new KeyButton(Key.BACK, "Back");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);
		
		button = new KeyButton(Key.QUICK_MENU_3D_OPTIONS, "Q.Menu");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);

		button = new KeyButton(Key.EXIT, "Exit");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		

		
		c.gridy++;
		
		button = new KeyButton(Key.RED, "Red");
		c.gridx = 0;
		c.gridwidth = 3;
		
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.GREEN, "Green");
		c.gridx = 3;
		button.addActionListener(al);
		pane.add(button, c);		
		
		button = new KeyButton(Key.YELLOW, "Yellow");
		c.gridx = 6;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.BLUE, "Blue");
		c.gridx = 9;
		button.addActionListener(al);
		pane.add(button, c);		
		
		
		c.gridy++;

		button = new KeyButton(Key.TEXT, "Text");
		c.gridx = 0;
		c.gridwidth = 4;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.TEXT_OPTIONS, "T.Options");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.SUBTITLE, "Subtitle");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		

		
		c.gridy++;
		
		button = new KeyButton(Key.STOP_LIVE_TV, "Stop");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.PLAY, "Play");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.PAUSE, "Pause");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		

		
		c.gridy++;
		
		button = new KeyButton(Key.REWIND, "Rewind");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.FAST_FORWARD, "F.Forward");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.RECORD, "Rec.");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);		

		
		c.gridy++;
		
		button = new KeyButton(Key.GUIDE, "Guide");
		c.gridx = 0;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.INFO, "Info");
		c.gridx = 4;
		button.addActionListener(al);
		pane.add(button, c);		

		button = new KeyButton(Key.RATIO, "Ratio");
		c.gridx = 8;
		button.addActionListener(al);
		pane.add(button, c);	
		
		
	}

	private void show() {
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Connection conn = new Connection();
				String host = null;
				PreferencesManager prefs = new PreferencesManager();
				try {
					host = conn.mDnsDiscovery();
				} catch (SocketTimeoutException e) {
					JOptionPane.showMessageDialog(null,
							"The TV dicovery via mDNS timed out",
							"Discovery Timeout",
							JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				} catch (IOException e) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					e.printStackTrace(new PrintStream(baos));
					JOptionPane.showMessageDialog(null,
						    "Exception: " + e.getMessage() + "\n" + baos.toString(),
						    "Exception while mDNS discovery",
						    JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
				if (host == null)
					return;
				String authKey = prefs.getAuthKeyOfHost(host);
				if (authKey == null) {
					try {
						conn.showAuthKey();
					} catch (Exception e) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						e.printStackTrace(new PrintStream(baos));
						JOptionPane.showMessageDialog(null,
							    "Exception: " + e.getMessage() + "\n" + baos.toString(),
							    "Exception while authentication",
							    JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
					authKey = (String)JOptionPane.showInputDialog(
		                    null,
		                    "Unknown host, enter Key displayed on the TV",
		                    "Enter Key",
		                    JOptionPane.QUESTION_MESSAGE);
					if (authKey == null)
						System.exit(0);
					prefs.setAuthKeyOfHost(host, authKey);
				}
				try {
					conn.authenticate(authKey);
				} catch (Exception e) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					e.printStackTrace(new PrintStream(baos));
					JOptionPane.showMessageDialog(null,
						    "Exception: " + e.getMessage() + "\n" + baos.toString(),
						    "Exception while authentication",
						    JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
					
				new Main(conn).show();
			}
		});
	}

	@SuppressWarnings("unused")
	private class KeyButton extends JButton {

		private static final long serialVersionUID = -5785177244336835106L;
		private final Key key;

		public KeyButton(Key key, String text, Icon icon) {
			super(text, icon);
			this.key = key;
		}

		public KeyButton(Key key, String text) {
			super(text);
			this.key = key;
		}

		public KeyButton(Key key) {
			super(key.toString());
			this.key = key;
		}

		public Key getKey() {
			return key;
		}

	}
}
