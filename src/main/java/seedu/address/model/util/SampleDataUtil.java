package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySerenity;
import seedu.address.model.Serenity;
import seedu.address.model.group.Group;
import seedu.address.model.group.Lesson;
import seedu.address.model.group.Score;
import seedu.address.model.group.Student;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

    public static Group[] getSampleGroups() {
        Set<Score> scores = getScoreSet(new Student("John", "E0123456"),
            new Student("James", "E02030303"));
        Set<Student> students = getStudentSet(new Student("John", "E0123456"),
            new Student("James", "E02030303"));
        Set<Lesson> lessons = new HashSet<>();
        lessons.add(new Lesson("1-1", scores));
        return new Group[] {new Group("G04", students, lessons)};
    }

    public static ReadOnlySerenity getSampleSerenity() {
        Serenity sampleS = new Serenity();
        for (Group sampleGroup : getSampleGroups()) {
            sampleS.addGroup(sampleGroup);
        }
        return sampleS;
    }

    public static Set<Score> getScoreSet(Student... students) {
        Set<Score> scores = new HashSet<>();
        for (Student student : students) {
            scores.add(new Score(student));
        }
        return scores;
    }

    /**
     * Returns a student set containing the list of strings given.
     */
    public static Set<Student> getStudentSet(Student... students) {
        return Arrays.stream(students)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a class set containing the list of strings given.
     */
    public static Set<Class> getClassSet(Class... classes) {
        return Arrays.stream(classes)
            .collect(Collectors.toSet());
    }
}
