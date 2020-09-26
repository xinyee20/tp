package seedu.address.model.serenity;

import java.util.ArrayList;
import java.util.List;

public class StudentList {

    private List<Student> students;

    public StudentList() {
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }
}
