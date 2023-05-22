/*

    Daren Kostov, Parker Babst

    This class controls the game and houses the Board and all buttons
    
*/


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;



class Puzzle implements ActionListener, MouseListener{

    Board board=new Board();
    JFrame frame= new JFrame("Puzzle!");
    

    
    public void Pizzle(){
        frame.setSize(600, 400);
        frame.setResizable(false);

    
    }

    public static void main(String[] args){
        new Puzzle();
    }


    @Override
    public void actionPerformed(ActionEvent event) {
    }
    @Override
    public void mouseClicked(MouseEvent event){
    }
    @Override
    public void mouseEntered(MouseEvent event){
    }

    @Override
    public void mouseExited(MouseEvent event){
    }
    @Override
    public void mousePressed(MouseEvent event){
    }
    public void mouseReleased(MouseEvent event){
    }



}
