package inf112.misc;

import inf112.tiles.ITile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoardParser
{
    public static ITile[][] parseBoard(String path)
    {
        File file = new File(path);
        ITile[][] board = null;
        try{
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine())
            {
                String line = reader.nextLine();
                String[] comp = line.split("\\s");
                //look for size of board
                if(comp[0].equals("<map"))
                {
                    String w = comp[5].split("[\"]")[1];
                    String h = comp[6].split("[\"]")[1];
                    board = new ITile[Integer.parseInt(w)][Integer.parseInt(h)];
                    continue; //jump to next line
                }

                for (String n:comp)
                {
                    if (n.equals("name=\"Board\""))
                    {
                        reader.nextLine();//skip encoding line
                        String s = reader.nextLine();
                        int h = 0;
                        while(!s.equals("</data>"))
                        {
                            String[] tilesLine = s.split("[,]");
                            for (int i = 0; i < tilesLine.length; i++) {
                                board[h][i] = Utils.toTile(Integer.parseInt(tilesLine[i]));
                            }
                            h++;
                            s = reader.nextLine();
                        }
                        return board;
                    }
                }
            }
            reader.close();
        }catch(FileNotFoundException e)
        {
            return null;
        }
        return null;
    }
}
