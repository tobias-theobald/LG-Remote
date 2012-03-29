package de.tobitheo.lgremote;

import java.util.prefs.Preferences;

public class PreferencesManager {
	
	Preferences prefs = Preferences.userNodeForPackage(this.getClass().getPackage().getClass());
	
	public String getAuthKeyOfHost (String host) {
		return prefs.get("AuthKey" + host, null);
	}
	
	public void setAuthKeyOfHost (String host, String authKey) {
		prefs.put("AuthKey" + host, authKey);
	}

}
