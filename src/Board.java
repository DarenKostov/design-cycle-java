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
    
    List<Image> correctChunks; 

    //count of correct positions
    int correctCount=0;

    
    //constructor
    public Board(){
        super();

        
    }

    //tells you if all images match
    public boolean allMatch(){
        if(correctCount==chunks.size())
            return true;
        return false;
    }
    
    //tells you how many images match
    public int getCount(){
        return correctCount;
    }

    //loads an image, returs whether or not it was successful
    public boolean loadImage(String path, int slicesPerSide){

        try{
            BufferedImage newImage=ImageIO.read(new File(path));
            chunks=SliceImage(newImage, slicesPerSide);
            
            correctChunks= new ArrayList<Image>();
            correctCount=chunks.size();
            for(Image slice : chunks){
                correctChunks.add(slice);
            }
            
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
        
        SliceImage(null, 2);
        return true;
    }

    //swaps the given images
    private void swapImageSlices(Image image1, Image image2){


        //don't swap the same image
        if(image1==image2)
            return;
        
        int localCorrectCount=0;

        
        int image1Index=chunks.indexOf(image1);
        int image2Index=chunks.indexOf(image2);

        //get the local correct postition count
        if(image1Index!=correctChunks.indexOf(image1))
            localCorrectCount++;
        if(image2Index!=correctChunks.indexOf(image2))
            localCorrectCount++;


        //swap the images
        chunks.set(image1Index, image2);
        chunks.set(image2Index, image1);



        //check for correct positions
        if(chunks.indexOf(image1)==correctChunks.indexOf(image1))
            localCorrectCount++;
        else
            localCorrectCount--;
            
        if(chunks.indexOf(image1)==correctChunks.indexOf(image2))
            localCorrectCount++;
        else
            localCorrectCount--;

        correctCount+=localCorrectCount;
        
        
        
        
    }

    //return you the image at these coords, returns null if it doesn't exist
    private Image getImageAt(int x, int y){
        

        //how many by how many image slices
        int matrixWidth=(int)Math.sqrt(chunks.size());

        //how big should each slice be (give heigh/width they are the same)
        int size=700/matrixWidth;


        //location of the mouse in terms of the grid
        int locationX=x/size;
        int locationY=y/size;


        //chunk is out of bounds
        if(locationX>=matrixWidth || locationY>=matrixWidth)
            return null;
        
        
        int index=locationX+locationY*matrixWidth;
        return chunks.get(index);
        
        
    }

    public void scrableSlices(){
        //do a bunch of image swaps
        for(int i=0; i<chunks.size()*2; i++){
            int randomIndex=(int)(Math.random()*chunks.size());
            swapImageSlices(chunks.get(0), chunks.get(randomIndex));    
        }
    }

    //slices the image into chunks and returns it
    private ArrayList<Image> SliceImage(BufferedImage in, int slicesPerSide){

        ArrayList<Image> output= new ArrayList<Image>();

    
        //slice up image into slicePerSide**2 pieces
        for(int y=0; y<slicesPerSide; y++){
            for(int x=0; x<slicesPerSide; x++){
                output.add(in.getSubimage(in.getWidth(this)/slicesPerSide*x, in.getHeight(this)/slicesPerSide*y, in.getWidth(this)/slicesPerSide, in.getHeight(this)/slicesPerSide));
            }
        }

    
        return output;
    }

    //draw the image slices
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(chunks==null)
            return;
        
        //how many by how many image slices
        int matrixWidth=(int)Math.sqrt(chunks.size());

        //how big should each slice be (give heigh/widthm they are the same)
        int size=700/matrixWidth;

        
        for(int i=0; i<chunks.size(); i++)
            g.drawImage(chunks.get(i), size*(i%matrixWidth), (i/matrixWidth)*size, size, size, null);

    
    }
}
