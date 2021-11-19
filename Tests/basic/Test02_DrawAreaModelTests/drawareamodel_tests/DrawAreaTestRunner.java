package drawareamodel_tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class DrawAreaTestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DrawAreaModelTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("Passed all " + result.getRunCount() + " Draw area model JUnit tests!");
            System.exit(0);
        } else {
            System.out.println("Failed " + result.getFailureCount() + " out of " + result.getRunCount() + " Draw area model JUnit tests!");
            System.exit(1);
        }
    }

}
