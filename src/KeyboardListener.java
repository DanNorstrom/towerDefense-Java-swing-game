import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private Game game; // game passed through to allow for game manipulation

    int lastletter = 0;
    int lastnumber = 0;

    public KeyboardListener(Game game) {
        this.game = game;
        System.out.println("Added keylistener");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {

            // grid movement ( arrows)
            case KeyEvent.VK_UP:
                if (game.selected_grid_y > 34) {
                    game.selected_grid_y -= 33;
                    System.out.println("Move Up");
                }
                break;
            case KeyEvent.VK_DOWN:
                if (game.selected_grid_y < 364) {
                    game.selected_grid_y += 33;
                    System.out.println("Move Down");
                }
                break;
            case KeyEvent.VK_LEFT:
                if (game.selected_grid_x > 34) {
                    game.selected_grid_x -= 33;
                    System.out.println("Move Left");
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (game.selected_grid_x < 430) {
                    game.selected_grid_x += 33;
                    System.out.println("Move Right");
                }
                break;

                //shop select row (q,w,e,a,s,d)
            case KeyEvent.VK_Q:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[0][0].doClick();
                game.inventory_grid_array[0][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastletter = 0;
                this.lastnumber = 0;
                break;

            case KeyEvent.VK_W:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[1][0].doClick();
                game.inventory_grid_array[1][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastletter = 1;
                this.lastnumber = 0;
                break;

            case KeyEvent.VK_E:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[2][0].doClick();
                game.inventory_grid_array[2][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastletter = 2;
                this.lastnumber = 0;
                break;

            case KeyEvent.VK_A:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[3][0].doClick();
                game.inventory_grid_array[3][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastletter = 3;
                this.lastnumber = 0;
                break;

            case KeyEvent.VK_S:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[4][0].doClick();
                game.inventory_grid_array[4][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastletter = 4;
                this.lastnumber = 0;
                break;

            case KeyEvent.VK_D:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[5][0].doClick();
                game.inventory_grid_array[5][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastletter = 5;
                this.lastnumber = 0;
                break;


                // shop select column
            case KeyEvent.VK_1:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[lastletter][0].doClick();
                game.inventory_grid_array[lastletter][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastnumber = 0;
                break;

            case KeyEvent.VK_2:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[lastletter][1].doClick();
                game.inventory_grid_array[lastletter][1].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastnumber = 1;
                break;

            case KeyEvent.VK_3:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[lastletter][2].doClick();
                game.inventory_grid_array[lastletter][2].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastnumber = 2;
                break;

            case KeyEvent.VK_4:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[lastletter][3].doClick();
                game.inventory_grid_array[lastletter][3].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastnumber = 3;
                break;

            case KeyEvent.VK_5:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[lastletter][4].doClick();
                game.inventory_grid_array[lastletter][4].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastnumber = 4;
                break;

            case KeyEvent.VK_6:
                System.out.println("Q: select this tower");
                game.inventory_grid_array[lastletter][lastnumber].setBorder(new JButton().getBorder()); // reset last border
                game.inventory_grid_array[lastletter][5].doClick();
                game.inventory_grid_array[lastletter][5].setBorder(BorderFactory.createLineBorder(Color.GREEN));
                this.lastnumber = 5;
                break;

            default:
                System.out.println("Other");
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
}
