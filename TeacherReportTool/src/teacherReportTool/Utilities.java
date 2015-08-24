/*******************************************************************************
 * Copyright (c) 2015 JP Fasano.
 *
 * This file is part of the TeacherReportTool.
 *
 * TeacherReportTool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TeacherReportTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TeacherReportTool.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package teacherReportTool;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class Utilities {

   public static String getTime() {
      String retVal = "Build Time Not Found";
      try {

         File jarFile = new File(
               (new Utilities()).getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
         // System.out.println("jar: " + new Date(jarFile.lastModified()));

         SimpleDateFormat sdf = new SimpleDateFormat();
         sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
         sdf.applyPattern("dd MMM yyyy HH:mm:ss z");

         retVal = sdf.format(new Date(jarFile.lastModified()));
      }
      catch (Exception e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }
      return retVal;
   }

   // This is a Kludge. The objective is to get the resource loaded when
   // running from
   // The Eclipse IDE or an executable jar file.
   public static InputStream getResourceInputStream(String resourceName) {
      InputStream retVal = null;
      try {

         ClassLoader classLoader = (new MenuBarHelp()).getClass().getClassLoader();

         // retVal = classLoader.getResource("/teacherReportTool/resources/"
         // + resourceName);
         retVal = classLoader.getResourceAsStream("/teacherReportTool/resources/" + resourceName);
         if (retVal == null) {
            retVal = classLoader.getResourceAsStream("teacherReportTool/resources/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResourceAsStream("resources/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResourceAsStream("/resources/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResourceAsStream("/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResourceAsStream(resourceName);
         }
      }
      catch (Exception e) {
         System.err.println(e.getMessage());
         e.printStackTrace();
      }

      return retVal;
   }

   public static URL getResourceURL(String resourceName) {
      URL retVal = null;
      try {

         ClassLoader classLoader = (new MenuBarHelp()).getClass().getClassLoader();

         retVal = classLoader.getResource("/teacherReportTool/resources/" + resourceName);
         if (retVal == null) {
            retVal = classLoader.getResource("teacherReportTool/resources/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResource("resources/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResource("/resources/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResource("/" + resourceName);
         }
         if (retVal == null) {
            retVal = classLoader.getResource(resourceName);
         }
      }
      catch (Exception e) {
         System.err.println(e.getMessage());
         e.printStackTrace();
      }

      return retVal;
   }

}
