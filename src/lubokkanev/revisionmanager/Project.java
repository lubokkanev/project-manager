package lubokkanev.revisionmanager;

import lubokkanev.revisionmanager.file.FileLoader;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Project {
   private final File dir;
   private FileLoader fileLoader = new FileLoader();

   public Project(File dir) {
      this.dir = dir;
   }

   public void order() {
      List<ObjectFile> files = getObjectFiles();

      for (ObjectFile file : files) {
         order(file);
      }
   }

   private List<ObjectFile> getObjectFiles() {
      return fileLoader.getFiles(dir).stream()
            .map(ObjectFile::new)
            .collect(Collectors.toList());
   }

   private void order(ObjectFile file) {
      copyToObjectFolder(file);
      setTheLatestRevision(file);
   }

   private void copyToObjectFolder(ObjectFile file) {
      // TODO
   }

   private void setTheLatestRevision(ObjectFile file) {
      Object object = file.getObject();
      ObjectFile currentLatest = object.getLatest();

      if (file.moreRecent(currentLatest)) { // inverse?
         object.setLatest(file);
      }
   }
}
