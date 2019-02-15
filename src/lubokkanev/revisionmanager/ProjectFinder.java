package lubokkanev.revisionmanager;

import lubokkanev.revisionmanager.file.FileLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectFinder {
   private FileLoader fileLoader = new FileLoader();
   private File currentDir;

   public ProjectFinder(File currentDir) {
      this.currentDir = currentDir;
   }

   public List<Project> findProjects() {
      List<File> dirs = fileLoader.getDirs(currentDir);
      List<Project> projects = new ArrayList<>();

      for (File dir : dirs) {
         projects.add(new Project(dir));
      }

      return projects;
   }
}
