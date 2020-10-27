package team.serenity.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.util.UniqueList;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String groupName;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        this.groupName = source.getGroupName().toString();
        this.students.addAll(source.getStudents().asUnmodifiableObservableList().stream()
            .map(JsonAdaptedStudent::new)
            .collect(Collectors.toList()));
        this.lessons.addAll(source.getSortedLessons().asUnmodifiableObservableList().stream()
            .map(JsonAdaptedLesson::new)
            .collect(Collectors.toList()));
    }

    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("groupName") String name,
        @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        this.groupName = name;
        this.lessons.addAll(lessons);
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {

        if (this.groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                GroupName.class.getSimpleName()));
        }

        if (this.students == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                UniqueStudentList.class.getSimpleName()));
        }

        if (this.lessons == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                UniqueLessonList.class.getSimpleName()));
        }

        if (!GroupName.isValidName(this.groupName)) {
            throw new IllegalValueException(GroupName.MESSAGE_CONSTRAINTS);
        }

        String modelName = this.groupName;
        final List<Student> groupStudents = new ArrayList<>();

        for (JsonAdaptedStudent student : this.students) {
            Student studentModel = student.toModelType();
            groupStudents.add(student.toModelType());
        }

        final UniqueList<Student> modelStudents = new UniqueStudentList();
        modelStudents.setElementsWithList(new ArrayList<>(groupStudents));

        final List<Lesson> groupLessons = new ArrayList<>();
        for (JsonAdaptedLesson groupLesson : this.lessons) {
            Lesson lessonItem = groupLesson.toModelType();
            groupLessons.add(lessonItem);
        }

        final UniqueList<Lesson> modelLessons = new UniqueLessonList();
        modelLessons.setElementsWithList(new ArrayList<>(groupLessons));

        return new Group(modelName, modelStudents, modelLessons);
    }

}
