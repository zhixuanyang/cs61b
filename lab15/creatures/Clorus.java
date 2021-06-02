package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;
public class Clorus extends Creature {
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public String name() {
        return "clorus";
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r, g, b);
    }

    public void move() {
        energy -= 0.03;
    }
    public void stay() {
        energy -= 0.01;
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plip = getNeighborsOfType(neighbors, "plip");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plip.size() >= 1) {
            Direction moveDir = HugLifeUtils.randomEntry(plip);
            return new Action(Action.ActionType.ATTACK, moveDir);
        } else if (energy() >= 1.0) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        } else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE, moveDir);
        }
    }
}
