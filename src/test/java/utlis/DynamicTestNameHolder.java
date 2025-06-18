package utlis;

public class DynamicTestNameHolder {
    private static Object value;

    public static void set(Object val) {
        value = val;
    }

    public static Object get() {
        return value;
    }

    public static void clear() {
        value = null;
    }
}
