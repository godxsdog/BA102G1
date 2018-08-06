package another;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class CompositeQuery {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;
		if ("restKind".equals(columnName) || "RESTREVIEWSTATUS".equals(columnName) || "restNo".equals(columnName) || "restLongTitude".equals(columnName) || "restLatitude".equals(columnName)) // 用於其他
			aCondition = columnName + "=" + value;
		else if ("restName".equals(columnName) || "restAdd".equals(columnName) || "restPhone".equals(columnName) || "restLocate".equals(columnName) || "restIntro".equals(columnName) ) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";
//		if ("empno".equals(columnName) || "sal".equals(columnName) || "comm".equals(columnName) || "deptno".equals(columnName)) // 用於其他
//			aCondition = columnName + "=" + value;
//		else if ("ename".equals(columnName) || "job".equals(columnName)) // 用於varchar
//			aCondition = columnName + " like '%" + value + "%'";
//		else if ("hiredate".equals(columnName))                          // 用於Oracle的date
//			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count1111 = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("restName", new String[] { "餐廳列表名1" });
		map.put("restAdd", new String[] { "餐廳地址1" });
		map.put("restPhone", new String[] { "餐廳電話1" });
		map.put("restKind", new String[] { "1" });
		map.put("RESTREVIEWSTATUS", new String[] { "2" });
		map.put("restLongTitude", new String[] { "111.12345" });
		//map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from rest "
				          + CompositeQuery.get_WhereCondition(map)
				          + "order by restNo";
		System.out.println("●●finalSQL = " + finalSQL);

	}

}
