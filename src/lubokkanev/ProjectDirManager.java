package lubokkanev;

import lubokkanev.revisionmanager.Project;
import lubokkanev.revisionmanager.file.Loader;

import java.io.File;

public class ProjectDirManager {
   public static void main(String[] args) {
      manageAllProjects(getWorkingDirectory(args.length > 0 ? args[0] : null));
   }

   private static void manageAllProjects(File directory) {
      Loader loader = new Loader(directory);

      while (true) {
         for (Project project : loader.getProjects()) {
            project.order();
         }

         try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }

   private static File getWorkingDirectory(String path) {
      if (path == null || path.isEmpty()) {
         path = System.getProperty("user.dir");
         System.err.println("Directory not set, using the one the program was started in.");
      }

      System.out.println("Working in directory '" + path + "'.");
      return new File(path);
   }
}
