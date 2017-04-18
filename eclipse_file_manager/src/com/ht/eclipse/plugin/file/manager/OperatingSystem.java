/**
 * File Name:OperatingSystem.java
 * Package Name:com.ht.eclipse.plugin.file.manager
 * Date:2017-2-8ÉÏÎç10:54:48
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * ClassName:OperatingSystem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 ÉÏÎç10:54:48 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public enum OperatingSystem {
	INSTANCE;

	public static final String WINDOWS = "win32";
	public static final String LINUX = "linux";
	public static final String MACOSX = "macosx";

	private String os;

	private OperatingSystem() {
		os = System.getProperty("osgi.os");
	}

	/**
     * @return the systemBrowser
     */
    public String getSystemBrowser() {
    	String systemBrowser = null;
    	if (isWindows()) {
			systemBrowser = Constants.EXPLORER;
		} else if (isLinux()) {
			IPreferenceStore store = Activator.getDefault()
			        .getPreferenceStore();
			systemBrowser = store
			        .getString(Constants.LINUX_FILE_MANAGER);
			if (systemBrowser.equals(Constants.OTHER)) {
				systemBrowser = store
				        .getString(Constants.LINUX_FILE_MANAGER_PATH);
			}
		} else if (isMacOSX()) {
			systemBrowser = Constants.FINDER;
		}
    	return systemBrowser;
    }
    
    public String getOS() {
    	return os;
    }

    public boolean isWindows() {
    	return WINDOWS.equalsIgnoreCase(os);
    }
    
    public boolean isMacOSX() {
    	return MACOSX.equalsIgnoreCase(this.os);
    }
    
    public boolean isLinux() {
    	return LINUX.equalsIgnoreCase(os);
    }
}

