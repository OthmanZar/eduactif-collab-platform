package edu.school.e_EducationSystem.exceptions;

public class SchoolYearException extends Exception {
    public SchoolYearException(String thisSchoolYearNameNotFound) {
        super(thisSchoolYearNameNotFound);
    }
}
