package xml2javacode.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import xml2javacode.util.Utils;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AddFieldJavadocHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public AddFieldJavadocHandler() {
	}

	ArrayList<DesciptionLine> descriptionLines = new ArrayList<>();

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelection selection = window.getSelectionService().getSelection();
		TreeSelection treeSelection = (TreeSelection) selection;
		ICompilationUnit javaFile = (ICompilationUnit) treeSelection.getFirstElement();
		ASTParser astParser = ASTParser.newParser(AST.JLS8);
		astParser.setSource(javaFile);
		CompilationUnit compilationUnit = (CompilationUnit) astParser.createAST(null);
		compilationUnit.recordModifications();
		List<AbstractTypeDeclaration> types = compilationUnit.types();
		TypeDeclaration typeDeclaration = (TypeDeclaration) types.get(0);
		FieldDeclaration[] fields = typeDeclaration.getFields();

		try {
			// IWorkbenchPage page = window.getActivePage();
			// IViewPart view =
			// page.showView("xml2javacode.views.Xml2JavaCodeView");
			// Xml2JavaCodeView2 viewPart = (Xml2JavaCodeView2) view;
			// Text textDescriptionFile = viewPart.getTextDescriptionFile();
			FilePathInputDialog dialog = new FilePathInputDialog(window.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			dialog.createContents();
			dialog.getShlXmljavacode().open();
			dialog.getShlXmljavacode().layout();
			Display display = dialog.getShlXmljavacode().getParent().getDisplay();
			Text textDesFilePath = dialog.getTextDesFilePath();
			String descriptionFilePath = null;
			while (!dialog.getShlXmljavacode().isDisposed()) {
				descriptionFilePath = textDesFilePath.getText();
				String data = (String) dialog.getShlXmljavacode().getData();
				if("ignore".equals(data)){
					dialog.getShlXmljavacode().close();
					return null;
				};
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}

			if (descriptionFilePath.equals("")) {
				MessageDialog.openInformation(window.getShell(), "java2xmlcode", "请写上描述文件路径");
				return null;
			}
			InputStream in = new FileInputStream(new File(descriptionFilePath));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str = null;
			while ((str = br.readLine()) != null) {
				descriptionLines.add(new DesciptionLine(str));
			}
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			if(e.getMessage().equals(""))
			{
				MessageDialog.openInformation(window.getShell(), "java2xmlcode", e.getCause().toString());
			}else
			{
				Utils.e1(window, e);
			}
		}

		compilationUnit.accept(new FieldVisitor(window, descriptionLines));
		// AST ast = compilationUnit.getAST();
		// Javadoc javadoc = ast.newJavadoc();
		// TextElement textElement = ast.newTextElement();
		// textElement.setText("hello world");
		// TagElement tagElement = ast.newTagElement();
		// tagElement.fragments().add(textElement);
		// javadoc.tags().add(tagElement);
		// typeDeclaration.setJavadoc(javadoc);
		// for(int i = 0;i < fields.length;i++)
		// {
		// fields[i].setJavadoc(javadoc);
		// }

		ASTRewrite astRewrite = ASTRewrite.create(compilationUnit.getAST());
		try {
			Document document = new Document(javaFile.getSource());
			// astRewrite.rewriteAST().apply(document);
			TextEdit rewrite = compilationUnit.rewrite(document, null);
			rewrite.apply(document);
			javaFile.getBuffer().setContents(document.get());
		} catch (MalformedTreeException | JavaModelException | IllegalArgumentException | BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Utils.e1(window, e1);
		}

		// MessageDialog.openInformation(
		// window.getShell(),
		// "xml2javacode",
		// classFile+"");
		// try {
		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream(file)));
		// bw.flush();
		// bw.close();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// MessageDialog.openInformation(
		// window.getShell(),
		// "xml2javacode",
		// e.getMessage()+"");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return null;
	}
}
