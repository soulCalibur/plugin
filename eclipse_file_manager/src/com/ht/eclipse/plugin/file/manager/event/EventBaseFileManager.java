/**
 * File Name:EventBaseFileManager.java
 * Package Name:com.ht.eclipse.plugin.file.manager.event
 * Date:2017-2-8上午10:57:35
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager.event;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.ht.eclipse.plugin.file.manager.Activator;
import com.ht.eclipse.plugin.file.manager.Messages;
import com.ht.eclipse.plugin.file.manager.OperatingSystem;

/**
 * ClassName:EventBaseFileManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 上午10:57:35 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class EventBaseFileManager implements IActionDelegate, IPropertyChangeListener {
	
	protected IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	protected Shell shell;
	protected ISelection currentSelection;
	protected String systemBrowser;

	public EventBaseFileManager() {
		this.systemBrowser = OperatingSystem.INSTANCE.getSystemBrowser();
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 * Date: 2017-2-8 上午11:10:59
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (OperatingSystem.INSTANCE.isLinux()) {
			this.systemBrowser = OperatingSystem.INSTANCE.getSystemBrowser();
		}
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 * Date: 2017-2-8 上午11:11:02
	 */
	public void run(IAction action) {
		if (this.currentSelection == null || this.currentSelection.isEmpty()) {
			return;
		}
		if (this.currentSelection instanceof ITreeSelection) {
			ITreeSelection treeSelection = (ITreeSelection) this.currentSelection;

			TreePath[] paths = treeSelection.getPaths();

			for (int i = 0; i < paths.length; i++) {
				TreePath path = paths[i];
				IResource resource = null;
				Object segment = path.getLastSegment();
				if ((segment instanceof IResource))
					resource = (IResource) segment;
				else if ((segment instanceof IJavaElement)) {
					resource = ((IJavaElement) segment).getResource();
				}
				if (resource == null) {
					continue;
				}
				String browser = this.systemBrowser;
				String location = resource.getLocation().toOSString();
				if ((resource instanceof IFile)) {
					location = ((IFile) resource).getParent().getLocation().toOSString();
					if (OperatingSystem.INSTANCE.isWindows()) {
						browser = this.systemBrowser + " /select,";
						location = ((IFile) resource).getLocation().toOSString();
					}
				}
				openInBrowser(browser, location);
			}
		} else if (this.currentSelection instanceof ITextSelection
		        || this.currentSelection instanceof IStructuredSelection) {
			// open current editing file
			IEditorPart editor = window.getActivePage().getActiveEditor();
			if (editor != null) {
				IFile current_editing_file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
				String browser = this.systemBrowser;
				String location = current_editing_file.getParent().getLocation().toOSString();
				if (OperatingSystem.INSTANCE.isWindows()) {
					browser = this.systemBrowser + " /select,";
					location = current_editing_file.getLocation().toOSString();
				}
				openInBrowser(browser, location);
			}
		}
	}

	/**
	 * openInBrowser:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:06
	 * @author Administrator
	 * @param browser
	 * @param location
	 */
	protected void openInBrowser(String browser, String location) {
		try {
			if (OperatingSystem.INSTANCE.isWindows()) {
				Runtime.getRuntime().exec(browser + " \"" + location + "\"");
			} else {
				Runtime.getRuntime().exec(new String[] { browser, location });
			}
		} catch (IOException e) {
			MessageDialog.openError(shell, Messages.OpenExploer_Error, Messages.Cant_Open + " \"" + location + "\"");
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.currentSelection = selection;
	}
}

