package fi.tuni.prog3.sisu;


/**
 * A class for saving course's information. A {@link CourseUnit} object will be created 
 * only when a student has marked the course as completed. A {@link CourseUnit} 
 * object will store information about the credits and the grade that the 
 * student has got from the course. In the GUI and in the
 * {@link DegreeObjectData#degreeProgrammes} map the courses will be handled as
 * a {@link StudyModule} object.
 */
public class CourseUnit {
    
    private final String name;
    private final String code;
    private final int credits;
    private final int grade;

    /**
     * Constructs a {@link CourseUnit} object. 
     * @param name course's name.
     * @param code course's code.
     * @param credits credits that the student has got from the course.
     * @param grade grade that the student has got from the course.
     */
    public CourseUnit(String name, String code, int credits, int grade) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.grade = grade;
    }
    
    /**
     * Returns the name of the course.
     * @return the name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the code of the course.
     * @return the code of the course.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the credits that the student has got from the course.
     * @return the credits that the student has got from the course.
     */
    public int getCredits() {
        return credits;
    }
    
    /**
     * Returns the grade that the student has got from the course.
     * @return the grade that the student has got from the course.
     */
    public int getGrade() {
        return grade;
    }
    
}
