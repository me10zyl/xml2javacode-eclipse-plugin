package xml2javacode.popup.actions;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import xml2javacode.views.Xml2JavaCodeView2;

public class CopyXMLFilePath2ViewAction implements IObjectActionDelegate {

	private Shell shell;
	private IWorkbenchPart targetPart;
	/**
	 * Constructor for Action1.
	 */
	public CopyXMLFilePath2ViewAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		this.targetPart = targetPart;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@Override
	public void run(IAction action) {
		IWorkbenchPage page = targetPart.getSite().getWorkbenchWindow().getActivePage();
		/*MessageDialog.openInformation(
			shell,
			"xml2javacode",
			"XML to Java Code was executed.");*/
		try {
			IStructuredSelection selection = (IStructuredSelection) page.getSelection();
			Object obj = selection.getFirstElement();
			File file = (File) obj;
			IPath fullPath = file.getRawLocation().makeAbsolute();
			String path = fullPath.toString();
			IViewPart view = page.showView("xml2javacode.views.Xml2JavaCodeView");
			Xml2JavaCodeView2 viewPart = (Xml2JavaCodeView2)view;
			Text textXML = viewPart.getTextXML();
			textXML.setText(path);
		} catch (PartInitException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
