package team.serenity.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalGroups.GROUP_G04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.Group;
import team.serenity.testutil.GroupBuilder;

class JsonAdaptedGroupTest {

    private static final String VALID_NAME = GROUP_G04.getGroupName().toString();
    private static final String INVALID_NAME_NOCAPS = "g01";
    private static final String INVALID_NAME_SPACE = "G 01";
    private static final String INVALID_NAME_MULTIPLE_STRINGS = "G01 G05";
    private static final String INVALID_NAME_EXTRA_DIGITS = "G011";
    private static final List<JsonAdaptedStudent> VALID_STUDENTS = GROUP_G04.getStudents()
            .asUnmodifiableObservableList()
            .stream()
            .map(JsonAdaptedStudent::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedLesson> VALID_CLASSES = GROUP_G04.getLessons()
            .asUnmodifiableObservableList()
            .stream()
            .map(JsonAdaptedLesson::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        Group typicalGroup = new GroupBuilder().build();
        JsonAdaptedGroup group = new JsonAdaptedGroup(typicalGroup);
        assertEquals(typicalGroup, group.toModelType());
    }

    @Test
    public void toModelType_null_throwsIllegalValueException() {
        JsonAdaptedGroup nullGroupName = new JsonAdaptedGroup(null, new ArrayList<>());
        JsonAdaptedGroup invalidGroup = new JsonAdaptedGroup(INVALID_NAME_NOCAPS, new ArrayList<>());
        JsonAdaptedGroup invalidGroupTwo = new JsonAdaptedGroup(INVALID_NAME_EXTRA_DIGITS, new ArrayList<>());
        JsonAdaptedGroup invalidGroupThree = new JsonAdaptedGroup(INVALID_NAME_MULTIPLE_STRINGS, new ArrayList<>());
        JsonAdaptedGroup invalidGroupFour = new JsonAdaptedGroup(INVALID_NAME_SPACE, new ArrayList<>());
        assertThrows(IllegalValueException.class, () -> nullGroupName.toModelType());
        assertThrows(IllegalValueException.class, () -> invalidGroup.toModelType());
        assertThrows(IllegalValueException.class, () -> invalidGroupTwo.toModelType());
        assertThrows(IllegalValueException.class, () -> invalidGroupThree.toModelType());
        assertThrows(IllegalValueException.class, () -> invalidGroupFour.toModelType());
    }

    @Test
    public void toModelType_nullGroup_throwsIllegalValueException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedGroup(null));
    }
}
