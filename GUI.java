

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author James D. McMillian
 */
public class GUI extends javax.swing.JFrame {
    // GUI Class Constructor

    private MapPanel mvarSOMMap;
    private SOM mvarSelfOrganizingMap;

    public GUI(SOM SelfOrganizingMap) {
        mvarSelfOrganizingMap = SelfOrganizingMap;
        //  This function call build the GUI
        initializeComponents();
        this.setTitle("Self Organizing Map");
    }

    public MapPanel getMapPanelInstance() {
        return mvarSOMMap;
    }

    public void setCurrentError(double vError) {
        this.statusError.setText(String.valueOf(vError));
    }

    public void setCurrentEpoch(double vEpoch) {
        this.statusEpoch.setText(String.valueOf(vEpoch));
    }

    public void setCurrentIteration(double vIteration) {
        this.statusIteration.setText(String.valueOf(vIteration));
    }

    public void updateGridInformation(java.util.LinkedList<MapPanel.mapPointInformation> MapPoints) {
        // call the SOM in evaluate mode, and plot the grid information
        mvarSOMMap.setMapPoints(MapPoints);
    }
    //  Create the toolbar/button bar
    //  start, stop, reset, adjust horizontal output, adjust vertical output
    javax.swing.JPanel topPanel;
    javax.swing.JButton startButton;
    javax.swing.JButton stopButton;
    javax.swing.JButton resetButton;
    //  This is the status update panel, contains a list of entries and their values
    javax.swing.JPanel leftPanel;
    //  bottom panel is status information, slplit horizontally with text boxes like iteration #, Error, etc....
    javax.swing.JPanel statusPanel;
    javax.swing.JTextField statusError;
    javax.swing.JTextField statusErrorTitle;
    javax.swing.JTextField statusEpoch;
    javax.swing.JTextField statusEpochTitle;
    javax.swing.JTextField statusIteration;
    javax.swing.JTextField statusIterationTitle;

    public final void initializeComponents() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setLayout(new java.awt.BorderLayout());
        //
        mvarSOMMap = new MapPanel(mvarSelfOrganizingMap);
        mvarSOMMap.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mvarSOMMap.setBackground(Color.DARK_GRAY);
        this.getContentPane().add(mvarSOMMap, java.awt.BorderLayout.CENTER);

        //
        topPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        startButton = new javax.swing.JButton("Start");
        stopButton = new javax.swing.JButton("Stop");
        resetButton = new javax.swing.JButton("Reset");
        topPanel.add(startButton);
        topPanel.add(stopButton);
        topPanel.add(resetButton);
        this.getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);
        //  This is the status update panel, contains a list of entries and their values
        leftPanel = new javax.swing.JPanel();
        this.getContentPane().add(leftPanel, java.awt.BorderLayout.WEST);
        //  bottom panel is status information, slplit horizontally with text boxes like iteration #, Error, etc....
        
        statusPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        
        
        statusErrorTitle = new JTextField();
        statusErrorTitle.setEditable(false);
        statusErrorTitle.setBorder(BorderFactory.createEmptyBorder());
        statusErrorTitle.setHorizontalAlignment(JTextField.RIGHT);
        statusErrorTitle.setText("Error:");
        
        statusError = new JTextField(10);
        statusError.setEditable(false);
        statusError.setSize(100,20);
        
        statusEpochTitle = new JTextField(5);
        statusEpochTitle.setEditable(false);
        statusEpochTitle.setBorder(BorderFactory.createEmptyBorder());
        statusEpochTitle.setHorizontalAlignment(JTextField.RIGHT);
        statusEpochTitle.setText("Epoch:");
        
        statusEpoch = new JTextField(10);
        statusEpoch.setEditable(false);
        statusEpoch.setSize(100,20);
        
        statusIterationTitle = new JTextField(5);
        statusIterationTitle.setEditable(false);
        statusIterationTitle.setBorder(BorderFactory.createEmptyBorder());
        statusIterationTitle.setHorizontalAlignment(JTextField.RIGHT);
        statusIterationTitle.setText("Iteration:");
        
        statusIteration = new JTextField(10);
        statusIteration.setEditable(false);
        statusIteration.setSize(100,20);
        
        statusPanel.add(statusErrorTitle);
        statusPanel.add(statusError);
        statusPanel.add(statusEpochTitle);
        statusPanel.add(statusEpoch);
        statusPanel.add(statusIterationTitle);
        statusPanel.add(statusIteration);
        statusPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        this.getContentPane().add(statusPanel, java.awt.BorderLayout.SOUTH);
        //

//        this.pack();
//        this.setPreferredSize(new Dimension(800, 600));
        this.validate();
//        this.validateTree();
        this.setVisible(true);
    }
    
}//end GUI class
