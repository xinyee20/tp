package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.serenity.Class;
import seedu.address.model.serenity.ClassList;
import seedu.address.model.serenity.Group;
import seedu.address.model.serenity.Student;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedClass> classes = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("name") String name,
        @JsonProperty("students") List<JsonAdaptedStudent> students,
        @JsonProperty("classes") List<JsonAdaptedClass> classes) {
        this.name = name;
        if (students != null) {
            this.students.addAll(students);
        }
        if (classes != null) {
            this.classes.addAll(classes);
        }
    }

    public JsonAdaptedGroup(Group source) {
        name = source.getName();
        students.addAll(source.getStudents().stream()
            .map(JsonAdaptedStudent::new)
            .collect(Collectors.toList()));
        classes.addAll(source.getClasses().stream()
            .map(JsonAdaptedClass::new)
            .collect(Collectors.toList()));
    }

    public Group toModelType() throws IllegalValueException {

        String modelName = name;

        final List<Student> groupStudents = new ArrayList<>();
        for (JsonAdaptedStudent groupStudent : students) {
            groupStudents.add(groupStudent.toModelType());
        }
        final Set<Student> modelStudents = new HashSet<>(groupStudents);

        final List<Class> groupClasses = new ArrayList<>();
        for (JsonAdaptedClass groupClass : classes) {
            groupClasses.add(groupClass.toModelType());
        }
        final Set<Class> modelClasses = new HashSet<>(groupClasses);

        return new Group(modelName, modelStudents, modelClasses);
    }

}
