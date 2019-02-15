package lubokkanev;

import lubokkanev.revisionmanager.Project;
import lubokkanev.revisionmanager.ProjectFinder;

import java.io.File;

public class ProjectDirManager {
   public static void main(String[] args) {
      manageAllProjects(getWorkingDirectory(args[0]));
   }

   private static void manageAllProjects(File directory) {
      ProjectFinder projectFinder = new ProjectFinder(directory);

      while (true) {
         for (Project project : projectFinder.findProjects()) {
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
         System.out.println("Directory not set, using the one the program was started in.");
         path = System.getProperty("user.dir");
      }

      System.out.println("Working in directory '" + path + "'.");
      return new File(path);
   }
}
