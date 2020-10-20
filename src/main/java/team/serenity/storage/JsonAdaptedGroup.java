package team.serenity.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.Group;
import team.serenity.model.group.Lesson;
import team.serenity.model.group.Student;
import team.serenity.model.group.StudentInfo;
import team.serenity.model.group.UniqueLessonList;
import team.serenity.model.group.UniqueStudentInfoList;
import team.serenity.model.group.UniqueStudentList;
import team.serenity.model.util.UniqueList;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        this.name = source.getName();
        this.students.addAll(source.getStudents().asUnmodifiableObservableList().stream()
            .map(JsonAdaptedStudent::new)
            .collect(Collectors.toList()));
        this.lessons.addAll(source.getSortedLessons().asUnmodifiableObservableList().stream()
            .map(JsonAdaptedLesson::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {

        String modelName = this.name;

        final List<Student> groupStudents = new ArrayList<>();
        for (JsonAdaptedStudent groupStudent : this.students) {
            groupStudents.add(groupStudent.toModelType());
        }
        final UniqueList<Student> modelStudents = new UniqueStudentList();
        modelStudents.setElementsWithList(new ArrayList<>(groupStudents));

        final UniqueList<StudentInfo> studentsInfo = new UniqueStudentInfoList();

        final List<Lesson> groupLessons = new ArrayList<>();
        for (JsonAdaptedLesson groupLesson : this.lessons) {
            Lesson lessonItem = new Lesson(groupLesson.getName(), studentsInfo);
            groupLessons.add(lessonItem);
        }
        final UniqueList<Lesson> modelLessons = new UniqueLessonList();
        modelLessons.setElementsWithList(new ArrayList<>(groupLessons));

        return new Group(modelName, modelStudents, modelLessons);
    }

}
