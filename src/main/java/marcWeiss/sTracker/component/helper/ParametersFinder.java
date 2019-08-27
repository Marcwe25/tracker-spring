package marcWeiss.sTracker.component.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import marcWeiss.sTracker.statistic.beans.MethodBean;
import marcWeiss.sTracker.statistic.component.repository.MethodRepositoryType;

@Component
public class ParametersFinder {
	
	@Autowired
	MethodRepositoryType methodRepository;

	private static String methodSignatureRegex = "([\\s\\n]*public[\\s\n]*|[\\s\n]*private[\\s\n]*|[\\s\\n]*static[\\s\\n]*|[\\s\\n]*protected[\\s\n]*|[\\s\\n]*abstract[\\s\n]*|[\\s\\n]*native[\\s\\n]*|[\\s\n]*synchronized[\\s\\n]*)*[\\s\\n]*(?<return>[a-zA-Z0-9<>._?,]+)[\\s\\n]+(?<methodName>[a-zA-Z0-9_]+)[\\s\\n]*(?<parameters>\\([a-zA-Z0-9<>\\[\\]._?, \\n]*\\)) *([a-zA-Z0-9_ ,\\n]*) *\\{";
	
	private String fullClassName;
	private int lineNumber;
	private String classContent;
	private String methodParameters;
	private String knownMethodName;
	private String packageName;
	private String className;

	public ParametersFinder(){}
	
	public  String searchParams(StackTraceElement element) throws IOException {
		reset();
		this.fullClassName = element.getClassName();
		this.knownMethodName = element.getMethodName();
		this.lineNumber = element.getLineNumber();
		this.packageName = fullClassName.substring(0, fullClassName.lastIndexOf("."));
		this.className = fullClassName.substring(fullClassName.lastIndexOf(".")+1);
		this.classContent=classContent(fullClassName);
		String lastMethod = "";
		String lastParameters = "";
		
		Matcher p = Pattern.compile(methodSignatureRegex).matcher(classContent);
		while (p.find()) {
			int startIndex = p.start();
			int startLineNumber = getLine(classContent, startIndex);
			if (startLineNumber < lineNumber) {
				lastMethod = p.group("methodName");
				lastParameters = p.group("parameters");
			} else{
				break;
			}
		}
		if(!lastMethod.equals(knownMethodName))
			return null;
		methodParameters = lastParameters;
		formatParameters();
		String[] checkedParams = updateByComparingToDatabase();
		String newParams = paramsArrayToStringPresentation(checkedParams);
		return newParams;
	}
	
	private List<String[]> paramsFromDatabase(){
		//build a methodbean instance class to search for in database
		MethodBean methodBean = new MethodBean();
		methodBean.setClassName(className);
		methodBean.setPackageName(packageName);
		methodBean.setMethodName(knownMethodName);
		List<MethodBean> methods= methodRepository.findSimilar(methodBean);
		//convert to List<String[]> for processing
		List<String[]> paramsFromDatabase = new ArrayList<>();
		if(methods==null||methods.size()==0) return paramsFromDatabase;
		for(MethodBean method : methods){
			String[] listParameters = stringListToArray(method.getMethodParameters());
			paramsFromDatabase.add(listParameters);
		}
		return paramsFromDatabase;
	}
	
	
	private String[] stringListToArray(String paramsAsString){
		String[] params = paramsAsString.substring(1, paramsAsString.length()-1).split(",");
		return params;
	}
	
	private HashMap<String,HashMap<Integer,String>> sortedParams(){ /*split use regex, so added "\\" in front of . in split*/
		String[] params = stringListToArray(methodParameters);
		HashMap<Integer,String> completeParams = new HashMap<>();
		HashMap<Integer,String> uncompleteParams = new HashMap<>();
		for (int i = 0; i < params.length; i++) {
			if(params[i].split("\\.").length>1){
				completeParams.put(i,params[i]);
			} else {uncompleteParams.put(i,params[i]);
			}
		}
		HashMap<String,HashMap<Integer,String>> paramsSorted = new HashMap<>();
		paramsSorted.put("complete", completeParams);
		paramsSorted.put("uncomplete", uncompleteParams);
		return paramsSorted;
	}
	
	private String[] updateByComparingToDatabase(){
		//gatering data
		List<String[]> paramsFromDatabase = paramsFromDatabase();
		HashMap<String,HashMap<Integer,String>> sortedParams = sortedParams();
		HashMap<Integer,String> uncompleteParameters = sortedParams.get("uncomplete");
		HashMap<Integer,String> completeParameters = sortedParams.get("complete");
		
		//filtering params from database;
		List<String[]> search = paramsFromDatabase.stream()
			.filter(d->{return  d.length==(uncompleteParameters.size()+completeParameters.size());})
			.filter(d->{return compareArrayToMap(d,completeParameters);})
			.filter(d->{
				String[] splitedArray = Arrays.stream(d)
					.map(s->{
						String[] tmp = s.split("\\.");
						return tmp[tmp.length-1];}
							)
					.toArray(String[]::new);
				return compareArrayToMap(splitedArray,uncompleteParameters);})
			.collect(Collectors.toList());
		
		//returning only unique result
		if(search.size()==1) return search.get(0);
		else return null;
	}
	
	private boolean compareArrayToMap(String[] array, Map<Integer,String> map){
		Set<Integer> indexes = map.keySet();
		for (Integer index : indexes) {
			String fromArray = array[index].trim();
			String fromMap = map.get(index).trim();
			if(!fromArray.equals(fromMap)) return false;
		}
		return true;
	}
	
	private  String classContent(String fullClassName) throws IOException {
		String delimiter = FileSystems.getDefault().getSeparator();
		String filePath = "C:\\java\\currentFiles\\src\\main\\java\\"+fullClassName.replaceAll("\\.", Matcher.quoteReplacement(delimiter)) + ".java";
		File file = new File(filePath);
		
		String fileContent = Utility.FileToString(file);
		return fileContent;
	}

	
	private String paramsArrayToStringPresentation(String[] params){
		if(params==null) return null;
		String string = "(";
		for (int i = 0; i < params.length; i++) {
			string += params[i]+",";
		}
		string=string.subSequence(0, string.length()-1)+")";
		return string;
	}

	private int getLine(String data, int index) {
		int line = 1;
		Pattern pattern = Pattern.compile("\n");
		Matcher matcher = pattern.matcher(data);
		matcher.region(0, index);
		while (matcher.find()) {
			line++;
		}
		return (line);
	}
	
	private void formatParameters(){
		methodParameters = methodParameters.replaceAll("\\(|\\)", "");
		String[] params = methodParameters.split(",");
		String newParametersString="";
		for (int i = 0; i < params.length; i++) {
			params[i]=params[i].trim();
			params[i]=params[i].split("\\s+")[0];
			params[i]=findInImport(params[i]);
			newParametersString += (params[i]+",");
		}
		newParametersString = "("+newParametersString.substring(0, newParametersString.length()-1)+")";
		methodParameters=newParametersString;
	}
	
	private String findInImport(String paramType){
		String regex = "import (?<fullType>(.{1,})\\.("+paramType+"))(;)";
		String fullType = paramType;
		int count = 0;
		Matcher m = Pattern.compile(regex).matcher(classContent);
		while(m.find()){
			if(++count>1) return paramType;
			fullType = m.group("fullType");
		}
		return fullType;
	}
	
	private void reset(){
		this.lineNumber = -1;
		this.fullClassName = null;
		this.knownMethodName = null;
		this.classContent = null;
		this.methodParameters = null;
		this.packageName = null;
		this.className = null;
	}
}
