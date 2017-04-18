/**
 * File Name:Bootstrap.java
 * Package Name:com.ht.eclipse.plugin.file.manager
 * Date:2017-2-8上午10:54:29
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * ClassName:Bootstrap <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 上午10:54:29 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Bootstrap extends AbstractPreferenceInitializer {

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 * Date: 2017-2-8 上午11:08:15
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(Constants.LINUX_FILE_MANAGER, OperatingSystem.INSTANCE.isLinux() ? Utils.detectLinuxSystemBrowser() : Constants.NAUTILUS);
		store.setDefault(Constants.LINUX_FILE_MANAGER_PATH, "");
	}

}


