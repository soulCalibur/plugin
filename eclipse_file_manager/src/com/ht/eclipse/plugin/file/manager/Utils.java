/**
 * File Name:Utils.java
 * Package Name:com.ht.eclipse.plugin.file.manager
 * Date:2017-2-8上午10:55:12
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager;

import java.io.File;
import java.io.IOException;

import com.ht.common.util.IOUtils;

/**
 * ClassName:Utils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 上午10:55:12 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Utils {


	/**
	 * detectLinuxSystemBrowser:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:10:44
	 * @author Administrator
	 * @return
	 */
	public static String detectLinuxSystemBrowser() {
		String result = executeCommand("which nautilus");
		if (result == null || result.trim().equals("")) {
			result = executeCommand("which dolphin");
		}
		if (result == null || result.trim().equals("")) {
			result = executeCommand("which thunar");
		}
		if (result == null || result.trim().equals("")) {
			result = executeCommand("which pcmanfm");
		}
		if (result == null || result.trim().equals("")) {
			result = executeCommand("which rox");
		}
		if (result == null || result.trim().equals("")) {
			result = executeCommand("xdg-open");
		}
		if (result == null || result.trim().equals("")) {
			result = Constants.OTHER;
		}
		String[] pathnames = result.split(File.separator);
		return pathnames[pathnames.length - 1];
	}

	/**
	 * executeCommand:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:10:49
	 * @author Administrator
	 * @param command
	 * @return
	 */
	public static String executeCommand(String command) {
		String stdout = null;
		try {
			Process process = Runtime.getRuntime().exec(command);
			stdout = IOUtils.toString(process.getInputStream());
			stdout = stdout.trim();
			stdout = stdout.replace("\n", "");
			stdout = stdout.replace("\r", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stdout;
	}

}

