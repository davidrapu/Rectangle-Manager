package Rectangle;

import com.sun.tools.javac.Main;

import java.awt.Color;
import java.util.*;

public class RectangleManager {

    public boolean addRectangle(RectangleShape shape, ArrayList<RectangleShape> shapeList) {
        // Collect the rectangle object which includes all the rectangle shape info
        shapeList.add(shape); // Add it to the list of Rectangles
        return true;
    }

    public RectangleShape searchRectangle(int id, ArrayList<RectangleShape> shapeList) {
        // If the id given is in the list of shapes then we can return that shape. If not return null
        for (RectangleShape shape : shapeList){
            if(shape.getId() == id) return shape;
        }
        return null;
    }

    public ArrayList<RectangleShape> getSortedRectangles(ArrayList<RectangleShape> shapeList) {
        // Create a second list to prevent the actual list being edited
        ArrayList<RectangleShape> sortedShapeList = new ArrayList<RectangleShape>(shapeList);
        Collections.sort(sortedShapeList);

        // return the list
        return sortedShapeList;
    }

    public boolean updateRectangle(ArrayList<RectangleShape> shapeList ,String shapeId , String shapeWidth, String shapeHeight, Color color){
        // Validate ID
        boolean idCheck = validateId(shapeId);

        // Validate Width
        boolean widthCheck = validateWidth(shapeWidth);

        // Validate Height
        boolean heightCheck = validateHeight(shapeHeight);

        int id = Integer.parseInt(shapeId);
        for (RectangleShape shape : shapeList){
            if(shape.getId() != id){
                return false;
            }
        }

        if (idCheck && widthCheck && heightCheck){
            int width = Integer.parseInt(shapeWidth);
            int height = Integer.parseInt(shapeHeight);
            for (RectangleShape shape : shapeList){
                if(shape.getId() == id){
                    shape.setWidth(width);
                    shape.setHeight(height);
                    shape.setColor(color);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateId(String id){
        boolean idCheck;

        try {
            String idExtract = id.substring(0, 3);

            idCheck = idExtract.equals("240") && id.length() == 6; // If the first 3 numbers of the id is 240 and
            // The length of the id == 3, then all is good

        }catch (NumberFormatException e) {

            idCheck = false; // If an error is gotten It's because the id is not a number. Could still be a string or a long
        }
        return idCheck;
    }

    public boolean validateWidth(String rectangleWidth) {
        // Validation
        boolean widthCheck = false;
        int width;

        try {
            width = Integer.parseInt(rectangleWidth);

            if(width % 2 == 0 && width != 0){ // If width is even and last digit of the id is odd then chosen width is correct
                widthCheck = true;
            }
        }catch (NumberFormatException e) { /* When width is not an integer*/}
        return widthCheck;
    }

    public boolean validateHeight(String rectangleHeight) {
        // Validation
        boolean heightCheck = false;
        int height;

        try {
            height = Integer.parseInt(rectangleHeight);
            if (height > 0)
                heightCheck = true;
        }catch (NumberFormatException e) { /* When width or height is not an integer*/}
        return heightCheck;
    }

    public ArrayList<RectangleShape> getRectangles() {
        return null;
    }

    public RectangleShape getCurrentRectangle() {
        return null;
    }
}