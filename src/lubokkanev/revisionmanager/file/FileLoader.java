package lubokkanev.revisionmanager.file;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileLoader {
   public List<File> getFiles(File dir) {
      validateDir(dir);
      return Arrays.stream(dir.listFiles()).filter(File::isFile).collect(Collectors.toList());
   }

   public List<File> getDirs(File dir) {
      validateDir(dir);
      return Arrays.stream(dir.listFiles()).filter(File::isDirectory).collect(Collectors.toList());
   }

   private void validateDir(File dir) {
      if (dir == null || !dir.isDirectory()) {
         throw new RuntimeException("Invalid directory");
      }
   }
}
