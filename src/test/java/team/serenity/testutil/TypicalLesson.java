package team.serenity.testutil;

import static team.serenity.testutil.TypicalGroups.GROUP_C;
import static team.serenity.testutil.TypicalGroups.GROUP_D;

import java.util.ArrayList;
import java.util.Arrays;

import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.managers.LessonManager;
import team.serenity.model.util.UniqueList;


public class TypicalLesson {

    public static final Lesson LESSON_A = new LessonBuilder().withName("4-2")
            .withStudents(
                new Student("Jeffery", "A0000000U"),
                new Student("Luna", "A0111111U"),
                new Student("Queenie", "A0222222U")
            )
            .build();

    public static final Lesson LESSON_B = new LessonBuilder().withName("5-2")
            .withStudents(
                    new Student("Jeffery", "A0000000U"),
                    new Student("Luna", "A0111111U"),
                    new Student("Queenie", "A0222222U")
            )
            .build();

    public static final Lesson LESSON_C = new LessonBuilder().withName("5-1")
            .withStudents(
                new Student("Jeffery", "A0000000U"),
                new Student("Luna", "A0111111U"),
                new Student("Queenie", "A0222222U")
            )
            .build();

    public static final Lesson LESSON_D = new LessonBuilder().withName("5-1")
            .withStudents(
                new Student("Freddie", "A0000000U"),
                new Student("June", "A0101011U")
            )
            .build();

    public static final Lesson LESSON_E = new LessonBuilder().withName("4-1")
            .withStudents(
                    new Student("Freddie", "A0000000U"),
                    new Student("June", "A0101011U")
            )
            .build();


    private TypicalLesson() {
    }// prevents instantiation

    public static UniqueList<Lesson> getTypicalLessons() {
        UniqueList<Lesson> list = new UniqueLessonList();
        list.setElementsWithList(new ArrayList<>(Arrays.asList(LESSON_A, LESSON_B)));
        return list;
    }

    public static LessonManager getTypicalLessonManager() {
        LessonManager lessonManager = new LessonManager();
        lessonManager.addNewMapping(GROUP_C.getGroupName(), GROUP_C.getLessons());
        lessonManager.addNewMapping(GROUP_D.getGroupName(), GROUP_D.getLessons());
        return lessonManager;
    }
}
