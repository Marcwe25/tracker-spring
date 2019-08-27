package marcWeiss.sTracker.statistic.beans;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class MethodBean {
	long id;
	String methodParameters;
	String methodName;
	String className;
	String packageName;
	LocalDateTime created;
	
	
	public MethodBean() {
		super();
	}

	public MethodBean(Method method) {
		super();
		String fullClassName = method.getDeclaringClass().toString();
		this.packageName = fullClassName.substring(0, fullClassName.lastIndexOf(".")).replaceAll("^class ", "");
		this.className = fullClassName.substring(fullClassName.lastIndexOf(".")+1);
		this.methodName = method.getName();
		this.methodParameters = methodToStringsParam(method);
	}

	public MethodBean(Signature aspectJSignature) {
		this(((MethodSignature)aspectJSignature).getMethod());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String methodToStringsParam(Method method){
		Parameter[] ps = method.getParameters();
		String params = "(";
		for (int i = 0; i < ps.length; i++) {
			params += ps[i].getParameterizedType().getTypeName()+",";
		}
		params = params.replaceAll(",\\z", "")+")";
		return params;
	}

	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodParameters() {
		return methodParameters;
	}
	public void setMethodParameters(String methodParameters) {
		this.methodParameters = methodParameters;
	}
	
	public String getSignature() {
		return packageName + "." +className + "." + methodName + methodParameters;
	}
	
	
}
