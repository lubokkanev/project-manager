package lubokkanev.revisionmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Object {
    private File dir;
    private List<ObjectFile> files = new ArrayList<>(); // FIXME: maybe use a set
    private ObjectFile latest;

    public Object(File dir, String objectName) {
        this.dir = new File(dir + "/" + objectName + "/");
    }

    public void addFile(ObjectFile file) {
        if (!files.contains(file)) {
            files.add(file);
        }

        if (file.moreRecent(getLatest())) {
            setLatest(file);
        }
    }

    public ObjectFile getLatest() {
        return latest;
    }

    private void setLatest(ObjectFile file) {
        latest = file;
    }

    public String getName() {
        return dir.getName();
    }

    public void createAndFillObjectFolder() {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("Failed to create directory: '" + dir.getAbsolutePath() + "'.");
        }

        for (ObjectFile file : files) {
            if (!new File(dir.getAbsolutePath() + "/" + file.getName()).exists()) {
                file.copyTo(dir);
            }
        }
    }
}
