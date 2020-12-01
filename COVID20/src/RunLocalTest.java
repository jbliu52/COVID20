import static org.hamcrest.Matchers.hasProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
        	for (Failure failure : result.getFailures()) {
            	System.out.println(failure.toString());
        	}
        }
    }
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;
        
        @SuppressWarnings("FieldCanBeLocal")
	    private ByteArrayInputStream testIn;

	    @SuppressWarnings("FieldCanBeLocal")
	    private ByteArrayOutputStream testOut;
        
	    @Before
	    public void outputStart() {
		    testOut = new ByteArrayOutputStream();
		    System.setOut(new PrintStream(testOut));
	    }
        
        @After
	    public void restoreInputAndOutput() {
		    System.setIn(originalSysin);
		    System.setOut(originalOutput);
	    }
        
        private String getOutput() {
		    return testOut.toString();
	    }
        
        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        } 
        
        /*===TESTS BEGIN==*/
        @Test(timeout = 1000)
        public void ClientTest() {
            String[] names = {};
            Class<?>[] classes = {};
        	runTest("Client", names, classes);
        }
        
        @Test(timeout = 1000)
        public void ProfileTest() {
            String[] names = {"firstName", "lastName", "preferredName", "age", "college", "gradClass", "MAJORS", "interests", "aboutMeSection", "email", "phoneNumber"};
            Class<?>[] classes = {String.class, String.class, String.class, int.class, String.class, String.class, String[].class, String.class, String.class, String.class, String.class};
        	runTest("Profile", names, classes);
        }
        
        @Test(timeout = 1000)
        public void UserTest() {
            String[] names = {"user", "pwd", "profile", "friends", "sent", "received"};
            Class<?>[] classes = {String.class, String.class, Profile.class, HashSet.class, HashSet.class, HashSet.class};
        	runTest("User", names, classes);
            
        }
        /*===TESTS END==*/
        
        private void runTest(String className, String[] names, Class<?>[] classes) {
            Class<?> clazz = null;
            int modifiers;
            Class<?> superclass;
            
            try {
            	clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                Assert.fail("Ensure that `Client` exists!");
            }
            modifiers = clazz.getModifiers();
            superclass = clazz.getSuperclass();
            
            Assert.assertTrue(String.format("Ensure that `%s` is `public`!", className), Modifier.isPublic(modifiers));
            Assert.assertEquals(String.format("Ensure that `%s` extends `Object`!", className), Object.class, superclass);

            for(int i = 0; i < names.length; i++) {
            	Field f = null;
                try {
                	f = clazz.getDeclaredField(names[i]);
                } catch (NoSuchFieldException e) {
                    Assert.fail(String.format("Ensure that `%s` in `%s` exists!", names[i], className));
                }
                Assert.assertTrue(String.format("Ensure that `%s` is `private`!", f.getName()), Modifier.isPrivate(f.getModifiers()));
                Assert.assertEquals(String.format("Ensure that `%s` is a `%s`!", f.getName(), classes[i].getCanonicalName()), f.getType(), classes[i]);
            }
        }
        
    }
    
}