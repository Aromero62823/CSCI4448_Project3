import java.text.DecimalFormat;

public class Tracker implements SysOutPrint {
    private final DecimalFormat df = new DecimalFormat("0.00");

    int day;
    double operatingBudget;
    double staffTotal;
    // Constructor
    Tracker() {
        day = 0;
        operatingBudget = 0.00;
        staffTotal = 0.00;
    }
    // method to print the tracked day, total made by both staff and FNDC
    public void end(String name, int day, double operatingBudget, double staffTotal) {
        this.day = day;
        this.operatingBudget = operatingBudget;
        this.staffTotal = staffTotal;

        print("Tracker: Day " + day);
        print("Total made by staff: $" + df.format(staffTotal));
        print("Total made by " + name + ": $" + df.format(operatingBudget));
    }

}
