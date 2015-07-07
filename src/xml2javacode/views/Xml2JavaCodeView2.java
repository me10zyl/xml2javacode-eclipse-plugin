package xml2javacode.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class Xml2JavaCodeView2 extends ViewPart{
	private Text textXML;
	private Text textDescriptionFile;
	private Text textArea;
	public Xml2JavaCodeView2() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(final Composite parent) {
				parent.setLayout(new FillLayout(SWT.HORIZONTAL));
				
				ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				scrolledComposite.setExpandHorizontal(true);
				scrolledComposite.setExpandVertical(true);
				
				Composite composite = new Composite(scrolledComposite, SWT.NO_MERGE_PAINTS);
				composite.setLayout(new GridLayout(5, false));
				
				Label lblNewLabel = new Label(composite, SWT.NONE);
				lblNewLabel.setText("XML\u6587\u4EF6\u5730\u5740");
				//		Frame frame = SWT_AWT.new_Frame(parent);
				//		frame.add(new UIMain());
				//		parent.setLayout(null);
						textXML = new Text(composite, SWT.BORDER);
						textXML.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
						
						Button btnNewButton = new Button(composite, SWT.NONE);
						btnNewButton.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								FileDialog filedialog = new FileDialog(parent.getShell(),SWT.OPEN);
								String path = filedialog.open();
								textXML.setText(path);
							}
						});
						btnNewButton.setText("\u6D4F\u89C8");
						
						Button btnTree = new Button(composite, SWT.NONE);
						btnTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 2));
						btnTree.setText("Tree");
						
						Label lblNewLabel_1 = new Label(composite, SWT.NONE);
						lblNewLabel_1.setText("\u63CF\u8FF0\u6587\u4EF6\u5730\u5740\uFF08\u53EF\u9009\uFF09");
						
						textDescriptionFile = new Text(composite, SWT.BORDER);
						textDescriptionFile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
						
						Button button = new Button(composite, SWT.NONE);
						button.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								FileDialog filedialog = new FileDialog(parent.getShell(),SWT.OPEN);
								String path = filedialog.open();
								textDescriptionFile.setText(path);
							}
						});
						button.setText("\u6D4F\u89C8");
						
						textArea = new Text(composite, SWT.BORDER);
						GridData gd_textArea = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 3);
						gd_textArea.widthHint = 319;
						textArea.setLayoutData(gd_textArea);
						
						final Group group = new Group(composite, SWT.NONE);
						group.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 2));
						group.setText("\u590D\u5236\u2193\u2193\u2193");
						group.setLayout(new FillLayout(SWT.HORIZONTAL));
						
						Button btnNewButton_2 = new Button(group, SWT.NONE);
						btnNewButton_2.setText("\u6DF1\u5EA60\u5143\u7D20");
						new Label(composite, SWT.NONE);
						new Label(composite, SWT.NONE);
						
						Button buttonDescriptionFile = new Button(composite, SWT.NONE);
						buttonDescriptionFile.setText("\u63CF\u8FF0\u6587\u4EF6\u683C\u5F0F\u8BF4\u660E");
						
						final Button btnCheckButtonIsPrintDepth = new Button(composite, SWT.CHECK);
						btnCheckButtonIsPrintDepth.setSelection(true);
						btnCheckButtonIsPrintDepth.setText("\u662F\u5426\u6253\u5370\u6DF1\u5EA6");
						
						final Button btnCheckButtonAttributeAsField = new Button(composite, SWT.CHECK);
						btnCheckButtonAttributeAsField.setText("\u662F\u5426\u6DFB\u52A0\u5C5E\u6027\u4F5C\u4E3A\u5B57\u6BB5");
				scrolledComposite.setContent(composite);
				scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				btnTree.addSelectionListener(new SelectionAdapter() {
					

					@Override
					public void widgetSelected(SelectionEvent e) {
						
						
					}

				
				});
	}

	private void e1(final Composite parent, Exception e1) {
		MessageBox m = new MessageBox(parent.getShell());
		m.setMessage(e1.getMessage());
		m.open();
		e1.printStackTrace();
	}
	
	@PreDestroy
	public void dispose() {
	}

	public void setFocus() {
		// TODO	Set the focus to control
	}

	@Override
	public void createPartControl(final Composite parent) {
		// TODO 自动生成的方法存根
		createControls(parent);
	}
}
