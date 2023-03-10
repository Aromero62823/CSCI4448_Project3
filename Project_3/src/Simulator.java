public class Simulator implements SysOutPrint {
    void run() {
        FNDC fndc = new FNDC();
        //
        //FNDC fndc1 = new FNDC();
        //
        Enums.daysInWeek day;
        // Where the 30 days will prompt from the for-loop
        for(int i = 1;i < 31;i++) {
            print("\n\t\t\t*** FNCD Day " + i + " ***");
            day = Enums.daysInWeek.values()[i%7];
            print("\t\t\t\t" + day);
            fndc.opening();
            fndc.washing();
            fndc.repairing();
            fndc.raceDay(day);
            fndc.selling(day);
            fndc.ending();
        }
    }
}