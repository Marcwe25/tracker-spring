package marcWeiss.sTracker.statistic.component.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import marcWeiss.sTracker.statistic.beans.MethodBean;
import marcWeiss.sTracker.statistic.beans.MethodEvent;

@Repository
public class MethodRepository implements MethodRepositoryType{

	@Autowired
	JdbcTemplate saJdbcTemplate;
	
	@Override
	public void insert(MethodBean methodBean){
		String sql = "INSERT INTO sa_method "
				+ "(methodParameters,methodName,className,packageName,created) "
				+ "VALUES (?,?,?,?,CURRENT_TIMESTAMP)";
		saJdbcTemplate.update(
				sql,
				lengthValidated(methodBean.getMethodParameters(),255),
				lengthValidated(methodBean.getMethodName(),50),
				lengthValidated(methodBean.getClassName(),50),
				lengthValidated(methodBean.getPackageName(),255));
	}

	@Override
	public List<String> showTables(){
		String sql= "SELECT tablename FROM sys.systables WHERE tabletype='T'";
		return saJdbcTemplate.query(sql, new SingleColumnRowMapper<String>());
	}

	@Override
	public boolean contain(MethodBean methodBean) {
		String sql = "SELECT * FROM sa_method WHERE methodParameters=? AND methodName=? AND className=? AND packageName=? FETCH FIRST ROW ONLY";
		SqlRowSet l = saJdbcTemplate.queryForRowSet(sql, 
				methodBean.getMethodParameters(),
				methodBean.getMethodName(),
				methodBean.getClassName(),
				methodBean.getPackageName());
		return l.next();
	}

	@Override
	public List<MethodBean> findSimilar(MethodBean methodBean) {
		String sql = "SELECT * FROM sa_method WHERE methodName=? AND className=? AND packageName=?";
		BeanPropertyRowMapper<MethodBean> MethodBeanMapper = BeanPropertyRowMapper.newInstance(MethodBean.class);
		List<MethodBean> l = saJdbcTemplate.query(
				sql,
				new Object[]{
					methodBean.getMethodName(),
					methodBean.getClassName(),
					methodBean.getPackageName()},
				MethodBeanMapper);
				;
		return l;
	}

	@Override
	public void addMethodEvent(MethodEvent methodEvent){
		addMethodEvent(
				methodEvent.getMethodBean(),
				methodEvent.getUserid(),
				methodEvent.getSessionId(),
				methodEvent.getDuration(),
				methodEvent.getExceptionType(),
				methodEvent.getExceptionMessage(),
				methodEvent.getExceptionCause());
	}
	
	@Override
	public void addMethodEvent(MethodBean methodBean, String username, String sessionId, long duration) {
		addMethodEvent(methodBean, username, sessionId, duration, null, null, null);
	}

	@Override
	public void addMethodEvent(MethodBean methodBean, String username, String sessionId, long duration, Throwable throwable) {
		addMethodEvent(methodBean, username, sessionId, duration, throwable.getClass().getName(),throwable.getMessage(),throwable.getCause().getClass().getName());
	}

	@Override
	public void addMethodEvent(MethodBean methodBean, String username, String sessionId, long duration, String exType, String exMessage, String exCause) {
		String sql = "INSERT INTO  methodUsage(methodParameters,methodName,className,packageName,userid,sessionId,duration,ExType,ExMessage,ExCause,datetime) VALUES (?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
		saJdbcTemplate.update(
				sql,
				lengthValidated(methodBean.getMethodParameters(),255),
				lengthValidated(methodBean.getMethodName(),50),
				lengthValidated(methodBean.getClassName(),50),
				lengthValidated(methodBean.getPackageName(),255),
				lengthValidated(username,255),
				lengthValidated(sessionId,255),
				duration,
				lengthValidated(exType,255),
				lengthValidated(exMessage,255),
				lengthValidated(exCause,255));
	}

	@Override
	public void setExist(boolean exist, MethodBean methodBean) {
		String sql = "update sa_method set exist = ? WHERE methodParameters=? AND methodName=? AND className=? AND packageName=? ";
		saJdbcTemplate.update(
				sql,
				exist,
				methodBean.getMethodParameters(),
				methodBean.getMethodName(),
				methodBean.getClassName(),
				methodBean.getPackageName() );
	}

	@Override
	public boolean isExist(MethodBean methodBean) {
		String sql = "select exist from sa_method WHERE methodParameters=? AND methodName=? AND className=? AND packageName=? ";
		Boolean exist = saJdbcTemplate.queryForObject(
				sql,
				Boolean.class,
				methodBean.getMethodParameters(),
				methodBean.getMethodName(),
				methodBean.getClassName(),
				methodBean.getPackageName() );
		return exist;
	}

	@Override
	public void setAllMethodExist(boolean exist) {
		String sql = "update sa_method set exist = ?";
		saJdbcTemplate.update(
				sql,
				exist
				);
	}
	
	public String lengthValidated(String string, int validLength){
		if(string==null) return null;
		if(string.length()>validLength){
			return string.substring(0, validLength);
		} else return string;
	}
}
