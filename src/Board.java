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
import java.awt.Color;

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

    Image selectedImageSlice;

    boolean haveWeScrabled=false;
    
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

    //tells us if we just scrambled the puzzle
    public boolean justScrambled(){
        return haveWeScrabled;
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
            
            haveWeScrabled=false;
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

        haveWeScrabled=false;
        swapImageSlices(image1, image2);
        return true;
    }

    //selects an image slice at these coords
    public boolean selectImage(int x, int y){
        selectedImageSlice=getImageAt(x, y);

        return selectedImageSlice!=null;
    }

    
    //swaps the given images
    private void swapImageSlices(Image image1, Image image2){


        //don't swap the same image
        if(image1==image2)
            return;
        
        int localCorrectCount1=0;
        int localCorrectCount2=0;

        
        int image1Index=chunks.indexOf(image1);
        int image2Index=chunks.indexOf(image2);

        //get local correct count before swap
        if(image1Index==correctChunks.indexOf(image1))
            localCorrectCount1++;
        if(image2Index==correctChunks.indexOf(image2))
            localCorrectCount1++;


        //swap the images
        chunks.set(image1Index, image2);
        chunks.set(image2Index, image1);



        //get local correct count before swap
        if(chunks.indexOf(image1)==correctChunks.indexOf(image1))
            localCorrectCount2++;
            
        if(chunks.indexOf(image2)==correctChunks.indexOf(image2))
            localCorrectCount2++;


        /*
            0-0 //were wrong; swaped them wrong
            1-1 //one was right, other was wrong; swaped the other one correctly but wrong the 1st one
            1-2 //impossible
            2-1 //impossible
            2-2 //impossible
            1-0 //all were wrong, swapped one in the correct place
            0-1 //one was right, swapped it wrong
            2-0 //were wrong, swaped them correctly
            0-2 //were right, swaped them wrong

        */

        
        correctCount+=(localCorrectCount2-localCorrectCount1);
        
        
        
        
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

        //do image swaps untill there are no matches to the original positions
        if(haveWeScrabled){
            
            for(int i=0; i<chunks.size(); i++){

                int countBeforeSwap=correctCount;
        
                int randomIndex=(int)(Math.random()*chunks.size());
                swapImageSlices(chunks.get(i), chunks.get(randomIndex));


                //this swap solved the puzzle a little, undo it
                if(countBeforeSwap<correctCount){
                    swapImageSlices(chunks.get(i), chunks.get(randomIndex));
                    i--;
                }
            }

            System.out.println(correctCount);

            if(correctCount!=0){
                scrableSlices();
            }
            
            haveWeScrabled=false;
            return;
        }
        

    
        
        //do a bunch of image swaps
        for(int i=0; i<chunks.size()*2; i++){
            int randomIndex=(int)(Math.random()*chunks.size());
            swapImageSlices(chunks.get(0), chunks.get(randomIndex));    
        }

        
        haveWeScrabled=true;
        
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

        
        for(int i=0; i<chunks.size(); i++){    
            g.drawImage(chunks.get(i), size*(i%matrixWidth), (i/matrixWidth)*size, size, size, null);
        }



        if(selectedImageSlice==null)
            return;
        //draw the selected node        
        int index=chunks.indexOf(selectedImageSlice);
        
        g.setColor(new Color(255, 0, 0));
        g.fillRect(size*(index%matrixWidth)-5, (index/matrixWidth)*size-5, size+10, size+10);  
        g.setColor(new Color(0, 255, 255));
        g.fillRect(size*(index%matrixWidth)-2, (index/matrixWidth)*size-2, size+4, size+4);  
        g.drawImage(chunks.get(index), size*(index%matrixWidth), (index/matrixWidth)*size, size, size, null);
    
    }
}
