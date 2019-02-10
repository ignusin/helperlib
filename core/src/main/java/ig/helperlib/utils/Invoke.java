package ig.helperlib.utils;

public class Invoke {
	@FunctionalInterface
	public interface ExceptionalAction0 {
		void invoke() throws Throwable;
	}
	
	@FunctionalInterface
	public interface ExceptionalAction1<T1> {
		void invoke(T1 arg1) throws Throwable;
	}
	
	@FunctionalInterface
	public interface ExceptionalFunc0<R> {
		R invoke() throws Exception;
	}
	
	
	public static void exceptional(ExceptionalAction0 action) {
		try {
			action.invoke();
		}
		catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static <R> R exceptional(ExceptionalFunc0<R> func) {
		try {
			return func.invoke();
		}
		catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
}
