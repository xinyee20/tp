package team.serenity.commons.core.sorter;

import java.util.Comparator;

import team.serenity.model.group.studentinfo.StudentInfo;

public class StudentInfoSorter implements Comparator<StudentInfo> {

    @Override
    public int compare(StudentInfo studentInfoOne, StudentInfo studentInfoTwo) {
        String infoOne = studentInfoOne.getStudent().getStudentName().fullName;
        int infoOneLen = infoOne.length();
        String infoTwo = studentInfoTwo.getStudent().getStudentName().fullName;
        int infoTwoLen = infoTwo.length();
        int minLength = Math.min(infoOneLen, infoTwoLen);
        for (int i = 0; i < minLength; i++) {
            int infoOneChar = infoOne.charAt(i);
            int infoTwoChar = infoTwo.charAt(i);

            if (infoOneChar != infoTwoChar) {
                return infoOneChar - infoTwoChar;
            }
        }

        if (infoOneLen != infoTwoLen) {
            return infoOneLen - infoTwoLen;
        } else {
            return 0;
        }
    }
}
