import java.util.ArrayList;
// Testing simulator without user
public class SimTestRun implements SysOutPrint {
    void run() {
        // Instantiate the objects and put them in an ArrayList of objects
        ArrayList<FNDC> dealerships = new ArrayList<>();

        dealerships.add(new FNDC("North_FNDC"));
        dealerships.add(new FNDC("South_FNDC"));
        // Instantiate the Tracker
        Tracker tracker = new Tracker();

        Enums.daysInWeek day;

        for(int i = 1;i < 31;i++) {
            new Log(dealerships.get(0).name, i);

            day = Enums.daysInWeek.values()[i%7];

            Log.log("\t\t\t\t  " + day);
            runFNDC(dealerships, day, i, tracker);
        }
    }

    // Normal day execution for normal workdays(Logging as well of course!)
    public static void runFNDC(ArrayList<FNDC> dealerships, Enums.daysInWeek d, int x, Tracker t) {
        System.out.println("\n\t\t\t*** FNCD Day " + x + " ***");
        for(int i = 0; i < dealerships.size();i++) {
            dealerships.get(i).opening(d);
            dealerships.get(i).washing(d);
            dealerships.get(i).repairing(d);
            dealerships.get(i).raceDay(d);
            dealerships.get(i).selling(d);
            dealerships.get(i).ending();

            t.end(dealerships.get(i).name, x, dealerships.get(i).getOperatingBudget(), dealerships.get(i).getStaffTotal());
            Log.close();
            if((i+1) != dealerships.size()) {
                Log.addObject(dealerships.get(i+1).name, x);
            }
        }
    }
}
