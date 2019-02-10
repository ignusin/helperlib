package ig.helperlib.utils;

public class Iterables {
    @FunctionalInterface
    public interface IndexedConsumer<T> {
        void action(T element, int index);
    }

    public static <T> void forEach(Iterable<T> iterable, IndexedConsumer<? super T> consumer) {
        int n = 0;

        for (T it: iterable) {
            consumer.action(it, n);
            ++n;
        }
    }
}
