package de.tobitheo.lgremote;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {

	JFrame frame;
	
	public Main() {

		// Create and set up the window.
		frame = new JFrame("GridBagLayoutDemo");
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
				// TODO Proper key press handling (aka send the command to the
				// TV)
				System.out.println(kb.getKey().toString());
			}
		};

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.ipady = 10;

		c.gridx = 0;
		c.gridy = 0;
		// pane.add(button, c);

		int i = 0;
		for (Key key : Key.values()) {
			button = new KeyButton(key);
			c.gridx = i % 3;
			c.gridy = i / 3;
			button.addActionListener(al);
			pane.add(button, c);
			++i;
		}
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
				new Main().show();
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
