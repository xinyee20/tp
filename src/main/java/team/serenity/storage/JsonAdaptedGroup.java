package team.serenity.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.Group;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.Lesson;
import team.serenity.model.group.lesson.UniqueLessonList;
import team.serenity.model.group.student.Student;
import team.serenity.model.group.student.UniqueStudentList;
import team.serenity.model.group.studentinfo.StudentInfo;
import team.serenity.model.group.studentinfo.UniqueStudentInfoList;
import team.serenity.model.util.UniqueList;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final GroupName name;
    private final List<String> students = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        this.name = source.getGroupName();
        this.students.addAll(source.getStudents().asUnmodifiableObservableList().stream()
            .map(this::convertStudentToString)
            .collect(Collectors.toList()));
        this.lessons.addAll(source.getSortedLessons().asUnmodifiableObservableList().stream()
            .map(JsonAdaptedLesson::new)
            .collect(Collectors.toList()));
    }

    private String convertStudentToString(Student s) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writeValueAsString(s);
            return jsonInString;
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IOException {

        String modelName = this.name.toString();
        ObjectMapper mapper = new ObjectMapper();
        final List<Student> groupStudents = new ArrayList<>();
        for (String student : this.students) {
            groupStudents.add(mapper.readValue(modelName, Student.class));
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
