package lubokkanev.revisionmanager.file;

import lubokkanev.revisionmanager.ObjectFile;
import lubokkanev.revisionmanager.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Loader {
   private final File directory;

   public Loader(File directory) {
      validateDir(directory);
      this.directory = directory;
   }
   public List<ObjectFile> getObjectFiles() {
      return getFiles(File::isFile, ObjectFile::new);
   }

   public List<Project> getProjects() {
      return getFiles(File::isDirectory, Project::new);
   }

   private void validateDir(File dir) {
      if (dir == null || !dir.isDirectory()) {
         throw new RuntimeException("Invalid directory: '" + (dir != null ? dir.getAbsolutePath() : "null") + "'.");
      }
   }

   private <T> List<T> getFiles(Predicate<File> filter, Function<File, T> constructor) {
      validateDir(directory);

      File[] files = directory.listFiles();
      if (files == null) {
         return new ArrayList<>();
      }

      return Arrays.stream(files)
            .filter(filter)
            .map(constructor)
            .collect(Collectors.toList());
   }
}
