package velib.tools;


public class Log {

	public static void v(Object caller, String msg) {
		android.util.Log.v(nameForClass(caller), msg);
	}

	public static void i(Object caller, String msg) {
		android.util.Log.i(nameForClass(caller), msg);
	}

	public static void d(Object caller, String msg) {
		android.util.Log.d(nameForClass(caller), msg);
	}

	public static void w(Object caller, String msg) {
		android.util.Log.w(nameForClass(caller), msg);
	}

	public static void e(Object caller, String msg) {
		android.util.Log.e(nameForClass(caller), msg);
	}

	public static void e(Object caller, String msg, Exception e) {
		android.util.Log.e(nameForClass(caller), msg, e);
	}
	
	private static String nameForClass(Object caller) {
		if(caller instanceof Class<?>) {
			Class<?> callerClass = (Class<?>) caller;
			return callerClass.getSimpleName();
		}
		else {
			return caller.getClass().getSimpleName();
		}
	}
}
