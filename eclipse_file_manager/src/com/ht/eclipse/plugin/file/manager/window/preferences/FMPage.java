/**
 * File Name:FMPage.java
 * Package Name:com.ht.eclipse.plugin.file.manager.window.preferences
 * Date:2017-2-8上午10:58:59
 * Copyright (c) 2016, huobao_accp@163.com All Rights Reserved.
 *
*/

package com.ht.eclipse.plugin.file.manager.window.preferences;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ht.eclipse.plugin.file.manager.Activator;
import com.ht.eclipse.plugin.file.manager.Constants;
import com.ht.eclipse.plugin.file.manager.Messages;

/**
 * ClassName:FMPage <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017-2-8 上午10:58:59 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class FMPage extends PreferencePage implements IWorkbenchPreferencePage {

	private ArrayList<Button> fileManagerButtons = new ArrayList<Button>();

	private Label fullPathLabel;

	private Text fileManagerPath;

	private Button browseButton;

	private String selectedFileManager;

	private String fileManagerPathString;

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 * Date: 2017-2-8 上午11:11:25
	 */
	public void init(IWorkbench workbench) {
		IPreferenceStore store = getPreferenceStore();
		selectedFileManager = store
		        .getString(Constants.LINUX_FILE_MANAGER);
		fileManagerPathString = store
		        .getString(Constants.LINUX_FILE_MANAGER_PATH);
		setDescription(Messages.System_File_Manager_Preferences);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 * Date: 2017-2-8 上午11:11:28
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = createComposite(parent);
		createMacOSXFMGroup(composite);
		createWindowsFMGroup(composite);
		createLinuxFMGroup(composite);
		return composite;
	}

	/**
	 * createComposite:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:34
	 * @author Administrator
	 * @param parent
	 * @return
	 */
	protected Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
		        | GridData.HORIZONTAL_ALIGN_FILL));
		return composite;
	}

	/**
	 * createGroup:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:31
	 * @author Administrator
	 * @param composite
	 * @param title
	 * @return
	 */
	private Group createGroup(Composite composite, String title) {
		Group groupComposite = new Group(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		groupComposite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
		        | GridData.GRAB_HORIZONTAL);
		groupComposite.setLayoutData(data);
		groupComposite.setText(title);
		return groupComposite;
	}

	/**
	 * createMacOSXFMGroup:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:38
	 * @author Administrator
	 * @param composite
	 */
	protected void createMacOSXFMGroup(Composite composite) {
		Group groupComposite = createGroup(composite, Messages.MAC_OS_X);
		Button macOSXFMButton = createRadioButton(groupComposite,
		        Messages.Finder, Constants.FINDER);
		macOSXFMButton.setSelection(true);
	}

	/**
	 * createWindowsFMGroup:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:40
	 * @author Administrator
	 * @param composite
	 */
	protected void createWindowsFMGroup(Composite composite) {
		Group groupComposite = createGroup(composite, Messages.WINDOWS);
		Button windowsFMButton = createRadioButton(groupComposite,
		        Messages.Windows_Explorer, Constants.EXPLORER);
		windowsFMButton.setSelection(true);
	}

	/**
	 * createLinuxFMGroup:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:43
	 * @author Administrator
	 * @param composite
	 */
	protected void createLinuxFMGroup(Composite composite) {
		Group groupComposite = createGroup(composite, Messages.LINUX);

		String label = Messages.Nautilus;
		String value = Constants.NAUTILUS;
		createRadioButtonWithSelectionListener(groupComposite, label, value,
		        false);

		label = Messages.Dolphin;
		value = Constants.DOLPHIN;
		createRadioButtonWithSelectionListener(groupComposite, label, value,
		        false);

		label = Messages.Thunar;
		value = Constants.THUNAR;
		createRadioButtonWithSelectionListener(groupComposite, label, value,
		        false);

		label = Messages.PCManFM;
		value = Constants.PCMANFM;
		createRadioButtonWithSelectionListener(groupComposite, label, value,
		        false);

		label = Messages.ROX;
		value = Constants.ROX;
		createRadioButtonWithSelectionListener(groupComposite, label, value,
		        false);

		label = Messages.Xdg_open;
		value = Constants.XDG_OPEN;
		createRadioButtonWithSelectionListener(groupComposite, label, value,
		        false);

		label = Messages.Other;
		value = Constants.OTHER;
		createRadioButtonWithSelectionListener(groupComposite, label, value,
		        true);

		Composite c = new Composite(groupComposite, SWT.NONE);
		c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		c.setLayout(layout);

		fullPathLabel = new Label(c, SWT.NONE);
		fullPathLabel.setText(Messages.Full_Path_Label_Text);
		fileManagerPath = new Text(c, SWT.BORDER);
		if (fileManagerPathString != null) {
			fileManagerPath.setText(fileManagerPathString);
		}
		fileManagerPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileManagerPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (fileManagerPath.isEnabled()) {
					String path = fileManagerPath.getText();
					if (path == null || path.equals("")) {
						setValid(false);
						setErrorMessage(Messages.Error_Path_Cant_be_blank);
						return;
					}
					File f = new File(path);
					if (!f.exists() || !f.isFile()) {
						setValid(false);
						setErrorMessage(Messages.Erorr_Path_Not_Exist_or_Not_a_File);
						return;
					}
					setErrorMessage(null);
					setValid(true);
				}
			}
		});

		browseButton = new Button(c, SWT.PUSH);
		browseButton.setText(Messages.Browse_Button_Text);
		browseButton.setFont(composite.getFont());
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				String newValue = browsePressed();
				if (newValue != null) {
					setFileManagerPath(newValue);
				}
			}
		});
		browseButton.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				browseButton = null;
			}
		});

		if (!Constants.OTHER.equals(selectedFileManager)) {
			fullPathLabel.setEnabled(false);
			fileManagerPath.setEnabled(false);
			browseButton.setEnabled(false);
		}

		createNoteComposite(composite.getFont(), groupComposite,
		        Messages.Preference_note,
		        Messages.FileManager_need_to_be_installed);
	}

	/**
	 * setFileManagerPath:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:51
	 * @author Administrator
	 * @param value
	 */
	private void setFileManagerPath(String value) {
		if (fileManagerPath != null) {
			if (value == null) {
				value = "";
			}
			fileManagerPath.setText(value);
		}
	}

	/**
	 * browsePressed:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:49
	 * @author Administrator
	 * @return
	 */
	private String browsePressed() {
		File f = new File(fileManagerPath.getText());
		if (!f.exists()) {
			f = null;
		}
		File filePath = getFilePath(f);
		if (filePath == null) {
			return null;
		}

		return filePath.getAbsolutePath();
	}

	/**
	 * getFilePath:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:54
	 * @author Administrator
	 * @param startingDirectory
	 * @return
	 */
	private File getFilePath(File startingDirectory) {
		FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN | SWT.SHEET);
		if (startingDirectory != null) {
			fileDialog.setFilterPath(startingDirectory.getPath());
		}
		String filePath = fileDialog.open();
		if (filePath != null) {
			filePath = filePath.trim();
			if (filePath.length() > 0) {
				return new File(filePath);
			}
		}

		return null;
	}

	/**
	 * toggleOtherTextField:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:12:02
	 * @author Administrator
	 * @param enabled
	 */
	private void toggleOtherTextField(boolean enabled) {
		fullPathLabel.setEnabled(enabled);
		fileManagerPath.setEnabled(enabled);
		browseButton.setEnabled(enabled);
	}

	/**
	 * createRadioButton:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:59
	 * @author Administrator
	 * @param parent
	 * @param label
	 * @param value
	 * @return
	 */
	private Button createRadioButton(Composite parent, String label,
	        String value) {
		Button button = new Button(parent, SWT.RADIO | SWT.LEFT);
		button.setText(label);
		button.setData(value);
		return button;
	}

	/**
	 * createRadioButtonWithSelectionListener:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * Date: 2017-2-8 上午11:11:57
	 * @author Administrator
	 * @param parent
	 * @param label
	 * @param value
	 * @param enableOtherTextFieldIfClick
	 * @return
	 */
	private Button createRadioButtonWithSelectionListener(Composite parent,
	        String label, final String value,
	        final boolean enableOtherTextFieldIfClick) {
		Button button = createRadioButton(parent, label, value);
		if (value != null && value.equals(selectedFileManager)) {
			button.setSelection(true);
		}
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedFileManager = (String) e.widget.getData();
				toggleOtherTextField(enableOtherTextFieldIfClick);
				if (Constants.OTHER.equals(value)) {
					fileManagerPath.notifyListeners(SWT.Modify, new Event());
				} else {
					setValid(true);
					setErrorMessage(null);
				}
			}
		});
		fileManagerButtons.add(button);
		return button;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
	 * Date: 2017-2-8 上午11:12:10
	 */
	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 * Date: 2017-2-8 上午11:12:25
	 */
	@Override
	protected void performDefaults() {
		IPreferenceStore store = getPreferenceStore();
		for (Button button : fileManagerButtons) {
			if (store.getDefaultString(Constants.LINUX_FILE_MANAGER)
			        .equals((String) button.getData())) {
				button.setSelection(true);
				selectedFileManager = (String) button.getData();
			} else {
				button.setSelection(false);
			}
		}
		fileManagerPath
		        .setText(store
		                .getDefaultString(Constants.LINUX_FILE_MANAGER_PATH));
		super.performDefaults();
	}
	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 * Date: 2017-2-8 上午11:12:19
	 */
	@Override
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(Constants.LINUX_FILE_MANAGER,
		        selectedFileManager);
		store.setValue(Constants.LINUX_FILE_MANAGER_PATH,
		        fileManagerPath.getText());
		return super.performOk();
	}

}
