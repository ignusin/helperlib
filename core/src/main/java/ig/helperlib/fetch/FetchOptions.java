package ig.helperlib.fetch;

public interface FetchOptions {
    String getFilter();
    Sorting getSorting();
    Paging getPaging();
}
