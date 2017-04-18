/**
 * File Name:Messages.java
 * Package Name:com.ht.eclipse.plugin.file.manager
 * Date:2017-2-8ÉÏÎç10:55:42
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager;

import org.eclipse.osgi.util.NLS;

/**
 * ClassName:Messages <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 ÉÏÎç10:55:42 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "openexplorer.util.messages";//$NON-NLS-1$
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String MAC_OS_X;
	public static String WINDOWS;
	public static String LINUX;

	public static String Finder;
	public static String Windows_Explorer;

	public static String Open_Explorer_Version;
	public static String Expand_Instruction;

	public static String Nautilus;
	public static String Dolphin;
	public static String Thunar;
	public static String PCManFM;
	public static String ROX;
	public static String Xdg_open;
	public static String Other;

	public static String Full_Path_Label_Text;
	public static String Browse_Button_Text;

	public static String Error_Path_Cant_be_blank;
	public static String Erorr_Path_Not_Exist_or_Not_a_File;

	public static String OpenExploer_Error;
	public static String Cant_Open;

	public static String Preference_note;
	public static String FileManager_need_to_be_installed;

	public static String System_File_Manager_Preferences;

}
