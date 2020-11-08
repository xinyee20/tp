package team.serenity.testutil;

import static team.serenity.testutil.TypicalGroups.GROUP_G01;
import static team.serenity.testutil.TypicalGroups.GROUP_G02;
import static team.serenity.testutil.TypicalStudent.AARON;
import static team.serenity.testutil.TypicalStudent.BENJAMIN;
import static team.serenity.testutil.TypicalStudent.CATHERINE;

import java.util.ArrayList;
import java.util.Arrays;

import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.managers.LessonManager;
import team.serenity.model.util.UniqueList;


public class TypicalLesson {

    public static final Lesson LESSON_1_1 = new LessonBuilder().withName("1-1")
            .withStudents(AARON, BENJAMIN, CATHERINE)
            .build();

    public static final Lesson LESSON_1_2 = new LessonBuilder().withName("1-2")
            .withStudents(AARON, BENJAMIN, CATHERINE)
            .build();

    public static final Lesson LESSON_2_1 = new LessonBuilder().withName("2-1")
            .withStudents(AARON, BENJAMIN, CATHERINE)
            .build();

    public static final Lesson LESSON_2_2 = new LessonBuilder().withName("2-2")
            .withStudents(AARON, BENJAMIN, CATHERINE)
            .build();

    public static final Lesson LESSON_3_1 = new LessonBuilder().withName("3-1")
            .withStudents(AARON, BENJAMIN, CATHERINE)
            .build();


    private TypicalLesson() {
    }// prevents instantiation

    public static UniqueList<Lesson> getTypicalLessons() {
        UniqueList<Lesson> list = new UniqueLessonList();
        list.setElementsWithList(new ArrayList<>(Arrays.asList(LESSON_1_1, LESSON_1_2)));
        return list;
    }

    public static LessonManager getTypicalLessonManager() {
        LessonManager lessonManager = new LessonManager();
        lessonManager.addNewMapping(GROUP_G01.getGroupName(), GROUP_G01.getLessons());
        lessonManager.addNewMapping(GROUP_G02.getGroupName(), GROUP_G02.getLessons());
        return lessonManager;
    }
}
