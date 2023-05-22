/*

    Daren Kostov, Parker Babst

    This class controls the board from image loading to slice swapping


    Sources:
    https://stackoverflow.com/questions/9132149/how-to-convert-buffered-image-to-image-and-vice-versa
    
*/


import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import java.io.File;

import java.awt.Image;
import java.awt.image.BufferedImage;



class Board extends JPanel{


    //all image slices
    List<Image> chunks;
    
    List<Image> CorrectChunks; 

    //count of correct positions
    int correctCout=0;

    
    //constructor
    public Board(){
        super();


        
        loadImage("image.jpeg");
        scrableSlices();


        
    }


    public int getCount(){
        return correctCout;
    }

    //loads an image, returs whether or not it was successful
    public boolean loadImage(String path){

        try{
            BufferedImage newImage=ImageIO.read(new File(path));
            chunks=SliceImage(newImage);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

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
    private void swapImageSlices(Image image1, Image image2){
        //swap the images

        int image1Index=chunks.indexOf(image1);
        int image2Index=chunks.indexOf(image2);

        chunks.set(image1Index, image2);
        chunks.set(image2Index, image1);
        
    }

    //return you the image at these coords, returns null if it doesn't exist
    private Image getImageAt(int x, int y){
        
        return null;
    }

    public void scrableSlices(){
        //do a bunch of image swaps
        for(int i=0; i<30; i++){
            int randomIndex=(int)(Math.random()*chunks.size());
            swapImageSlices(chunks.get(0), chunks.get(randomIndex));    
        }
    }

    //slices the image into chunks and returns it
    public ArrayList<Image> SliceImage(BufferedImage in){

        ArrayList<Image> output= new ArrayList<Image>();


        //slice up image into 25 pieces
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                output.add(in.getSubimage(in.getWidth(this)/5*x, in.getHeight(this)/5*y, in.getWidth(this)/5, in.getHeight(this)/5));
            }
        }

    
        return output;
    }

    //draw the image slices
    public void paintComponent(Graphics g){
        super.paintComponent(g);



        
        for(int i=0; i<chunks.size(); i++)
            g.drawImage(chunks.get(i), 105*(i%5), (i/5)*105, 100, 100, null);

    
    }
}
