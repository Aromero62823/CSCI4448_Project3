// Citation from Bruce's code, will make my code much tighter and cleaner
public interface SysOutPrint {
        default void print(String message) {System.out.println(message);}
}
