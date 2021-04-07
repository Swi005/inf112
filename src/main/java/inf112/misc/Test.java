package inf112.misc;

import com.badlogic.gdx.math.Vector2;
import inf112.gameBoard.Robot;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Robot> r = new ArrayList<>();
        List<Robot> rl = new ArrayList<>();

        Robot r1 = new Robot(1, 4, new Vector2(),new Vector2(),new Facing(0,1));
        Robot r2 = r1;

        r.add(r1);
        rl.addAll(r);

        rl.get(0).setHp(3);
        System.out.println("r HP: " + r.get(0).getHp());
        System.out.println("rl HP: " + rl.get(0).getHp()); //Yay
    }
}
