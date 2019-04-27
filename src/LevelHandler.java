import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelHandler {
    private List<GameObject> gameObjectList = new ArrayList<>();
    private Map<String, List<String>> worldNames = new HashMap<>();

    LevelHandler(){
        generateWorldNames();
    }

    private void generateWorldNames(){
        String path = System.getProperty("user.dir") + "\\Worlds\\";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        try {
            assert listOfFiles != null;
            for (File dir : listOfFiles) {
                if (dir.isDirectory()) {
                    File subFolder = new File(path + dir.getName());
                    File[] listOfSubFiles = subFolder.listFiles();

                    List<String> levelsInWorld = new ArrayList<>();
                    assert listOfSubFiles != null;
                    for (File subFile : listOfSubFiles) {
                        if (subFile.isFile()) {
                            levelsInWorld.add(subFile.getName());
                        }
                    }
                    worldNames.put(dir.getName(), levelsInWorld);
                }
            }
        }catch(NullPointerException e){
            System.out.println("Can't get levels");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("IDK bruh");
        }
        System.out.println(worldNames);
    }

    Map<String, List<String>> getWorldNames(){
        return this.worldNames;
    }

    List<GameObject> generateLevel(String world, String level){
        String longPathName = "";
        try {
            longPathName = System.getProperty("user.dir") + "\\Worlds\\" + world + "\\" + level + ".txt";
            File levelFile = new File(longPathName);
            FileReader fileReader = new FileReader(levelFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while(!((line=bufferedReader.readLine()) == null)){
                if(!line.startsWith("#")){
                    String type = line.split(" ")[0];
                    int x = Integer.parseInt(line.split(" ")[1]);
                    int y = Integer.parseInt(line.split(" ")[2]);
                    int width = Integer.parseInt(line.split(" ")[3]);
                    int height = Integer.parseInt(line.split(" ")[4]);

                    switch (type){
                        case "platform":
                            gameObjectList.add(new Platform(x, y, width));
                            break;
                        case "ladder":
                            gameObjectList.add(new Ladder(x, y, height));
                            break;
                        case "door":
                            gameObjectList.add(new Door(x, y));
                            break;
                        case "coin":
                            gameObjectList.add(new Coin(x, y));
                            break;
                        default:
                            System.out.println("Cant find type: " + type);
                            break;
                    }
                }
            }

        }catch(FileNotFoundException e){
            System.out.println("Can't find level file: " + longPathName + " must not exist");
        } catch (IOException e) {
            System.out.println("Can't read line in file: " + longPathName);
        }

        return gameObjectList;
    } //generateLevel


}
