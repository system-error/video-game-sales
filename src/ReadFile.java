import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadFile {
    private BufferedReader br;
    private String line;
    private String file;
    private HashMap<String , ArrayList<Games>> gamesOfSpecificGenre = new HashMap<>();
    private HashMap<String , Games> topSalesOnSpecificYear = new HashMap<>();
    private HashMap<String , ArrayList<Games>> numberOfGamesPerYear = new HashMap<>();
    private HashMap<String , ArrayList<Games>> numberOfGamesPerPlatform = new HashMap<>();

    public ReadFile(){
        readTheFile();
    }

    private void readTheFile(){
        file = "vgsales.csv";

        try {

            br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) !=null){
                String[] columns = line.split(",");
                if(columns[3].equals("Year")){
                    continue;
                }

                /**
                 * columns[1] -> Name
                 * columns[2] -> Platform
                 * columns[3] -> Year
                 * columns[4] -> Genre
                 * columns[10] -> Global_Sales
                 */

                gamesOfSpecificGenre(new Games(columns[1],columns[2],columns[3],columns[4],columns[10]));
                gamesWithTopSalesOnSpecificYear(new Games(columns[1],columns[2],columns[3],columns[4],columns[10]));
                gamesPerYearPerPlatform(new Games(columns[1],columns[2],columns[3],columns[4],columns[10]));

            }
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException e){
            System.out.println("Cannot read the file");
        }
    }

    private void gamesOfSpecificGenre(Games g){

        if(gamesOfSpecificGenre.containsKey(g.getGenre())){
            gamesOfSpecificGenre.get(g.getGenre()).add(g);
        }else{
            ArrayList<Games> games = new ArrayList<>();
            games.add(g);
            gamesOfSpecificGenre.put(g.getGenre(),games);
        }
    }

    private void gamesWithTopSalesOnSpecificYear(Games g){

        double globalSalesNext = Double.parseDouble(g.getGlobal_Sales().trim());
        if(topSalesOnSpecificYear.containsKey(g.getYear())){
            for(var games: topSalesOnSpecificYear.values()){
                double globalSalesPrevious = Double.parseDouble(games.getGlobal_Sales().trim());
                if((globalSalesNext > globalSalesPrevious) && g.getYear().equals(games.getYear()) ){
                    topSalesOnSpecificYear.put(g.getYear(),g);
                }
            }
        }else{
            topSalesOnSpecificYear.put(g.getYear(),g);
        }
    }

    private void gamesPerYearPerPlatform(Games g){

        if(numberOfGamesPerPlatform.containsKey(g.getPlatform().trim())){
            numberOfGamesPerPlatform.get(g.getPlatform().trim()).add(g);
        }else {
            ArrayList<Games> games = new ArrayList<>();
            games.add(g);
            numberOfGamesPerPlatform.put(g.getPlatform().trim(),games);
        }

        if( numberOfGamesPerYear.containsKey(g.getYear().trim())){
            numberOfGamesPerYear.get(g.getYear().trim()).add(g);
        }else {
            ArrayList<Games> games = new ArrayList<>();
            games.add(g);
            numberOfGamesPerYear.put(g.getYear().trim(),games);
        }
    }

    public void results(){
        System.out.println("The games of a specific genre: ");
        System.out.println("---------------------------------------------------------------------------------------");
        for (String key: gamesOfSpecificGenre.keySet()){
            System.out.print("Genre: " + key);
            output(key, gamesOfSpecificGenre, true);
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("The game with top sales on a specific year:");
        System.out.println("---------------------------------------------------------------------------------------");
        for(Games game: topSalesOnSpecificYear.values()){
            System.out.println(game.getYear() + " : "+ game.getName() + " | " + game.getGlobal_Sales());
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("The number of games per platform:");
        System.out.println("---------------------------------------------------------------------------------------");
        for (String key: numberOfGamesPerPlatform.keySet()){
            System.out.print("Platform: " + key);
            output(key, numberOfGamesPerPlatform,false);
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("The number of games per year:");
        System.out.println("---------------------------------------------------------------------------------------");
        for (String key: numberOfGamesPerYear.keySet()){
            System.out.print("Year: " + key);
            output(key, numberOfGamesPerYear,false);
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------------");

    }

    private void output(String key, HashMap<String, ArrayList<Games>> numberOfGamesPerYearPerPlatform ,boolean flag) {
        ArrayList<Games> games = numberOfGamesPerYearPerPlatform.get(key);
        int size = games.size();
        if(flag){
            System.out.print(size > 1 ? " |Name of games: " : " |Name of game: ");
        }else{
            System.out.print(size > 1 ? " |Number of games: " + games.size() + " |Name of games: " : " |Number of game: " + games.size() + " |Name of game: ");
        }
            int counter = 0;
            for (Games game: games){
                if(size > 1){
                    if( counter == (size-1)){
                        System.out.println(game.getName());
                    }else{
                        System.out.print(game.getName() + ", ");
                        counter++;
                    }
                }else {
                    System.out.println(game.getName());
                }
            }




    }


}
