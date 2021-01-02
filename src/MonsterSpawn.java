import java.util.ArrayList;

public class MonsterSpawn {

    public Game game;

    public Monster MonsterType(Integer mobID, Integer lvl) {
        switch (mobID) {
            case 1:
                //System.out.println("Adding spawn: BlueSquareMonster");
                return new Monster_BlueSquare(lvl);
            case 2:
                //System.out.println("Adding spawn: RedCircleMonster");
                return new Monster_RedCircle(lvl);
            case 3:
                //System.out.println("Adding spawn: GreenTriangle");
                return new Monster_YellowTriangle(lvl);
            case 4:
                //System.out.println("Adding spawn: GreenTriangle");
                return new Monster_GreenStar(lvl);

            case 10:
                //System.out.println("Adding spawn: GreenTriangle");
                int newBoss = 1 + (int)(Math.random() * 3);
                return new Monster_WaveBoss(lvl, newBoss);

            default:
                //System.out.println("Adding spawn: YellowDot");
                return new Monster_RedCircle(lvl);
        }
    }


    public MonsterSpawn(ArrayList<Integer> type,ArrayList<Integer> lvl, Game game){
        this.game = game;

        for (int i = 0; i < type.size(); i++){
            Monster mob = MonsterType(type.get(i), lvl.get(i));
            game.monster_list.add(mob);

            try{Thread.sleep(500);} // 0,5sec btw monster spawns
            catch(InterruptedException e){}

        }

        }
    }
