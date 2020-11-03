package team.serenity.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public static enum UiAction {
        ADD_GRP,
        DEL_GRP,
        DEL_LSN,
        EXIT,
        FLAG_ATT,
        SHOW_HELP,
        VIEW_ATT,
        VIEW_GRP,
        VIEW_LSN,
        VIEW_QN,
        VIEW_SCORE,
        REFRESH_TABLE,
        NO_ACTION,
    }

    private final String feedbackToUser;
    private final UiAction uiAction;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, UiAction action) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiAction = action;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser) {
        this.uiAction = UiAction.NO_ACTION;
        this.feedbackToUser = requireNonNull(feedbackToUser);
    }

    public String getFeedbackToUser() {
        return this.feedbackToUser;
    }

    public UiAction getUiAction() {
        return this.uiAction;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return this.feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && this.uiAction == otherCommandResult.uiAction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.feedbackToUser, this.uiAction);
    }
}
