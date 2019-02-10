package ig.helperlib.fetch;

import lombok.Getter;

public class Fetch implements FetchOptions {
    static final Fetch empty = new Fetch(null, null, null);

    @Getter
    private final String filter;

    @Getter
    private final Sorting sorting;

    @Getter
    private final Paging paging;

    Fetch(String filter, Sorting sorting, Paging paging) {
        this.filter = filter;
        this.sorting = sorting;
        this.paging = paging;
    }

    public static Fetch of() {
        return empty;
    }

    public static Fetch of(String filter) {
        return new Fetch(filter, null, null);
    }

    public static Fetch of(Sorting sorting) {
        return new Fetch(null, sorting, null);
    }

    public static Fetch of(Paging paging) {
        return new Fetch(null, null, paging);
    }

    public Fetch filter(String filter) {
        return new Fetch(filter, null, null);
    }

    public Fetch sort(Sorting sorting) {
        return new Fetch(filter, sorting, paging);
    }

    public Fetch asc(String field) {
        return sort(Sorting.asc(field));
    }

    public Fetch desc(String field) {
        return sort(Sorting.desc(field));
    }

    public Fetch page(Paging paging) {
        return new Fetch(filter, sorting, paging);
    }

    public Fetch page(int startIndex, int size) {
        return page(new Paging(startIndex, size));
    }
}
