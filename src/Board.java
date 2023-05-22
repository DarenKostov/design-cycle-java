/*

    Daren Kostov, Parker Babst

    This class controls the board from image loading to slice swapping
    
*/

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;



class Board extends JPanel{


    //all image slices
    List<Image> chunks;
    
    List<Image> CorrectChunks; 

    //count of correct positions
    int correctCout=0;

    
    //constructor
    public Board(){
        super();
    }


    public int getCount(){
        return correctCout;
    }

    //loads an image, returs whether or not it was successful
    public boolean loadImage(String path){

        if(false){
            return false;
        }


        SliceImage(null);
        return true;
    }

    //swaps the images at these coords, return true if the swap increased the correct count
    public boolean swapCoords(int x1, int y1, int x2, int y2){

        Image image1=getImageAt(x1, y1);
        Image image2=getImageAt(x2, y2);

        if(image1==null || image2==null || image1==image2){
            return false;
        }
        
        SliceImage(null);
        return true;
    }

    //swaps the given images
    private void swapImages(Image image1, Image image2){
        //swap the images
    }

    //return you the image at these coords, returns null if it doesn't exist
    private Image getImageAt(int x, int y){
        
        return null;
    }

    public void scrableSlices(){
        //do a bunch of image swaps
    }

    //slices the image into chunks and returns it
    public ArrayList<Image> SliceImage(Image in){
        return null;
    }

    //draw the image slices
    public void paintComponent(Graphics g){
        super.paintComponent(g);




        
        // for(int i=0; i<chunks.size(); i++)
        //     g.drawImage(chunks.get(i), 10*i, 0, 10, 10, null);

    
    }
}
