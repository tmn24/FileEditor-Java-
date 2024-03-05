import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import static java.nio.file.StandardCopyOption.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class FileEditor{
   File file;
   FileWriter fw;
   BufferedWriter bw;
   OutputStream out;
   InputStream in;
   String leftOver = "";
   FileEditor(){}
   boolean endFile = false;
   //Create new file at specified path
   public void createNewFile(String pathname){
      file = new File(pathname);
      try{
      file.createNewFile();
      } catch (IOException e){  
         e.printStackTrace();   
      }
   }
   public void saveImage(String pathname, String fileType, BufferedImage bi){
      createNewFile(pathname);
      try{
      ImageIO.write(bi,fileType,new File(pathname));
      } catch(Exception e){}
   }
   //Write and close file in binary UTF-8
   public void writeBinary(String pathname, String text){
      try{
         Charset charset = StandardCharsets.UTF_8;
         out = new BufferedOutputStream(new FileOutputStream(pathname));
         out.write(text.getBytes(charset),0,text.length());
         out.close();
         out = null;
      } catch(Exception e){
         e.printStackTrace();
      }
   }
   //Write to a binary file in UTF-8: MUST CALL closeBinary() BEFORE PROGRAM ENDS
   public void addBinary(String pathname, String text){
      try{
         Charset charset = StandardCharsets.UTF_8;
         if(out == null){
            out = new BufferedOutputStream(new FileOutputStream(pathname));
         }
         out.write(text.getBytes(charset),0,text.length());
      } catch(Exception e){
         e.printStackTrace();
      }
   }
   //Closes the writer object
   public void closeBinary(String pathname){
      try{
         if(out == null){
            out = new BufferedOutputStream(new FileOutputStream(pathname));
         }
         out.close();
         out = null;
      } catch(Exception e){
         e.printStackTrace();
      }
   }
   
   public void readBinary(String pathname){}//I don't want to do this rn but its the same concept as readFile
   public boolean endOfFile(){
      if(endFile){
         endFile = false;
         return true;
      }
      return false;
   }
   //Reads a line from a binary file in UTF-8
   public String readLineBinary(String pathname){
      try{
         Charset charset = StandardCharsets.UTF_8;
         if(in == null){
            in = new BufferedInputStream(new FileInputStream(pathname));
            leftOver = "";
         }
         if(leftOver.indexOf("\n") > -1){
            String str = leftOver.substring(0,leftOver.indexOf("\n"));
            leftOver = leftOver.substring(leftOver.indexOf("\n")+1);
            return str;
         }
         byte[] b = new byte[4096];
         if(in.read(b,0,b.length) > -1){
            //System.out.println(p);
            String returnString = "";
            //DONT USE STRINGS, ONLY USE BYTES UNTil \n APPEARS
            int index = 0;
            boolean bool = true;
            boolean endOfFile = false;
            while(bool && !endOfFile){
               for(int i = 0; i < b.length; i++){
                  if(b[i] == 10){
                     bool = false;
                     index = i;
                     break;
                  }
               }
               if(bool){
                  returnString += new String(b,0,b.length,charset); 
                  System.out.println("||"+returnString);
                  endOfFile = in.read(b,0,b.length) == -1;
                  //System.out.println(p+"|"+endOfFile);
               }
            }
            //in.mark(10);
            if(!endOfFile){
               if(returnString.equals("")){
                  returnString = leftOver+returnString+(new String(b,0,index,charset));
               } else {
                  returnString = leftOver+returnString+(new String(b,0,index,charset));
               }
               leftOver = new String(b,index+1,b.length-index-1,charset);
               return returnString;
            } else {
               in = null;
               String str = leftOver;
               leftOver = "";
               endFile = true;
               return str+returnString;
            }
         } else {
            //System.out.println(p);
            in = null;
            String str = leftOver;
            leftOver = "";
            endFile = true;
            return str; 
         }
      }catch(Exception e){
         e.printStackTrace();
      }
      endFile = true;
      return "";
   }
   //Writes to and closes a text file 
   public void writeToFile(String pathname, String text){
      try{
      fw = new FileWriter(pathname);
      fw.write(text);
      fw.close();
      fw = null;
      } catch (IOException e){
         System.out.println(1);     
      }
   }
   //Reads and returns the entirety of the data within a file
   public String readFile(String pathname){
      try{
      file = new File(pathname);
      ArrayList<String> string = new ArrayList<String>(10);  
      Scanner scanner = new Scanner(file);
      while(scanner.hasNext()){
         string.add(scanner.nextLine());
      }
      String str = "";
      for(int i = 0; i < string.size(); i++){
         str += string.get(i)+"\n";
      }
      return str;
      } catch (FileNotFoundException e){
         return "";
      }
   }
   //Copies a file and pastes (and replaces) it at the deisgnated path 
   public void copyTo(String filePathname, String targetPathname){
      file = new File(filePathname);
      File file2 = new File(targetPathname);
      try{
      Files.copy(file.toPath(),file2.toPath(),REPLACE_EXISTING);
      } catch (Exception e){
         
      }
   }
   //Checks whether or not a file exists
   public boolean exists(String pathname){
      file = new File(pathname);
      return file.exists();
   }
   //Lists the absolute paths of all files within a folder at the specified path
   public String[] listFiles(String pathname){
      file = new File(pathname);
      File[] files = file.listFiles();
      String[] fileNames = new String[files.length];
      for(int i = 0; i < files.length; i++){
         fileNames[i] = files[i].getAbsolutePath();
         fileNames[i] = fileNames[i].replace('\\', '/');
      }
      return fileNames;
   }
   //Appends text to a file at the specified path. MUST CALL closeFile() FOR WRITER OBJECT TO FINISH
   public void addWriting(String pathname, String text){
      try{
      if(fw == null){
         fw = new FileWriter(pathname);
         bw = new BufferedWriter(fw);
      }
      //System.out.println(text);
      fw.write(text);
      } catch (IOException e){
         e.printStackTrace();     
      }
   }
   //Closes File/Writer Object
   public void closeFile(String pathname){
      try{
         if(fw == null){
         fw = new FileWriter(pathname);
         }
         fw.close();
         fw = null;
      } catch (IOException e){
         System.out.println(1);
      }
   }
}