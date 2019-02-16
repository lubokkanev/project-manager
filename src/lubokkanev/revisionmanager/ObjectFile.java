package lubokkanev.revisionmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ObjectFile {
    private final String DELIMITER = " - ";

    private File file;

    private String object;
    private Revision revision;
    private String additionalInfo = ""; // FIXME: Use for the object dir name
    private String extension;

    public ObjectFile(File file) {
        this.file = file;

        // FIXME: refactor in a new class probably
        String[] split = file.getName().split(DELIMITER);
        object = split[0];

        if (split.length < 2) {
            throw new RuntimeException("Invalid file name: '" + file.getName() + "'. No object name or revision.");
        } else if (split.length == 2) {
            int extensionDelimiter = split[1].lastIndexOf(".");
            if (extensionDelimiter == split[1].length() - 1) {
                throw new RuntimeException("Invalid file name: '" + file.getName() + "'. No file extension.");
            }

            revision = new Revision(split[1].substring(0, extensionDelimiter));
            extension = split[1].substring(extensionDelimiter + 1);
        } else {
            revision = new Revision(split[1]);

            String lastPart = file.getName().substring((object + DELIMITER + revision + DELIMITER).length());

            int extensionDelimiter = lastPart.lastIndexOf(".");
            additionalInfo = lastPart.substring(0, extensionDelimiter);
            extension = lastPart.substring(extensionDelimiter + 1);
        }
    }

    public String getObjectName() {
        return object;
    }

    public boolean moreRecent(ObjectFile file) {
        return file == null || revision.getNumber() > file.revision.getNumber();
    }

    public void copyTo(File dir) {
        if (!dir.isDirectory()) {
            throw new RuntimeException("Invalid directory: '" + dir.getAbsolutePath() + "'.");
        }

        try {
            File newFile = new File(dir.getAbsolutePath() + "/" + this.file.getName());
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to copy file '" + file.getAbsolutePath() + "'.");
            e.printStackTrace();
        }
    }

    public String getName() {
        return file.getName();
    }
}
