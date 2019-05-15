import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelHandler {
    private List<GameObject> gameObjectList = new ArrayList<>();
    private Map<String, List<String>> worlds = generateWorldNames();

    LevelHandler() {
    }

    List<GameObject> getGameObjectList(){
        return this.gameObjectList;
    }

    void setGameObjectList(List<GameObject> gameObjectList){
        this.gameObjectList = gameObjectList;
    }

    private Map<String, List<String>> generateWorldNames() {
        Map<String, List<String>> worldNames = new HashMap<>();

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
        } catch (NullPointerException e) {
            System.out.println("Can't get levels");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("IDK bruh");
        }
        System.out.println(worldNames);
        return worldNames;
    }

    Map<String, List<String>> getWorldNames() {
        return this.worlds;
    }

    List<GameObject> generateLevel(String world, String level) {
        gameObjectList = new ArrayList<>();

        String longPathName = "";
        try {
            longPathName = System.getProperty("user.dir") + "\\Worlds\\" + world + "\\" + level + ".txt";
            File levelFile = new File(longPathName);
            FileReader fileReader = new FileReader(levelFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while (!((line = bufferedReader.readLine()) == null)) {
                if (!line.startsWith("#")) {
                    String type = line.split(" ")[0];
                    int x = Integer.parseInt(line.split(" ")[1]);
                    int y = Integer.parseInt(line.split(" ")[2]);
                    int width = Integer.parseInt(line.split(" ")[3]);
                    int height = Integer.parseInt(line.split(" ")[4]);

                    String imagePath;
                    try{
                        imagePath = line.split(" ")[5];
                    }catch (ArrayIndexOutOfBoundsException e){
                        imagePath = type;
                    }

                    Color objectColor = Color.BLACK;
                    switch (type) {
                        case "player":
                            DreamLand.game.getPlayer().setX(x);
                            DreamLand.game.getPlayer().setY(y);
                            break;
                        case "platform":
                            objectColor = Color.ORANGE;
                            //gameObjectList.add(new Platform(x, y, width, height, imagePath));
                            break;
                        case "floor":
                            objectColor = Color.GRAY;
                            //gameObjectList.add(new Floor(x, y, width, height, imagePath));
                            break;
                        case "ladder":
                            objectColor = Color.BLUE;
                            //gameObjectList.add(new Ladder(x, y, width, height, imagePath));
                            break;
                        case "door":
                            objectColor = Color.CYAN;
                            //gameObjectList.add(new Door(x, y, width, height, imagePath));
                            break;
                        case "coin":
                            objectColor = Color.YELLOW;
                            //gameObjectList.add(new Coin(x, y, width, height,imagePath));
                            break;
                        default:
                            System.out.println("Cant find type: " + type);
                            break;
                    }
                    gameObjectList.add(new GameObject(type, x, y, width, height, objectColor, imagePath));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Can't find level file: " + longPathName + " must not exist");
        } catch (IOException e) {
            System.out.println("Can't read line in file: " + longPathName);
        }

        return gameObjectList;
    } //generateLevel


}
