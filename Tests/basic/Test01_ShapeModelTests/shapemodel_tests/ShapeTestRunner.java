package shapemodel_tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class ShapeTestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(shapemodel_tests.ShapeTestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("Passed all " + result.getRunCount() + " Shape model JUnit tests!");
            System.exit(0);
        } else {
            System.out.println("Failed " + result.getFailureCount() + " out of " + result.getRunCount() + " Shape model JUnit tests!");
            System.exit(1);
        }
    }

}
