package team.serenity.testutil.question;

import team.serenity.logic.commands.question.EditQnCommand.EditQuestionDescriptor;
import team.serenity.model.group.GroupName;
import team.serenity.model.group.lesson.LessonName;
import team.serenity.model.group.question.Description;
import team.serenity.model.group.question.Question;

/**
 * A utility class to help with building EditQuestionDescriptor objects.
 */
public class EditQuestionDescriptorBuilder {

    private EditQuestionDescriptor descriptor;

    public EditQuestionDescriptorBuilder() {
        descriptor = new EditQuestionDescriptor();
    }

    public EditQuestionDescriptorBuilder(EditQuestionDescriptor descriptor) {
        this.descriptor = new EditQuestionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditQuestionDescriptor} with fields containing {@code question}'s details
     */
    public EditQuestionDescriptorBuilder(Question question) {
        this.descriptor = new EditQuestionDescriptor();
        this.descriptor.setGroupName(question.getGroupName());
        this.descriptor.setLessonName(question.getLessonName());
        this.descriptor.setDescription(question.getDescription());
    }

    /**
     * Sets the {@code groupName} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withGroupName(String groupName) {
        this.descriptor.setGroupName(new GroupName(groupName));
        return this;
    }

    /**
     * Sets the {@code lessonName} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withLessonName(String lessonName) {
        this.descriptor.setLessonName(new LessonName(lessonName));
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withDescription(String description) {
        this.descriptor.setDescription(new Description(description));
        return this;
    }


    public EditQuestionDescriptor build() {
        return this.descriptor;
    }

}
