

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseClickListener implements MouseListener, MouseMotionListener {

    private Game game; // game passed through to allow for game manipulation


    public MouseClickListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // click and let go in same object
        // show info, get coordinates for keyboard
        int x = e.getComponent().getX();
        int y = e.getComponent().getY();
        System.out.println("selecting: " + x + " " + y);

        game.selected_grid_x = x;
        game.selected_grid_y = y;

    }


    // unused Overrides, might expand later.
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent f) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e){
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse to " + e.getPoint());
    }

}
