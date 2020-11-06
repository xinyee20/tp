package team.serenity.commons.core.sorter;

import java.util.Comparator;

import team.serenity.model.group.lesson.Lesson;

public class LessonSorter implements Comparator<Lesson> {

    @Override
    public int compare(Lesson lessonOne, Lesson lessonTwo) {
        String lesOne = lessonOne.getLessonName().lessonName;
        int lesOneLen = lesOne.length();
        String lesTwo = lessonTwo.getLessonName().lessonName;
        int lesTwoLen = lesTwo.length();
        int minLength = Math.min(lesOneLen, lesTwoLen);
        for (int i = 0; i < minLength; i++) {
            int lesOneChar = lesOne.charAt(i);
            int lesTwoChar = lesTwo.charAt(i);

            if (lesOneChar != lesTwoChar) {
                return lesOneChar - lesTwoChar;
            }
        }

        if (lesOneLen != lesTwoLen) {
            return lesOneLen - lesTwoLen;
        } else {
            return 0;
        }
    }
}
