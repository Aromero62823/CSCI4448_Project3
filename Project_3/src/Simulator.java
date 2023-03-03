public class Simulator {
    void run() throws InterruptedException {
        // Initializing the object
        FNDC fndc = new FNDC();

        // Where the 30 days will prompt from the for-loop
        for(int i = 1;i < 31;i++) {
            System.out.println("\n\t\t\t*** FNCD Day " + i + " ***");
            System.out.println(getDay(i%7));
            if(i%7 != 0)
            {
                fndc.Opening();
                fndc.Washing();
                fndc.Repairing();
                fndc.Selling(getDay(i%7));
                fndc.Ending();
            }
        }
    }
    public static String getDay(int x) {
        String y = "";
        switch (x) {
            case 0 -> y = "\t\tSunday: \t--- Closed ---";
            case 1 -> y = "\t\t\t\tMonday";
            case 2 -> y = "\t\t\t\tTuesday";
            case 3 -> y = "\t\t\t\tWednesday";
            case 4 -> y = "\t\t\t\tThursday";
            case 5 -> y = "\t\t\t\tFriday";
            case 6 -> y = "\t\t\t\tSaturday";
        }
        return y;
    }
}