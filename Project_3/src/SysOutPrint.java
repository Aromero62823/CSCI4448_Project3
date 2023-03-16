import static java.lang.System.*;
public interface SysOutPrint {
    default void print(String message) {
        out.println(message);
    }
}