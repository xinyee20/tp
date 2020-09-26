package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySerenity;

public interface SerenityStorage {

    Path getSerenityFilePath();

    Optional<ReadOnlySerenity> readSerenity() throws DataConversionException, IOException;

    Optional<ReadOnlySerenity> readSerenity(Path filePath) throws DataConversionException, IOException;

    void saveSerenity(ReadOnlySerenity serenity) throws IOException;

    void saveSerenity(ReadOnlySerenity serenity, Path filePath) throws IOException;

}
