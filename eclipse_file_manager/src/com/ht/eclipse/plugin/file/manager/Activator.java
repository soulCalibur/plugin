/**
 * File Name:Activator.java
 * Package Name:com.ht.eclipse.plugin.file.manager
 * Date:2017-2-8上午10:48:29
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * ClassName:Activator <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 上午10:48:29 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Activator extends AbstractUIPlugin {

    // The shared instance
    private static Activator plugin;

    /**
     * Creates a new instance of Activator.
     *
     */
    public Activator() {
    }

    /**
     * TODO 简单描述该方法的实现功能（可选）.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     * Date: 2017-2-8 上午10:53:12
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }
    /**
     * TODO 简单描述该方法的实现功能（可选）.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     * Date: 2017-2-8 上午10:53:21
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * getDefault:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * Date: 2017-2-8 上午10:53:25
     * @author Administrator
     * @return
     */
    public static Activator getDefault() {
        return plugin;
    }

}
