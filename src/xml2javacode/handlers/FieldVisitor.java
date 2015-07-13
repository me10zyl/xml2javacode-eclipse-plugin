package xml2javacode.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;

import net.xicp.zyl_me.xml2javacode.CodeGenerator;
import net.xicp.zyl_me.xml2javacode.DescriptionFileFormatNotCorrectException;
import net.xicp.zyl_me.xml2javacode.ElementWrapper;
import xml2javacode.util.Utils;

public class FieldVisitor extends ASTVisitor {
	List<DesciptionLine> descriptionLines;
	IWorkbenchWindow window;
	static boolean exceptionOccur = false;

	public FieldVisitor(IWorkbenchWindow window, List<DesciptionLine> descriptionLines) {
		super();
		this.descriptionLines = descriptionLines;
		this.window = window;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration fieldDeclaration) {
		// TODO Auto-generated method stub
		AST ast = fieldDeclaration.getAST();
		CodeGenerator generator = new CodeGenerator();
		List<ElementWrapper> list = new ArrayList<ElementWrapper>();
		VariableDeclarationFragment lastFragment = null;
		for (Object obj : fieldDeclaration.fragments()) {
			lastFragment = (VariableDeclarationFragment) obj;
		}

		IViewPart view = null;
		try {
			A: for (DesciptionLine desciptionLine : descriptionLines) {
				String str = desciptionLine.line;
				String[] split = str.split("\\s+");
				if (split.length < 2) {
					throw new DescriptionFileFormatNotCorrectException("请确认字段描述文件的格式是否正确");
				}
				for (DesciptionLine descriptionLine : descriptionLines) {
					if (lastFragment.getName().toString().equals(split[0]) && !split[1].matches("\\s+")) {
						Javadoc javadoc = ast.newJavadoc();
						TextElement textElement = ast.newTextElement();
						textElement.setText(split[1]);
						TagElement tagElement = ast.newTagElement();
						tagElement.fragments().add(textElement);
						javadoc.tags().add(tagElement);
						fieldDeclaration.setJavadoc(javadoc);
						break A;
					}
				}
			}
		} catch (DescriptionFileFormatNotCorrectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (exceptionOccur) {
				return super.visit(fieldDeclaration);
			} else {
				exceptionOccur = true;
			}
			Utils.e1(window, e);
		}
		return super.visit(fieldDeclaration);
	}

}
