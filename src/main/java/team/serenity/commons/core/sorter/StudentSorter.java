package team.serenity.commons.core.sorter;

import java.util.Comparator;

import team.serenity.model.group.student.Student;

public class StudentSorter implements Comparator<Student> {

    @Override
    public int compare(Student studentOne, Student studentTwo) {
        String sOne = studentOne.getStudentName().fullName;
        int sOneLen = sOne.length();
        String sTwo = studentTwo.getStudentName().fullName;
        int sTwoLen = sTwo.length();
        int minLength = Math.min(sOneLen, sTwoLen);
        for (int i = 0; i < minLength; i++) {
            int lesOneChar = sOne.charAt(i);
            int lesTwoChar = sTwo.charAt(i);

            if (lesOneChar != lesTwoChar) {
                return lesOneChar - lesTwoChar;
            }
        }

        if (sOneLen != sTwoLen) {
            return sOneLen - sTwoLen;
        } else {
            return 0;
        }
    }
}
