import javafx.application.Application;

import java.util.ArrayList;
import java.util.Random;
// The simulator class will handle all executed FNDC objects
// and the log/tracker objects
public class Simulator implements SysOutPrint {
    private static final Random random = new Random();
    void run() {
        // Instantiate the objects and put them in an ArrayList of objects
        ArrayList<FNDC> dealerships = new ArrayList<>();

        dealerships.add(new FNDC("North_FNDC"));
        dealerships.add(new FNDC("South_FNDC"));
        // Instantiate the Tracker
        Tracker tracker = new Tracker();

        Enums.daysInWeek day;

        for(int i = 1;i <= 31;i++) {
            new Log(dealerships.get(0).name, i);

            day = Enums.daysInWeek.values()[i%7];

            Log.log("\t\t\t\t  " + day);

            if(i != 31) {
                runFNDC(dealerships, day, i, tracker);
            } else {
                userFNDC(dealerships, day, i, tracker);
            }
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

    // Same thing here but, instead of terminating, this will be the 31st day
    private static void userFNDC(ArrayList<FNDC> dealerships, Enums.daysInWeek d, int x, Tracker t) {
        System.out.println("\n\t\t\t*** FNCD Day " + x + " ***");
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0;i < dealerships.size();i++) {
            dealerships.get(i).opening(d);
            dealerships.get(i).washing(d);
            dealerships.get(i).repairing(d);
            dealerships.get(i).raceDay(d);
            names.add(dealerships.get(i).name);
            Log.close();
            if((i+1) != dealerships.size()) {
                Log.addObject(dealerships.get(i+1).name, x);
            }
        }

        FNDC randFNDC = dealerships.get(random.nextInt(dealerships.size()));
        // Go to a random FNDC object
        Buyer user = userBuyer();

        // user choice
        String userChoice = randFNDC.userSelling(user, names);
        // The user menu where they will be able to go in between objects

        while(!userChoice.equals("Quit")) {
            randFNDC = dealerships.get(names.indexOf(userChoice));
            userChoice = randFNDC.userSelling(user, names);
        }

        for (FNDC dealership : dealerships) {
            Log.addObject(dealership.name, x);
            dealership.ending();
            Log.close();
            t.end(dealership.name, x, dealership.getOperatingBudget(), dealership.getStaffTotal());

        }
        // Log everything from the log-n-.txt files
        Log.simulateResults();
    }

    // The user as a buyer being instantiated outside the fndc object
    private static Buyer userBuyer() {
        return new Buyer("User");
    }
}