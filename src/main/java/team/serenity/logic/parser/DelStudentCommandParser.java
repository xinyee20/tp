package team.serenity.logic.parser;

import static team.serenity.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import team.serenity.logic.commands.DelStudentCommand;
import team.serenity.logic.parser.exceptions.ParseException;
import team.serenity.model.group.GroupContainsKeywordPredicate;

public class DelStudentCommandParser implements Parser<DelStudentCommand> {

    private final ParseException deleteStudentCommandParserException = new ParseException(
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelStudentCommand.MESSAGE_USAGE));

    @Override
    public DelStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GRP,
            CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_GRP, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ID)
            || !argMultimap.getPreamble().isEmpty()) {
            throw deleteStudentCommandParserException;
        }

        String[] grpKeywordArray = argMultimap.getValue(CliSyntax.PREFIX_GRP).get().split("\\s+");
        String[] studentNameArray = argMultimap.getValue(CliSyntax.PREFIX_NAME).get().split("\\s+");
        String[] studentIdArray = argMultimap.getValue(CliSyntax.PREFIX_ID).get().split("\\s+");

        //if id or group keyword is more than 1, or if student name has more than 10 letters, throw exception
        boolean matchesGrp = grpKeywordArray.length == 1;
        boolean matchesId = studentIdArray.length == 1 && studentIdArray[0].length() == 8;
        boolean matchesStudentName = studentNameArray.length <= 10;
        if (!matchesGrp || !matchesId || !matchesStudentName) {
            throw deleteStudentCommandParserException;
        }

        String studentName = String.join(" ", studentNameArray);
        String studentId = studentIdArray[0];
        String grpName = grpKeywordArray[0];

        return new DelStudentCommand(studentName, studentId, new GroupContainsKeywordPredicate(grpName));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
