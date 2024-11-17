package edu.school.e_EducationSystem.exceptions;

public class CoursException extends Exception {
    public CoursException(String thisCoursIsNotFound) {
        super(thisCoursIsNotFound);
    }
}
