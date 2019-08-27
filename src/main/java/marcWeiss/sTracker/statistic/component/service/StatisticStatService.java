package marcWeiss.sTracker.statistic.component.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marcWeiss.sTracker.component.helper.PageProperties;
import marcWeiss.sTracker.statistic.component.repository.MethodStatisticRepositoryType;

@Service
public class StatisticStatService implements StatisticStatServiceType {

	@Autowired
	MethodStatisticRepositoryType statRepository;
	@Autowired
	PageProperties adminStatpage;
	
	@Override
	public List<Map<String, Object>> getGeneraleStatistic() {
		List<Map<String, Object>> statistic = statRepository.getGeneralStatistic();
		statistic = format(statistic, "PACKAGENAME");
		return statistic;
	}

	@Override
	public List<Map<String, Object>> getExceptionStatistic() {
		List<Map<String, Object>> statistic = statRepository.getExceptionStatistic();
		statistic = statistic.stream().filter(m -> {
			return (int) m.get("total") > 0;
		}).collect(Collectors.toList());
		statistic = format(statistic, "exc","PACKAGENAME");
		return statistic;
	}

	@Override
	public List<Map<String, Object>> peekDays() {
		List<Map<String, Object>> statistic = statRepository.getpeekDays();
		return statistic;
	}

	@Override
	public List<Map<String, Object>> unusedMethod() {
		List<Map<String, Object>> statistic = statRepository.unusedMethod();
		statistic = format(statistic, "METHODPARAMETERS","PACKAGENAME");
		return statistic;
	}

	@Override
	public List<Map<String, Object>> peekHours() {
		List<Map<String, Object>> statistic = statRepository.getpeekHours();
		return statistic;
	}

	@Override
	public int getTotalFor(List<Map<String, Object>> exceptionStatistic, String key) {
		int total = 0;
		for(Map<String,Object> map : exceptionStatistic){
			total += Integer.valueOf(map.get(key).toString());
		}
		
		return total;
	}

	@Override
	public List<Map<String, Object>> rawData() {
		List<Map<String, Object>> statistic = statRepository.rawUsage();
		statistic = format(statistic,"PACKAGENAME","METHODPARAMETERS");
		return statistic;
	}

	@Override
	public List<Map<String, Object>> sessionData(long usageid) {
		List<Map<String, Object>> statistic = statRepository.bySession(usageid);
		statistic = format(statistic,"PACKAGENAME","METHODPARAMETERS");
		return statistic;
	}
	@Override
	public List<Map<String, Object>> userData(long usageid) {
		List<Map<String, Object>> statistic = statRepository.byUser(usageid);
		statistic = format(statistic,"PACKAGENAME","METHODPARAMETERS");
		return statistic;
	}
	
	public List<Map<String, Object>> format(List<Map<String, Object>> statistic, String...columns){
		if(adminStatpage.isCompact()){
		statistic = statistic.stream()
		.map(row->{
			Arrays.stream(columns).forEach(
					column->{
						Object r = row.get(column);
						String value = r == null ? "" : r.toString();
						String newValue = "";
						if(!value.startsWith("(")){
							newValue = value.substring(value.lastIndexOf(".")+1);
						} else {
							value = value.replaceAll("\\(|\\)", "");
							String[] params = value.split(",");
							newValue = "(";
							for(String param : params){
								param = param.substring(param.lastIndexOf(".")+1);
								newValue += (param+",");
							}
							newValue = newValue.replaceAll(",$", "\\)");
						}
						row.put(column, newValue);
						});
			return row;
		})
		.collect(Collectors.toList());}
		return statistic;
	}
}
