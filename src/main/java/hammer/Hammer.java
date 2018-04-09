package hammer;

public class Hammer {

	/**
	 * gets object of specified type from database
	 *
	 * @param sqlString
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T get(String sqlString, Class<T> clazz) {
		T t = null;
		return t;
	}

	/**
	 * stores into DB
	 *
	 * @param s
	 * @param object
	 *
	 * @return - id of persisted object
	 */
	public static String put(String s, Object object){
		return "";
	}
}
