package org.eclipse.jdt.internal.debug.ui.actions;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.jdt.internal.debug.ui.BreakpointUtils;
import org.eclipse.jdt.internal.debug.ui.ExceptionHandler;
import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Action to support run to line (i.e. where the cursor is in the active editor)
 */
public class RunToLineAction extends AddBreakpointAction implements IWorkbenchWindowActionDelegate, IViewActionDelegate {

	private IWorkbenchWindow fWorkbenchWindow= null;
	
	public RunToLineAction() {			
	}
	
	protected void run() {
		try {
			IDebugTarget target= getContext();
			if (target == null) {
				getTextEditor().getSite().getShell().getDisplay().beep();
				return;
			}
			
			IType type= getType(getTextEditor().getEditorInput());
			if (type == null) {
				return;
			}
	
			IBreakpoint breakpoint= null;
			try {
				Map attributes = new HashMap(4);
				BreakpointUtils.addJavaBreakpointAttributes(attributes, type);
				BreakpointUtils.addRunToLineAttributes(attributes);
				breakpoint= JDIDebugModel.createLineBreakpoint(BreakpointUtils.getBreakpointResource(type), type.getFullyQualifiedName(), getLineNumber(), -1, -1, 1, false, attributes);
			} catch (CoreException ce) {
				ExceptionHandler.handle(ce, ActionMessages.getString("RunToLine.error.title1"), ActionMessages.getString("RunToLine.error.message1")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			} 
			target.breakpointAdded(breakpoint);
			IThread[] threads= target.getThreads(); 
			for (int i= 0; i < threads.length; i++) {
				IThread thread= threads[i];
				if (thread.canResume()) {
					try {
						thread.resume();
					} catch (DebugException de) {
					}
					break;
				}
			}
		} catch(DebugException de) {
			JDIDebugUIPlugin.logError(de);
		}
	}
	/**
	 * Resolves the debug target context to set the run to line
	 */
	protected IDebugTarget getContext() throws DebugException{
		IDebugTarget target= getContextFromUI();
		if (target == null) {
			target= getContextFromModel();
			//target has already been checked for suspended thread
			return target;
		}
		if (target == null) {
			return null;
		}
		IThread[] threads= target.getThreads();
		for (int i= 0; i < threads.length; i++) {
			IThread thread= threads[i];
			if (thread.canResume()) {
				return target;
			}
		}
		
		return null;
	}
	/**
	 * Resolves a debug target context from the model
	 */
	protected IDebugTarget getContextFromModel() throws DebugException {
		IDebugTarget[] dts= DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
		for (int i= 0; i < dts.length; i++) {
			IDebugTarget dt= dts[i];
			if (getContextFromDebugTarget(dt) != null) {
				return dt;
			}
		}
		return null;
	}
	/**
	 * Resolves a debug target context from the model
	 */
	protected IDebugTarget getContextFromThread(IThread thread) throws DebugException {
		if (thread.isSuspended()) {
			return thread.getDebugTarget();
		}
		return null;
	}
	/**
	 * Resolves a stack frame context from the UI
	 */
	protected IDebugTarget getContextFromUI() throws DebugException {
		IAdaptable de= DebugUITools.getDebugContext();
		if (de != null) {
			if (de instanceof IThread) {
				return getContextFromThread((IThread) de);
			} else if (de instanceof IDebugElement) {
				return ((IDebugElement)de).getDebugTarget();
			}
		}
		
		return null;
	}
	
	/**
	 * Updates the enabled state of this action and the plugin action
	 * this action is the delegate for.
	 */
	public void update() {
		IAction action= getPluginAction();
		if (action != null) {
			try {
				IDebugTarget target= getContext();
				action.setEnabled(target != null 
					&& !(target.isDisconnected() || target.isTerminated())
					&& getTextEditor() != null);
			} catch (DebugException de) {
				action.setEnabled(false);
				JDIDebugUIPlugin.logError(de);
			}
		}
	}
		
	/**
	 * Resolves a stack frame context from the model.
	 */
	protected IDebugTarget getContextFromDebugTarget(IDebugTarget dt) throws DebugException {
		if (dt.isTerminated() || dt.isDisconnected()) {
			return null;
		}
		IThread[] threads= dt.getThreads();
		for (int i= 0; i < threads.length; i++) {
			IThread thread= threads[i];
			if (thread.isSuspended()) {
				return dt;
			}
		}
		return null;
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		setPluginAction(action);
		update();
	}
	
	/**
	 * @see IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		getWorkbenchWindow().getPartService().removePartListener(this);
	}
	/**
	 * @see IWorkbenchWindowActionDelegate#init(IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		setWorkbenchWindow(window);
		IEditorPart part= window.getActivePage().getActiveEditor();
		if (part instanceof ITextEditor) {
			setTextEditor((ITextEditor)part);
		}
		window.getPartService().addPartListener(this);
	}
	
	
	protected IWorkbenchWindow getWorkbenchWindow() {
		return fWorkbenchWindow;
	}
	protected void setWorkbenchWindow(IWorkbenchWindow workbenchWindow) {
		fWorkbenchWindow = workbenchWindow;
	}
	
	/**
	 * @see IEditorActionDelegate#setActiveEditor(IAction, IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof ITextEditor) {
			setTextEditor((ITextEditor)targetEditor);
		}
		setPluginAction(action);
		update();
	}
	
	/**
	 * @see IPartListener#partClosed(IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		if (part == getTextEditor()) {
			setTextEditor(null);
			update();
		}
	}
	
	/**
	 * @see IPartListener#partActivated(IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof ITextEditor) {
			setTextEditor((ITextEditor)part);
			update();
		}
	}
	
	/**
	 * @see IPartListener#partOpened(IWorkbenchPart)
	 */
	public void partOpened(IWorkbenchPart part) {
		if (part instanceof ITextEditor) {
			if (getTextEditor() == null) {
				setTextEditor((ITextEditor)part);
				update();
			}
		}
	}
	/**
	 * @see IViewActionDelegate#init(IViewPart)
	 */
	public void init(IViewPart view) {
		init(view.getViewSite().getWorkbenchWindow());
	}
}