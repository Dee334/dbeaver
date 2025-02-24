/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2023 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.tasks.ui.view;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.model.task.DBTTask;
import org.jkiss.dbeaver.tasks.ui.internal.TaskUIViewMessages;
import org.jkiss.dbeaver.ui.dialogs.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTasksSelectorDialog extends BaseDialog {
    private static final Log log = Log.getLog(DatabaseTasksSelectorDialog.class);

    private DatabaseTasksTree tasksTree;

    private final List<DBTTask> selectedTasks = new ArrayList<>();

    public DatabaseTasksSelectorDialog(Shell parentShell) {
        super(parentShell, TaskUIViewMessages.db_tasks_selector_dialog, null);
    }

    @Override
    protected Composite createDialogArea(Composite parent) {
        Composite dialogArea = super.createDialogArea(parent);

        this.tasksTree = new DatabaseTasksTree(dialogArea, true);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = 500;
        this.tasksTree.getViewer().getTree().setLayoutData(gd);
        tasksTree.loadViewConfig();
        tasksTree.getViewer().addSelectionChangedListener(event -> {
            selectedTasks.clear();
            selectedTasks.addAll(tasksTree.getCheckedTasks());

            getButton(IDialogConstants.OK_ID).setEnabled(!selectedTasks.isEmpty());
        });

        tasksTree.loadTasks();

        return dialogArea;
    }

    public List<DBTTask> getSelectedTasks() {
        return selectedTasks;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        getButton(IDialogConstants.OK_ID).setEnabled(false);
    }
}
