package team.serenity.commons.util;

import java.nio.file.Path;

public class XlsxUtil {

    /**
     * The path of the XLSX file that the tutor downloaded from LUMINUS.
     */
    private Path filePath;

    /**
     * Creates an XlsxUtil object that manages XLSX files.
     *
     * @param filePath The path of the XLSX file that the tutor downloads from LUMINUS.
     */
    public XlsxUtil(Path filePath) {
        this.filePath = filePath;
    }

}
