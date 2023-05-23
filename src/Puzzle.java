

/*

Daren Kostov, Parker Babst

This class controls the game and houses the Board and all buttons

*/


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;



class Puzzle implements ActionListener, MouseListener{


Board board =new Board();
JFrame frame= new JFrame("Puzzle!");


//controls
JTextField imagePath= new JTextField("Image Path");
JButton loadImage= new JButton("Load Image");
JTextField slicesPerSideField= new JTextField("Slices per Side");
JButton shuffleImageSlices= new JButton("Scramble");
JLabel correctPositions= new JLabel("Correct Positions: ???");
Container south= new Container();


//previous position of the mouse
int prevX;
int prevY;

public Puzzle(){


    
    frame.setSize(700, 727);
    frame.setResizable(false);
    
    frame.setLayout(new BorderLayout());
    frame.add(board, BorderLayout.CENTER);


    //buttons and text field setup
    south.setLayout(new GridLayout(1, 3));
    south.add(imagePath);
    // imagePath.addActionListener(this);
    south.add(loadImage);
    loadImage.addActionListener(this);
    south.add(slicesPerSideField);
    south.add(shuffleImageSlices);
    shuffleImageSlices.addActionListener(this);
    south.add(correctPositions);
    frame.add(south, BorderLayout.SOUTH);
    
    frame.addMouseListener(this);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    

}

public static void main(String[] args){
    new Puzzle();
}


@Override
public void actionPerformed(ActionEvent event){
    if(event.getSource().equals(loadImage)){


        //get slices pre side
        int slicesPerSide=3;
        try{
            slicesPerSide=Integer.parseInt(slicesPerSideField.getText());
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(frame, "Put in slices per side next time, must be in integer. Defaulting to 3.");
        }


        //get image
        if(board.loadImage(imagePath.getText(), slicesPerSide)){
            //update scramble button
            shuffleImageSlices.setText(board.justScrambled()? "True Shuffle" : "Shuffle");

            correctPositions.setText("Correct Positions: "+board.getCount());
            frame.repaint();
            JOptionPane.showMessageDialog(frame, "Loaded image successfully");
        }else
            JOptionPane.showMessageDialog(frame, "Loading image failed. Wrong file path?");

        
    }else if(event.getSource().equals(shuffleImageSlices)){
        board.scrableSlices();
        correctPositions.setText("Correct Positions: "+board.getCount());
        frame.repaint();

        //update scramble button
        shuffleImageSlices.setText(board.justScrambled()? "True Shuffle" : "Shuffle");
    }


        


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
public void mousePressed(MouseEvent e){
  prevX=e.getX();
  prevY=e.getY();
  board.selectImage(prevX, prevY);
  frame.repaint();
}
@Override
public void mouseReleased(MouseEvent e){
  //similar to the edge creation, click on 2 images and flip them (instead of drawing a line) 

  //unselect the image
  board.selectImage(999999, 999999);
  
		//switch the image
  if(board.swapCoords(prevX, prevY, e.getX(), e.getY())){
  //success
  
    frame.repaint();
    correctPositions.setText("Correct Positions: "+board.getCount());

    //update scramble button
    shuffleImageSlices.setText(board.justScrambled()? "True Shuffle" : "Shuffle");
    
    if(board.allMatch()){
      JOptionPane.showMessageDialog(frame, "You win! All image slices match!");
    }

  
  }else{
  //failure
    JOptionPane.showMessageDialog(frame, "You either clicked and dragged on the same image or\nSelected a non-existant image slice.");
  }

}



}
