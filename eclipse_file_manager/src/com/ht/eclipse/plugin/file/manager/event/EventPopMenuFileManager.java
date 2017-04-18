/**
 * File Name:EventPopMenuFileManager.java
 * Package Name:com.ht.eclipse.plugin.file.manager.event
 * Date:2017-2-8上午10:58:32
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager.event;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * ClassName:EventPopMenuFileManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 上午10:58:32 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class EventPopMenuFileManager extends EventBaseFileManager implements IObjectActionDelegate {

    /**
     * TODO 简单描述该方法的实现功能（可选）.
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
     * Date: 2017-2-8 上午11:11:16
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.window = targetPart.getSite().getWorkbenchWindow();
        this.shell = targetPart.getSite().getShell();
    }

}

