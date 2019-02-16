package lubokkanev.revisionmanager.file;

import lubokkanev.revisionmanager.ObjectFile;
import lubokkanev.revisionmanager.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Loader {
   private final File directory;

   public Loader(File directory) {
      validateDir(directory);
      this.directory = directory;
   }
   public List<ObjectFile> getObjectFiles() {
      validateDir(directory);

      return Arrays.stream(directory.listFiles())
              .filter(File::isFile)
              .map(ObjectFile::new)
              .collect(Collectors.toList());
   }

   public List<Project> getProjects() {
      validateDir(directory);

      return Arrays.stream(directory.listFiles())
              .filter(File::isDirectory)
              .map(Project::new)
              .collect(Collectors.toList());
   }

   private void validateDir(File dir) {
      if (dir == null || !dir.isDirectory()) {
         throw new RuntimeException("Invalid directory: '" + (dir != null ? dir.getAbsolutePath() : "null") + "'.");
      }
   }
}
