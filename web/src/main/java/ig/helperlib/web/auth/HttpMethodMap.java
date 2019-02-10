package ig.helperlib.web.auth;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class HttpMethodMap {
    public static class AnyHttpMethodMap extends HttpMethodMap {
        @Override
        public boolean isHttpMethodSupported(String method) {
            return true;
        }
    }

    public static class SpecificHttpMethodMap extends HttpMethodMap {
        private final List<String> httpMethods;

        public SpecificHttpMethodMap(String... httpMethods) {
            this.httpMethods = Arrays.asList(httpMethods);
        }

        @Override
        public boolean isHttpMethodSupported(String method) {
            return ListUtils.indexOf(httpMethods, x -> StringUtils.equalsIgnoreCase(x, method)) >= 0;
        }
    }

    public abstract boolean isHttpMethodSupported(String method);

    public static HttpMethodMap any() {
        return new AnyHttpMethodMap();
    }

    public static HttpMethodMap specific(String... httpMethods) {
        return new SpecificHttpMethodMap(httpMethods);
    }

    public static HttpMethodMap crudRead() {
        return new SpecificHttpMethodMap(HttpMethods.GET);
    }

    public static HttpMethodMap crudWrite() {
        return new SpecificHttpMethodMap(HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE);
    }
}
