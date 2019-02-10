package ig.helperlib.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Exceptions {
	public static String stackTraceAsString(Throwable ex) {
		return Invoke.exceptional(() -> {
			try (StringWriter sw = new StringWriter()) {
				try (PrintWriter pw = new PrintWriter(sw)) {
					ex.printStackTrace(pw);
					return sw.toString();
				}
			}
		});
	}
}
