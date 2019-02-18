package lubokkanev.revisionmanager;

import lubokkanev.revisionmanager.file.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private File dir;
    private File latestDir;
    private Loader loader;
    private List<Object> objects = new ArrayList<>(); // FIXME: maybe use a set

    public Project(File dir) {
        this.dir = dir;
        latestDir = new File(dir + "/" + "current");
        loader = new Loader(dir);
    }

    public void order() {
        for (ObjectFile file : loader.getObjectFiles()) {
            addObjectAndFile(file);
        }

        for (Object object : objects) {
            order(object);
        }
    }

    private void addObjectAndFile(ObjectFile file) {
        Object object = null;
        for (Object obj : objects) {
            if (obj.getName().equals(file.getObjectName())) { // FIXME: if we are using a set, move this logic in the equals method
                object = obj;
                break;
            }
        }

        if (object == null) {
            object = new Object(dir, file.getObjectName());
            objects.add(object);
        }

        object.addFile(file);
    }

    private void order(Object object) {
        object.createAndFillObjectFolder();
        putTheLatestRevision(object);
    }

    private void putTheLatestRevision(Object object) {
        if (!latestDir.exists() && !latestDir.mkdirs()) {
            throw new RuntimeException("Failed to create directory: '" + latestDir.getAbsolutePath() + "'.");
        }

        File[] files = latestDir.listFiles();
        if (files != null) {
           for (File file : files) {
              if (file.getName().startsWith(object.getName())) {
                 if (!file.delete()) {
                    System.err.println("Failed to delete the latest revision file: '" + file.getAbsolutePath() + "'.");
                 }
              }
           }
        }

        object.getLatest().copyTo(latestDir);
    }
}
