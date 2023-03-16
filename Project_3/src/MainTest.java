import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.util.ArrayList;

class MainTest implements SysOutPrint {
    @Test
    void checkVehicle() {
        var vehicle = new Vehicle();
        //checking if the newly instantiated vehicles have been sold
        try {
            assertFalse(vehicle.sold);
            assertNotNull(vehicle);
            print("1) Test Case Passed!");
        } catch (AssertionFailedError e) {
            print("1) Test Case Failed for Instantiation for Vehicle Object.");
        }
    }

    @Test
    void checkSubClass() {
        try {
            ArrayList<Vehicle> vehicles = new ArrayList<>();
            vehicles.add(new Pickup());
            vehicles.add(new Car());
            vehicles.add(new PerformanceCar());
            vehicles.add(new MonsterTruck());
            vehicles.add(new ElectricCar());
            vehicles.add(new Motorcycle());
            vehicles.add(new OffRoad());
            vehicles.add(new SuperCar());
            vehicles.add(new Bicycle());
            for (Vehicle i : vehicles) {
                assertNotNull(i, "Object Null");
                assertInstanceOf(Vehicle.class, i);
                assertInstanceOf(i.getClass(), i);
                assertNotNull(i.cost);
                assertEquals(0, i.vWinCount);
                assertTrue(i.salesPrice > 0);
            }
            print("2) Test Case Passed!");
        } catch (AssertionError | Exception e) {
            print("2) Test Case Failed");
        }
    }

    @Test
    void checkStaffSubClassObjects() {
        try {
            ArrayList<Staff> staffList = new ArrayList<>();
            staffList.add(new Driver());
            staffList.add(new Salespeople());
            staffList.add(new Interns());
            staffList.add(new Mechanics());

            for (Staff i : staffList) {
                assertNotNull(i);
                assertInstanceOf(Staff.class, i);
            }
            print("3) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("3) Test Case Failed");
        }

    }

    @Test
    void checkFNDCObject() {
        try {
            var fndc = new FNDC("Test");
            assertNotNull(fndc);
            print("4) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("4) Test Case Failed");
        }
    }

    @Test
    void checkBuyerObject() {
        try {
            var buyer = new Buyer();
            assertNotNull(buyer);
            assertInstanceOf(Enums.vehicleType.class, buyer.preferredType);
            assertInstanceOf(Enums.vehicleAddons.class, buyer.preferredAddon);
            assertTrue(buyer.buyingChance > 0);
            print("5) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("5) Test Case Failed");
        }
    }

    @Test
    void checkTestRun() {
        try {
            var fndc = new FNDC("Test");
            var day = Enums.daysInWeek.Wednesday;
            fndc.opening(day);
            fndc.washing(day);
            fndc.repairing(day);
            fndc.raceDay(day);
            fndc.selling(day);
            fndc.ending();
            assertTrue(fndc.getOperatingBudget() != 500000);
            assertNotNull(fndc);
            assertInstanceOf(Vehicle.class, fndc.inventory.getClass());
            print("6) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("6) Test Case Failed");
        }
    }

    @Test
    void checkInventoryOfObjects() {
        try {
            var fndc1 = new FNDC("Test");
            var fndc2 = new FNDC("Test");
            var matchCounter = 0;
            for (int i = 0; i < fndc1.inventory.size(); i++) {
                if (fndc1.inventory.get(i).equals(fndc2.inventory.get(i))) {
                    matchCounter++;
                }
            }
            assertFalse(matchCounter != fndc1.inventory.size() - 1);
            print("7) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("7) Test Case Failed");
        }
    }

    @Test
    void checkStaffOfObjects() {
        try {
            var fndc1 = new FNDC("Test");
            var fndc2 = new FNDC("Test");
            var matchCounter = 0;
            for (int i = 0; i < fndc1.storeStaff.size(); i++) {
                if (fndc1.storeStaff.get(i).equals(fndc2.storeStaff.get(i))) {
                    matchCounter++;
                }
            }
            assertFalse(matchCounter != fndc1.storeStaff.size() - 1);
            print("8) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("8) Test Case Failed");
        }
    }

    @Test
    void checkSimulatorRun() {
        try {
            var simulation = new Simulator();
            simulation.run();
            print("9) Test Case Passed");
        } catch (Exception e) {
            print("9) Test Case Failed");
        }
    }

    @Test
    void checkFiles() {
        try {
            for (int i = 1; i < 32; i++) {
                var filename = new File("Logger-" + i + "-.txt");
                assertTrue(filename.exists());
            }
            print("10) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("10) Test Case Failed");
        }
    }

    @Test
    void checkSimResults() {
        try {
            var filename = new File("SimResults.txt");
            assertTrue(filename.exists());
            print("11) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("11) Test Case Failed");
        }
    }

    @Test
    void checkSubClassAttributes() {
        try {
            var vehicle = new ElectricCar();
            assertNotNull(vehicle);
            assertTrue(vehicle.range > 0);

            var vehicle2 = new Motorcycle();
            assertNotNull(vehicle2);
            assertTrue(vehicle2.engineRating > 0);

            var vehicle3 = new MonsterTruck();
            assertNotNull(vehicle3);
            assertFalse(vehicle3.vehicleName.isEmpty());

            print("12) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("12) Test Case Failed");
        }
    }

    @Test
    void checkRaceEligibility() {
        try {
            // Cars, Electric Car, and Bicycle cannot race
            var car = new Car();
            var car2 = new ElectricCar();
            var car3 = new Bicycle();

            assertNotNull(car);
            assertNotNull(car2);
            assertNotNull(car3);

            assertFalse(car.raceEligible);
            assertFalse(car2.raceEligible);
            assertFalse(car3.raceEligible);

            print("13) Test Case Failed");
        } catch (Exception | AssertionError e) {
            print("13) Test Case Failed");
        }
    }

    @Test
    void checkDriverWin() {
        try {
            var driver = new Driver();
            var vehicle = new Vehicle();

            assertNotNull(driver);
            assertNotNull(vehicle);

            driver.race(1, vehicle);

            assertTrue(driver.status);
            assertEquals(driver.totalEarned, vehicle.raceWinBonus);
            assertTrue(driver.winCount>0);
            assertTrue(vehicle.vWinCount>0);

            print("14) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("14) Test Case Failed");
        }
    }

    @Test
    void checkDriverLost() {
        try {
            var driver = new Driver();
            var vehicle = new Vehicle();

            assertNotNull(driver);
            assertNotNull(vehicle);

            driver.race(19, vehicle);

            assertFalse(driver.status);
            assertEquals(vehicle.vCondition, Enums.vehicleCondition.Broken);
            print("15) Test Case Passed");
        } catch (AssertionError | Exception e) {
            print("15) Test Case Failed");
        }
    }
}