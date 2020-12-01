import java.io.Serializable;

/**
 * Project 5 - Profile.java
 * <p>
 * A class representing the profile of a user on Corona Connect!
 * <p>
 * a list of your sources of help (if any)
 *
 * @author Riya Verma and Katie Yi
 * @version November 19, 2020
 */
public class Profile implements Serializable {
	
    // Fields
    private String firstName;
    private String lastName;
    private String preferredName; //nickname
    private int age;
    private String college; //dropdown in GUI
    private String gradClass; //dropdown in GUI
    private static String[] MAJORS = {"Computer Science", "Data Science"}; // list of majors for dropdown
    private String interests; //big JTextField
    private String aboutMeSection; //paragraph style JTextField (prompt user for social media)
    private String email; //part of Contact Info & use the given email when creating account
    private String phoneNumber;
    //private static final String[] SCHOOLS = {"Purdue", "IU", "PogU"}; //list of schools for dropdown


    //Constructors
    //Constructor for REQUIRED ONLY
    public Profile(String firstName, String lastName, int age, String gradClass, String college, String interests, String[] MAJORS,
                   String aboutMeSection, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.college = college;
        this.gradClass = gradClass;
        this.interests = interests;
        this.MAJORS = new String[2]; //will have to change later to accommodate for more majors
        this.aboutMeSection = aboutMeSection;
        this.email = email;
    }

    //Constructor for required and optional fields to fill
    public Profile(String firstName, String lastName, String preferredName, int age, String college, String gradClass,
                   String[] MAJORS, String interests, String aboutMeSection, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.age = age;
        this.college = college;
        this.gradClass = gradClass;
        this.interests = interests;
        this.MAJORS = new String[2]; //will have to change later to accommodate for more majors
        this.aboutMeSection = aboutMeSection;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public static String[] getMAJORS() {
        return MAJORS;
    }

    public static void setMAJORS(String[] MAJORS) {
        Profile.MAJORS = MAJORS;
    }

    public String getGradClass() {
        return gradClass;
    }

    public void setGradClass(String gradClass) {
        this.gradClass = gradClass;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getAboutMeSection() {
        return aboutMeSection;
    }

    public void setAboutMeSection(String aboutMeSection) {
        this.aboutMeSection = aboutMeSection;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
