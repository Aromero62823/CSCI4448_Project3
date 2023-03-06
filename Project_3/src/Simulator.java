import static java.lang.System.*;
public class Simulator {
    void run() {
        new FNDC();
        Enums.daysInWeek day;
        // Where the 30 days will prompt from the for-loop
        for(int i = 1;i < 31;i++) {
            out.println("\n\t\t\t*** FNCD Day " + i + " ***");
            day = Enums.daysInWeek.values()[i%7];
            out.println("\t\t\t\t" + day);
            FNDC.opening();
            FNDC.washing();
            FNDC.repairing();
            FNDC.raceDay(day);
            FNDC.selling(day);
            FNDC.ending();
        }
    }
}