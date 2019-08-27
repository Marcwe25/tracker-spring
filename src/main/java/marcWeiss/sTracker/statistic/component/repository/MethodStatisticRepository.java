package marcWeiss.sTracker.statistic.component.repository;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;

import marcWeiss.sTracker.component.helper.PageProperties;

@Repository
public class MethodStatisticRepository implements MethodStatisticRepositoryType{

	@Autowired
	PageProperties adminStatpage;
	
	@Autowired
	JdbcTemplate saJdbcTemplate;

	@Override
	public List<Map<String, Object>> getGeneralStatistic() {
		String sql = ""
				+ "select "
					+ "packagename,"
					+ "classname,"
					+ "methodname,"
					+ "count (distinct userid) as users,"
					+ "count(methodname) as used,"
					+ "min(duration) mini,"
					+ "avg(duration) duration,"
					+ "max(duration) maxi,"
					+ "count(extype) exc"
				+ " from methodusage "
				+ " where datetime between ? and ? and sessionid not like ? "
				+ " group by methodparameters,methodname,classname,packagename"
				+ " order by packagename,classname,METHODNAME";
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,from(),to(), sessionid());
		return l;
		
	}

	@Override
	public List<Map<String, Object>> getExceptionStatistic() {
		String sql  = ""
				+ "select "
					+ "m.packagename,"
					+ "m.classname, "
					+ "m.methodname,"
					+ "m.extype as exc,"
					+ "count(extype) as total,"
					+ "cast(100.0*count(m.methodname)/sum(u.myCount)*count(u.methodname) as decimal(5,2)) as percent "
				+ "from METHODUSAGE as m  join "
									+ "(select "
										+ "count(methodname) as myCount,"
										+ "	methodparameters,"
										+ "methodname,"
										+ "classname,"
										+ "packagename "
									+ "from METHODUSAGE "
									+ "group by methodparameters,methodname,classname,packagename) "
									+ "as u "
				+ "on "
					+ "m.methodparameters=u.methodparameters "
					+ "and m.methodname=u.methodname "
					+ "and m.classname=u.classname "
					+ "and m.packagename=u.packagename "
				+ " where datetime between ? and ?  and sessionid not like ? "
				+ "group by m.extype, m.methodparameters,m.methodname,m.classname,m.packagename "
				+ "order by m.packagename,m.classname,m.METHODNAME";
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,from(),to(), sessionid());
		return l;
	}

	@Override
	public List<Map<String, Object>> getpeekDays() {

		String sql = ""
				+ "select count (distinct userid) as users, date(datetime) days "
				+ "from METHODUSAGE "
				+ "where datetime between ? and ?  and sessionid not like ? "
				+ "group by date(datetime) "
				+ "order by users desc";
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,from(),to(), sessionid());
		return l;
	}
	
	@Override
	public List<Map<String, Object>> getpeekHours() {
		
		String sql = ""
				+ "select count (distinct userid) as users, hour(datetime) time "
				+ "from METHODUSAGE "
				+ " where datetime between ? and ?  and sessionid not like ? "
				+ "group by hour(datetime) "
				+ "order by users desc";
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,from(),to(), sessionid());
		return l;
	}

	@Override
	public List<Map<String, Object>> unusedMethod() {
		String sql  = ""
				+ "select "
					+ "count(u.methodname) as n,"
					+ "m.packagename,"
					+ "m.classname, "
					+ "m.methodname, "
					+ "m.methodparameters "
				+ "from sa_method as m left outer join METHODUSAGE as u "
				+ "on m.methodparameters=u.methodparameters and m.methodname=u.methodname and m.classname=u.classname and m.packagename=u.packagename "
				+ " where datetime between ? and ?  and sessionid not like ? "
				+ "group by m.methodparameters, m.methodname, m.classname, m.packagename "
				+ "order by n asc";
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,from(),to(), sessionid());
		return l;
	}

	@Override
	public List<Map<String, Object>> rawUsage() {
		String sql  = ""
				+ "select id,methodparameters,methodname,classname,packagename,datetime,duration, extype as exc, exmessage as message "
				+ "from methodusage "
				+ "where datetime between ? and ?  and sessionid not like ? "
				+ "order by DATETIME desc "
				+ "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,from(),to(), sessionid(),adminStatpage.getPagingValue());
		return l;
	}
	
	
	public Timestamp from(){
		return Timestamp.valueOf(adminStatpage.getFrom().atStartOfDay());
	}
	public Timestamp to(){
		return Timestamp.valueOf(adminStatpage.getTo().atTime(LocalTime.MAX));
	}
	public String sessionid(){
		return RequestContextHolder.currentRequestAttributes().getSessionId();
	}

	@Override
	public List<Map<String, Object>> bySession(long usageid) {
		String sql  = "select m.id,m.methodparameters,m.methodname,m.classname,m.packagename,m.datetime,m.duration, m.extype as exc, m.exmessage as message "
				+ "from methodusage as m inner join (select id,sessionid from methodusage where id=?) as s "
				+ "on m.sessionid=s.sessionid "
				+ "where datetime between ? and ? "
				+ "order by DATETIME desc "
				+ "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";				;
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,usageid,from(),to(),adminStatpage.getPagingValue());
		return l;
	}

	@Override
	public List<Map<String, Object>> byUser(long usageid) {
		String sql  = "select m.id,m.methodparameters,m.methodname,m.classname,m.packagename,m.datetime,m.duration, m.extype as exc, m.exmessage as message "
				+ "from methodusage as m inner join (select id,userid from methodusage where id=?) as s "
				+ "on m.userid=s.userid "
				+ "where datetime between ? and ? and sessionid not like ?  "
				+ "order by DATETIME desc "
				+ "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";				;
		List<Map<String, Object>> l = saJdbcTemplate.queryForList(sql,usageid,from(),to(), sessionid(),adminStatpage.getPagingValue());
		return l;
	}
}
