package team.serenity.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.serenity.testutil.Assert.assertThrows;
import static team.serenity.testutil.TypicalStudent.AARON;

import org.junit.jupiter.api.Test;

import team.serenity.commons.exceptions.IllegalValueException;
import team.serenity.model.group.student.Student;

class JsonAdaptedStudentTest {

    @Test
    public void constructor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedStudent(null));
    }

    @Test
    public void toModelType() throws Exception {
        Student typicalStudent = AARON;
        JsonAdaptedStudent adaptedAaron = new JsonAdaptedStudent(typicalStudent);
        JsonAdaptedStudent adaptedAaronWithStrings = new JsonAdaptedStudent("Aaron Tan", "A0123456A");
        assertEquals(typicalStudent, adaptedAaron.toModelType());
        assertEquals(typicalStudent, adaptedAaronWithStrings.toModelType());
    }

    @Test
    public void toModelType_emptyNameThrowsIllegalValueException() throws Exception {
        String emptyName = "";
        String emptyWithSpace = " ";
        String validMatric = "A0123456A";
        JsonAdaptedStudent student = new JsonAdaptedStudent(emptyName, validMatric);
        JsonAdaptedStudent anotherStudent = new JsonAdaptedStudent(emptyWithSpace, validMatric);
        assertThrows(IllegalValueException.class, () -> student.toModelType());
        assertThrows(IllegalValueException.class, () -> anotherStudent.toModelType());
    }

    @Test
    public void toModelType_emptyMatricThrowsIllegalValueException() throws Exception {
        String validName = "Aaron Tan";
        String emptyMatric = "";
        String emptyMatricWithSpace = " ";
        JsonAdaptedStudent student = new JsonAdaptedStudent(validName, emptyMatric);
        JsonAdaptedStudent anotherStudent = new JsonAdaptedStudent(validName, emptyMatricWithSpace);
        assertThrows(IllegalValueException.class, () -> student.toModelType());
        assertThrows(IllegalValueException.class, () -> anotherStudent.toModelType());
    }

    @Test
    public void toModelType_invalidMatricThrowsIllegalValueException() throws Exception {
        String validName = "Aaron Tan";
        String invalidMatricAdditionalNumber = "A12345678R";
        JsonAdaptedStudent student = new JsonAdaptedStudent(validName, invalidMatricAdditionalNumber);
        assertThrows(IllegalValueException.class, () -> student.toModelType());
    }
}
