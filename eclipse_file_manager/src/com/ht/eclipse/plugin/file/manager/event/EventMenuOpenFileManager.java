/**
 * File Name:EventMenuOpenFileManager.java
 * Package Name:com.ht.eclipse.plugin.file.manager.event
 * Date:2017-2-8����10:58:17
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager.event;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * ClassName:EventMenuOpenFileManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 ����10:58:17 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class EventMenuOpenFileManager extends EventBaseFileManager implements IWorkbenchWindowActionDelegate {

    /**
     * TODO �������÷�����ʵ�ֹ��ܣ���ѡ��.
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     * Date: 2017-2-8 ����11:11:12
     */
    public void init(IWorkbenchWindow window) {
        this.window = window;
        this.shell = this.window.getShell();
    }

    public void dispose() {
    }

}

