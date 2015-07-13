package xml2javacode.handlers;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FilePathInputDialog extends Dialog {

	protected Object result;
	protected Shell shlXmljavacode;
	public Shell getShlXmljavacode() {
		return shlXmljavacode;
	}

	private Text textDesFilePath;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public FilePathInputDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	private Object open() {
		createContents();
		shlXmljavacode.open();
		shlXmljavacode.layout();
		Display display = getParent().getDisplay();
		while (!shlXmljavacode.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	public void createContents() {
		shlXmljavacode = new Shell(getParent(), getStyle());
		shlXmljavacode.setSize(450, 174);
		shlXmljavacode.setText("xml2javacode");
		
		Label lblxml = new Label(shlXmljavacode, SWT.NONE);
		lblxml.setBounds(10, 10, 181, 20);
		lblxml.setText("\u8BF7\u586B\u5199XML\u63CF\u8FF0\u6587\u4EF6\u5730\u5740");
		
		textDesFilePath = new Text(shlXmljavacode, SWT.BORDER);
		textDesFilePath.setBounds(61, 54, 247, 26);
		
		Button buttonScanFile = new Button(shlXmljavacode, SWT.NONE);
		buttonScanFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog (shlXmljavacode, SWT.OPEN);
				String open = dialog.open();
				textDesFilePath.setText(open);
			}
		});
		buttonScanFile.setBounds(314, 50, 98, 30);
		buttonScanFile.setText("\u6D4F\u89C8...");
		
		Button buttonOk = new Button(shlXmljavacode, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlXmljavacode.setData("ok");
				shlXmljavacode.close();
			}
		});
		buttonOk.setBounds(61, 95, 98, 30);
		buttonOk.setText("\u786E\u5B9A");
		
		Button buttonCancel = new Button(shlXmljavacode, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlXmljavacode.setData("ignore");
			}
		});
		buttonCancel.setBounds(314, 95, 98, 30);
		buttonCancel.setText("\u53D6\u6D88");

	}

	public Text getTextDesFilePath() {
		return textDesFilePath;
	}

	public void setTextDesFilePath(Text textDesFilePath) {
		this.textDesFilePath = textDesFilePath;
	}
}
