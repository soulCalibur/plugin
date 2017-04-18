/**
 * File Name:Page.java
 * Package Name:com.ht.eclipse.plugin.file.manager.window.preferences
 * Date:2017-2-8上午10:59:05
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager.window.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ht.eclipse.plugin.file.manager.Activator;
import com.ht.eclipse.plugin.file.manager.Constants;
import com.ht.eclipse.plugin.file.manager.Messages;

/**
 * ClassName:Page <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 上午10:59:05 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Page extends PreferencePage implements   IWorkbenchPreferencePage {
	public void init(IWorkbench workbench) {
		setDescription(Messages.Open_Explorer_Version + Constants.VERSION);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
	 * Date: 2017-2-8 上午11:12:44
	 */
	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 * Date: 2017-2-8 上午11:12:47
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = createComposite(parent);
		createLabel(composite, Messages.Expand_Instruction);
		return composite;
	}

	/**
	 * createComposite:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:12:41
	 * @author Administrator
	 * @param parent
	 * @return
	 */
	private Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 5;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return composite;
	}

	/**
	 * createLabel:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:12:39
	 * @author Administrator
	 * @param parent
	 * @param text
	 * @return
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}
}
