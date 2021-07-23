package file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Files {

    public enum fileAction {
        Update,
        Delete
    }


    public Files(String path) {
        String filename = System.getProperty("user.dir") + "/src/"+path;
        this.file = new File(filename);

        read();
    }

    private File file;

    private List<Object> objects = new ArrayList<>();

    public List<Object> getObjects() {
        return objects;
    }

    public File getFile() {
        return file;
    }

    public void read() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()))) {
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null) {
                splitLine(currentLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error the file doesn't exist: "+e.getMessage());
        } catch(IOException e) {
            System.out.println("Error handling the file: "+e.getMessage());
        }
    }



    public Object splitLine(String currentline) {
        return currentline;
    }


    public boolean update(fileAction action, String find, String... replace) {
        File tmpFile = new File(getFile().getAbsolutePath() + ".tmp");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tmpFile))) {
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null) {
                //if the line needs to be deleted
                if(action == fileAction.Delete) {
                    //if the currentline is not equals the line up for delete, move it to the tmp file.
                    if (!find.equals(currentLine.trim())) {
                        bufferedWriter.write(currentLine);
                        bufferedWriter.newLine();
                    }
                    //if the line needs to be updated
                } if (action == fileAction.Update) {
                    //if the currentline is not equals the line up for update, move it over.
                    if (!find.equals(currentLine.trim())) {
                        bufferedWriter.write(currentLine);
                        bufferedWriter.newLine();
                    } else {
                        //if this is the line that needs to be replaced add the replacement instead of the line.
                        bufferedWriter.write(replace[0]);
                        bufferedWriter.newLine();
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Error the file doesn't exist: "+e.getMessage());
        } catch(IOException e) {
            System.out.println("Error handling the file: "+e.getMessage());
        } finally {
            //Delete the original file
            if (!file.delete()) {
                System.out.println("Could not edit file");
                return false;
            }

            //Rename the new file to the filename the original file had.
            if (!tmpFile.renameTo(file)) {
                System.out.println("Could not rename file");
                return false;
            }

            return true;

        }

    }


}
