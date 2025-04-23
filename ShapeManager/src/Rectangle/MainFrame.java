package Rectangle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import java.util.*;

public class MainFrame extends JFrame implements DocumentListener {
    private ArrayList<RectangleShape> rectangleList = new ArrayList<RectangleShape>();
    private RectangleManager rm = new RectangleManager();
    private JTextField idField, widthField, heightField, xPositionField, yPositionField;
    private JTextArea consoleOutput;
    private JLabel widthLabel;
    private JLabel heightlabel;
    private JComboBox rectangleColor;
    private final Map<String, Color> colorStringMap = new HashMap<>();
    private JPanel centerPanel, displayPanel;

    public MainFrame(){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
        this.setTitle("Shape Manager - ID: 2409829");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ImageIcon imgIcon = new ImageIcon("image.png");
        this.setIconImage(imgIcon.getImage());
        this.setResizable(false);
        this.setLayout(new BorderLayout());



        // Set up panels for inputs, display, and buttons
        setupTopPanel();
        setupCenterPanel();
        setupBottomPanel();

        this.setVisible(true);
        String introMessage = """
                THESE ARE THE RULES OF THIS SHAPE MANAGER (600 x 800):
                
                
                1. ID must be a 6 digit numerical value that starts with 240
                
                2. Width must be an Even numerical value
                
                3. Height must be a numerical value
                
                4. x Value must be a numerical value
                
                5. y Value ust be a numerical value
                
                6. x value + width must not be above 600
                
                7. y value + height must not be above 800""";

        JOptionPane.showMessageDialog(this,introMessage,"Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupTopPanel() {
        // Create input fields for ID, width, height, xPos, yPos, and color
        // Add input fields to the top panel

        // Objects for Top Panel
        JPanel topPanel = new JPanel();
        JLabel idLabel = new JLabel("ID (Starts with 240):");
        idField = new JTextField();
        widthLabel = new JLabel("Width (Even Only):");
        widthField = new JTextField();
        heightlabel = new JLabel("Height:");
        heightField = new JTextField();
        JLabel xPositionLabel = new JLabel("X Position:");
        xPositionField = new JTextField();
        JLabel yPositionlabel = new JLabel("Y Position:");
        yPositionField = new JTextField();
        JLabel colorLabel = new JLabel("Color:");


        // Colors
        colorStringMap.put("Black", Color.BLACK);
        colorStringMap.put("Blue", Color.BLUE);
        colorStringMap.put("Yellow", Color.YELLOW);
        colorStringMap.put("Cyan", Color.CYAN);
        colorStringMap.put("Green", Color.GREEN);
        colorStringMap.put("Magenta", Color.MAGENTA);
        colorStringMap.put("Red", Color.RED);
        colorStringMap.put("Pink", Color.PINK);

        rectangleColor = new JComboBox<>();
        for (String colorName : colorStringMap.keySet()){
            rectangleColor.addItem(colorName);
        }
        rectangleColor.setSelectedIndex(6);

        // Text fields
        idField.setText("240"); // Default text is always 240
        idField.setCaretPosition(idField.getText().length()); // The caret starts exactly after 210 in the id field
        idField.getDocument().addDocumentListener(this); // This document would be watched/listened to


        // Customizing Panel
        topPanel.setLayout(new GridLayout(3,4,10,10));
        topPanel.setPreferredSize(new Dimension(100,100)); // The top panel size set to 100x100
        topPanel.setBackground(new Color(238,238,238)); // Color of the top panel set to be light gray


        // Adding to the panel
        topPanel.add(idLabel);
        topPanel.add(idField);
        topPanel.add(widthLabel);
        topPanel.add(widthField);
        topPanel.add(heightlabel);
        topPanel.add(heightField);
        topPanel.add(xPositionLabel);
        topPanel.add(xPositionField);
        topPanel.add(yPositionlabel);
        topPanel.add(yPositionField);
        topPanel.add(colorLabel);
        topPanel.add(rectangleColor);

        // Adding panel to frame
        this.add(topPanel, BorderLayout.NORTH); // Adding it to the
    }

    private void setupCenterPanel() {
        // Create and add a panel for displaying the rectangle

        // Objects for Central Panel
        centerPanel = new JPanel();
        consoleOutput = new JTextArea(20,1);
        consoleOutput.setLineWrap(true);
        consoleOutput.setWrapStyleWord(true);
        consoleOutput.setEditable(false);
        centerPanel.setBackground(new Color(192,192,192));
        centerPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setPreferredSize(new Dimension(250,100));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        // Output console inside main Panel
        consoleOutput.setBackground(Color.white);
        centerPanel.add(scrollPane, BorderLayout.EAST);


        this.add(centerPanel, BorderLayout.CENTER);
    }

    private void setupBottomPanel() {
        // Add buttons for Add, Search, Display, Sort, Update

        // Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(238,238,238));

        //Add Button
        JButton addButton = new JButton("Add Rectangle");
        addButton.addActionListener(e -> addRectangle());
        addButton.setFocusable(false);
        bottomPanel.add(addButton);

        // Search Button
        JButton searchButton = new JButton("Search Rectangle");
        searchButton.addActionListener(e -> searchRectangle());
        searchButton.setFocusable(false);
        bottomPanel.add(searchButton);

        // Display all in a sorted format
        JButton displayAllSorted = new JButton("Display All (Sorted)");
        displayAllSorted.addActionListener(e -> sortRectangles());
        displayAllSorted.setFocusable(false);
        bottomPanel.add(displayAllSorted);

        // Update Rectangle
        JButton updateRectangles = new JButton("Update Rectangles");

        updateRectangles.addActionListener(e -> {
            // Clear display would be the first thing
            try {
                centerPanel.remove(displayPanel);
                centerPanel.revalidate();
                centerPanel.repaint();
            }catch (Exception _){}
            displayAllId();
            setDefaultFieldValues();
            // Ask the user for an ID
            String rectangleId = JOptionPane.showInputDialog(
                    this,
                    "What is the rectangle ID?",
                    null,JOptionPane.QUESTION_MESSAGE);
            try {
                if (rm.validateId(rectangleId)) {


                    final RectangleShape[] shapeHolder = new RectangleShape[1]; // Mutable container for `shape`
                    int id = Integer.parseInt(rectangleId);
                    for (RectangleShape r : rectangleList) if (r.getId() == id) {
                        shapeHolder[0] = r; // Make sure the ID exists
                        break;
                    }

                    if (shapeHolder[0] != null) {
                        RectangleShape shape = shapeHolder[0];
                        addToConsole(shape.toString());

                        // New updateFrame for taking user values
                        JFrame updateFrame = new JFrame();
                        JLabel widthLabel2 = new JLabel("Width (Even Only):");
                        JTextField widthField2 = new JTextField();
                        JLabel heightLabel2 = new JLabel("Height:");
                        JTextField heightField2 = new JTextField();
                        JLabel colorName = new JLabel("Color:");
                        JButton confirm = new JButton("Confirm");
                        JButton cancel = new JButton("Cancel");


                        // Fields
                        widthField2.setPreferredSize(new Dimension(50,40));
                        heightField2.setPreferredSize(new Dimension(50,40));

                        // Buttons
                        confirm.addActionListener(e1 -> {
                            if (rm.validateHeight(heightField2.getText()) &&
                                    rm.validateWidth(widthField2.getText()) /*Height and width are validated*/) {

                                boolean updateRectangleInfo = rm.updateRectangle(
                                        rectangleList, rectangleId,
                                        widthField2.getText(), heightField2.getText(),
                                        colorStringMap.get(rectangleColor.getSelectedItem())
                                );

                                if (updateRectangleInfo) {
                                    addToConsole("Shape Updated Successfully\n\n",shape.toString());
                                    drawShape(shape);
                                    updateFrame.dispose();
                                }
                            }else{
                                JOptionPane.showMessageDialog(updateFrame,"Height or Width doesn't meet the criteria: Integer Value","Error",JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        cancel.addActionListener(e1 -> {
                            updateFrame.dispose();
                            addToConsole("Shape Update Canceled!");
                        });


                        updateFrame.setSize(400,120);
                        updateFrame.setResizable(false);
                        updateFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
                        updateFrame.setLocationRelativeTo(null);
                        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


                        updateFrame.add(widthLabel2);
                        updateFrame.add(widthField2);
                        updateFrame.add(heightLabel2);
                        updateFrame.add(heightField2);
                        updateFrame.add(colorName);
                        updateFrame.add(rectangleColor);
                        updateFrame.add(confirm);
                        updateFrame.add(cancel);

                        updateFrame.setVisible(true);


                    }
                    else addToConsole("Invalid ID");
                }
            } catch (Exception ex) {addToConsole("Update cancelled due to Invalid ID");}
        });
        updateRectangles.setFocusable(false);
        bottomPanel.add(updateRectangles);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addToConsole(String... text){
        consoleOutput.setText("");
        for (String values : text){
            consoleOutput.append(values);
        }
    }


    private void addRectangle() {
        // Read values, validate, create RectangleShape, add to list

        // Clear display would be the first thing
        try {
            centerPanel.remove(displayPanel);
            centerPanel.revalidate();
            centerPanel.repaint();
        }catch (Exception _){}

        // Variables to be used
        String rectangleId, rectangleWidth, rectangleHeight;
        Color color;
        String colorValue;
        int id=0, width=0, height=0, xPosition=0, yPosition=0;
        boolean idCheck, widthCheck = false, heightCheck = false, xPositionCheck = false, yPositionCheck = false, uniqueCheck = false;


        // Collecting the final chosen ID
        rectangleId = idField.getText();
        // Validating the ID
        idCheck = rm.validateId(rectangleId);
        if(idCheck) id = Integer.parseInt(rectangleId);

        // Collecting the final chosen width and height of the rectangle
        rectangleWidth = widthField.getText();
        rectangleHeight = heightField.getText();

        // Validation
        widthCheck = rm.validateWidth(rectangleWidth);
        if(widthCheck) width = Integer.parseInt(rectangleWidth);

        heightCheck = rm.validateHeight(rectangleHeight);
        if(heightCheck) height = Integer.parseInt(rectangleHeight);


        // Collecting the final chosen X value of the rectangle
        // Come back later to make sure coordinates are never the same in the list of rectangles
        try{
            xPosition = Integer.parseInt(xPositionField.getText());
            if (xPosition >= 0 && xPosition <= 600) xPositionCheck = true;
            if(xPosition + width > 600) {xPositionCheck = false; widthCheck = false;}
        }catch (NumberFormatException e) {/* x value is not an integer and must be an integer so xPositionCheck is not changed at this point*/
        }


        // Collecting the final chosen Y value of the rectangle
        // Come back later to make sure coordinates are never the same in the list of rectangles
        try{
            yPosition = Integer.parseInt(yPositionField.getText());
            if (yPosition >= 0 && yPosition <= 800) yPositionCheck = true;
            if (yPosition + height > 800) {yPositionCheck = false; heightCheck = false;}
        }catch (NumberFormatException e) {/* y value is not an integer and must be an integer*/}


        // Collecting the final chosen color of the rectangle
        // To get the chosen color get its index value in the color names array
        colorValue = rectangleColor.getSelectedItem().toString(); // Make sure that the value collected is not null
        color = colorStringMap.get(colorValue);

        // Make sure rectangle is unique of others in the list

        if(rectangleList.isEmpty()){
            uniqueCheck = true;
        }else{
            for (RectangleShape rs : rectangleList) {
                if(rs.getId() != id){
                    uniqueCheck = true;
                }else{
                    uniqueCheck = false;
                    break;
                }
            }
        }

//        System.out.println("unique: " + uniqueCheck);
//        System.out.println("id: " + id + " is " + idCheck);
//        System.out.println("width: " + width + " is " + widthCheck);
//        System.out.println("height: " + height + " is " + heightCheck);
//        System.out.println("xPosition: " + xPosition + " is " + xPositionCheck);
//        System.out.println("yPosition: " + yPosition + " is " + yPositionCheck);
//        System.out.println("color: " + color);


        // Call add rectangle function once all the checks are true
        if(idCheck && widthCheck && heightCheck && xPositionCheck && yPositionCheck && uniqueCheck){

            RectangleShape rectangleShape = new RectangleShape(id, width, height,xPosition,yPosition,color);

            boolean rectangleAdded = rm.addRectangle(rectangleShape,rectangleList); // This would return true if it was added to the list successfully

            // Shape added successfully
            addToConsole("Shape Added Successfully");
            setDefaultFieldValues();
        }else{
            consoleOutput.setText("");
            consoleOutput.append("Insertion failed\n\n");
            if (!idCheck) consoleOutput.append("ID does not meet requirements: 6 numerical characters\n\n");
            if (!widthCheck) consoleOutput.append("Width does not meet criteria: A numerical even or any value\n\n");
            if (!heightCheck) consoleOutput.append("Height does not meet criteria: A numerical even or any value\n\n");
            if (!xPositionCheck) consoleOutput.append("X Position does not meet criteria: A numerical value\n\n");
            if (!yPositionCheck) consoleOutput.append("Y Position does not meet criteria: A numerical value\n\n");
            if (!uniqueCheck) consoleOutput.append("ID is not unique");
        }

    }

    private void displayAllId(){
        StringBuilder allRectangleIds = new StringBuilder();
        for (RectangleShape rs : rectangleList) {
            allRectangleIds.append("ID: ").append(rs.getId()).append("\n\n");
        }
        addToConsole(allRectangleIds.toString());
    }
    private void setDefaultFieldValues(){
        idField.setText("240");
        widthField.setText("");
        heightField.setText("");
        xPositionField.setText("");
        yPositionField.setText("");
        rectangleColor.setSelectedIndex(6);
    }

    private void searchRectangle() {
        try {
            centerPanel.remove(displayPanel);
            centerPanel.revalidate();
            centerPanel.repaint();
        }catch (Exception _){}
        // Clear the Fields
        setDefaultFieldValues();
        // Show the Ids available
        displayAllId();

        String choice = JOptionPane.showInputDialog(this,"What is the Rectangle ID",null,JOptionPane.INFORMATION_MESSAGE);
        if (choice == null) {consoleOutput.setText("");return;}
        // Find rectangle by ID, display details, draw it
        if(displayPanel != null) {centerPanel.remove(displayPanel);}
        int id = 0;
        try {
            id = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            addToConsole("ID does not meet criteria: A 6 digit numerical value");
            return;
        }
        if(choice.length() != 6){ // If the length of the
            addToConsole("ID does not meet criteria: A 6 digit numerical value");
            return;
        }

        RectangleShape shape = rm.searchRectangle(id, rectangleList); // searching for the shape by ID

        if (shape == null) {
            try {
                centerPanel.remove(displayPanel);
                centerPanel.revalidate();
                centerPanel.repaint();
            }catch (NullPointerException _) {}
            addToConsole("Shape not found");
        } else {
            addToConsole(shape.toString());
            drawShape(shape);

        }
    }

    private void drawShape(RectangleShape shape) {
        // Re-override the paintComponent method to draw the rectangle
        // Display panel inside main Panel
        displayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);  // Call superclass to ensure proper painting
                shape.draw(g);  // Call the draw method of RectangleShape to draw the rectangle
            }
        };


        displayPanel.setLayout(null); // Set the layout of the display panel to null to accommodate the layer pane
        displayPanel.setPreferredSize(new Dimension(600,800));
        displayPanel.setBackground(new Color(192,192,192)); // set the background of the display panel

        // Add the display panel back into the frame
        centerPanel.add(displayPanel, BorderLayout.CENTER); // Adding inner panel to main panel
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void sortRectangles() {
        centerPanel.remove(displayPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
        // Sort list by ID and display on console
        // call get sorted rectangles to sort the rectangles
        ArrayList<RectangleShape> sortedRectanglesList = rm.getSortedRectangles(rectangleList);

        // Display the list in a sorted format
        StringBuilder allShapeDetails = new StringBuilder();
        for (RectangleShape rs : sortedRectanglesList) {
            allShapeDetails.append(rs.toString());
            allShapeDetails.append("\n\n");
        }
        addToConsole(String.valueOf(allShapeDetails));

        setDefaultFieldValues();
    }



    @Override
    public void insertUpdate(DocumentEvent e) {
        String textValue = idField.getText();

        try{
//            String lastDigit = textValue.substring(textValue.length()-1);
            Integer.parseInt(textValue);// Turn the whole text check if the text is an integer
            widthField.setEditable(true); // Allow user to edit the value
            widthLabel.setText("Width (Even Only):");
            heightField.setEditable(true); // Allow user to edit the value
            heightlabel.setText("Height:");
//            int lastNumber = Integer.parseInt(lastDigit); // Turn the last value to an integer
//            if(lastNumber % 2 == 0 && lastNumber != 0/*The last number is even and not equal to 0*/){
//                widthLabel.setText("Width:"); // Any width accepted
//                heightlabel.setText("Height (Odd only):"); // Odd height only
//            }else {
//                widthLabel.setText("Width (Even Only):"); // Even width only
//                heightlabel.setText("Height:"); // Any height accepted
//            }
            if(textValue.length() >= 7){
                widthLabel.setText("Width (ID must be a number):");
                widthField.setEditable(false); // Remove access from typing a width
                heightlabel.setText("Height (ID must be a number):");
                heightField.setEditable(false);
            }
        }catch(NumberFormatException nfe){ // In the case where the text is not an integer: String, Long, etc..
            widthLabel.setText("Width (ID must be a number):");
            widthField.setEditable(false); // Remove access from typing a width
            heightlabel.setText("Height (ID must be a number):");
            heightField.setEditable(false);
        }catch (Exception ex){ // In the case where there is an unexpected error
            widthLabel.setText("Width:");
            heightlabel.setText("Height:");
        };
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        insertUpdate(e); // Code in insert so no need to re-write

    }
    @Override
    public void changedUpdate(DocumentEvent e) {
        insertUpdate(e); // Code in insert so no need to re-write
    }

    public static void main(String[] args){
        new MainFrame();
    }

}
