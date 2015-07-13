package xml2javacode.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class Utils {

	public static void e1(IWorkbenchWindow window, Exception e) {
		MessageDialog.openInformation(window.getShell(), "xml2javacode", e.getMessage());
	}
}
