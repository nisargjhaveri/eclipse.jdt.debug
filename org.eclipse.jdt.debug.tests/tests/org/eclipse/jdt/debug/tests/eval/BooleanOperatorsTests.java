package org.eclipse.jdt.debug.tests.eval;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaPrimitiveValue;
import org.eclipse.jdt.internal.debug.core.model.JDIObjectValue;

public class BooleanOperatorsTests extends Tests {

	public BooleanOperatorsTests(String arg) {
		super(arg);
	}

	protected void init() throws Exception {
		initializeFrame("EvalSimpleTests",5,1);
	}

	protected void end() throws Exception {
		destroyFrame();
	}

	// boolean | boolean

	public void testBooleanOrBoolean() throws Throwable {
		try {
		init();
		IValue value = eval(xBoolean + orOp + xBoolean);
		String typeName = value.getReferenceTypeName();
		assertEquals("boolean or boolean : wrong type : ", "boolean", typeName);
		boolean booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean or boolean : wrong result : ", xBooleanValue | xBooleanValue, booleanValue);

		value = eval(xBoolean + orOp + yBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean or boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean or boolean : wrong result : ", xBooleanValue | yBooleanValue, booleanValue);

		value = eval(yBoolean + orOp + xBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean or boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean or boolean : wrong result : ", yBooleanValue | xBooleanValue, booleanValue);

		value = eval(yBoolean + orOp + yBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean or boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean or boolean : wrong result : ", yBooleanValue | yBooleanValue, booleanValue);
		} catch (Throwable e) {
		e.printStackTrace(); throw e;
		} finally {;
		end();
		}
	}

	// boolean & boolean

	public void testBooleanAndBoolean() throws Throwable {
		try {
		init();
		IValue value = eval(xBoolean + andOp + xBoolean);
		String typeName = value.getReferenceTypeName();
		assertEquals("boolean and boolean : wrong type : ", "boolean", typeName);
		boolean booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean and boolean : wrong result : ", xBooleanValue & xBooleanValue, booleanValue);

		value = eval(xBoolean + andOp + yBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean and boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean and boolean : wrong result : ", xBooleanValue & yBooleanValue, booleanValue);

		value = eval(yBoolean + andOp + xBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean and boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean and boolean : wrong result : ", yBooleanValue & xBooleanValue, booleanValue);

		value = eval(yBoolean + andOp + yBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean and boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean and boolean : wrong result : ", yBooleanValue & yBooleanValue, booleanValue);
		} catch (Throwable e) {
		e.printStackTrace(); throw e;
		} finally {;
		end();
		}
	}

	// boolean ^ boolean

	public void testBooleanXorBoolean() throws Throwable {
		try {
		init();
		IValue value = eval(xBoolean + xorOp + xBoolean);
		String typeName = value.getReferenceTypeName();
		assertEquals("boolean xor boolean : wrong type : ", "boolean", typeName);
		boolean booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean xor boolean : wrong result : ", xBooleanValue ^ xBooleanValue, booleanValue);

		value = eval(xBoolean + xorOp + yBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean xor boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean xor boolean : wrong result : ", xBooleanValue ^ yBooleanValue, booleanValue);

		value = eval(yBoolean + xorOp + xBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean xor boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean xor boolean : wrong result : ", yBooleanValue ^ xBooleanValue, booleanValue);

		value = eval(yBoolean + xorOp + yBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("boolean xor boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("boolean xor boolean : wrong result : ", yBooleanValue ^ yBooleanValue, booleanValue);
		} catch (Throwable e) {
		e.printStackTrace(); throw e;
		} finally {;
		end();
		}
	}

	// ! boolean

	public void testNotBoolean() throws Throwable {
		try {
		init();
		IValue value = eval(notOp + xBoolean);
		String typeName = value.getReferenceTypeName();
		assertEquals("not boolean : wrong type : ", "boolean", typeName);
		boolean booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("not boolean : wrong result : ", ! xBooleanValue, booleanValue);

		value = eval(notOp + yBoolean);
		typeName = value.getReferenceTypeName();
		assertEquals("not boolean : wrong type : ", "boolean", typeName);
		booleanValue = ((IJavaPrimitiveValue)value).getBooleanValue();
		assertEquals("not boolean : wrong result : ", ! yBooleanValue, booleanValue);
		} catch (Throwable e) {
		e.printStackTrace(); throw e;
		} finally {;
		end();
		}
	}


}
