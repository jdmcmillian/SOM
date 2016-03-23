/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Zanza259
 */
public class JMapPanel extends javax.swing.JPanel {

    
    private static final long serialVersionUID = 7528474872067939033L;
    private int GRID_WIDTH;
    private int GRID_HEIGHT;
    private SOM mvarSOM;
    private java.util.LinkedList<JMapPanel.mapPointInformation> mvarMapPoints = new java.util.LinkedList<JMapPanel.mapPointInformation>();
    private boolean linked = false;
    
    
    public JMapPanel() {
        initComponents();
    }//end constructor
    
    public void linkSOM(SOM SelfOrganizingMap) {
        mvarSOM = SelfOrganizingMap;
        GRID_WIDTH = mvarSOM.getWidth();
        GRID_HEIGHT = mvarSOM.getHeight();
        linked = true;
    }//end linkSOM methoc

    public String[] getAllShortDescriptions() {
        
        String[] temp = new String[mvarMapPoints.size()];
        
        for (int i = 0; i < temp.length; i++) {
            temp[i] = mvarMapPoints.get(i).mvarNutInfo.getShrt_Desc();
        }//end for i
        
        return temp;
        
    }//end getAllShortDescriptions method

    public String getFormattedPointInformationAt(Point p) {
        
        StringBuilder builder = new StringBuilder("");
        
        
        int CELL_WIDTH = this.getWidth() / this.GRID_WIDTH;
        int CELL_HEIGHT = this.getHeight() / this.GRID_HEIGHT;
        Point temp = new Point(p.x/CELL_WIDTH,p.y/CELL_HEIGHT);
        
        for (mapPointInformation pointInformation : mvarMapPoints) {
            
            if (pointInformation.getCoordinate().equals(temp)) {
                
                NUTList.NutritionalDataEntry nutEntry = pointInformation.getNutinfo();
                builder.append(nutEntry.Shrt_Desc).append("\n");
                builder.append("NDB: ").append(nutEntry.NDB_No).append("\n");
                
                builder.append("Calcium(mg): ").append(nutEntry.Calcium_mg).append("\n");
                builder.append("Carbohydrate(g): ").append(nutEntry.Carbohydrt_g).append("\n");
                builder.append("Cholesterol(mg): ").append(nutEntry.Cholestrl_mg).append("\n");
                builder.append("Energy(Kcal): ").append(nutEntry.Energ_Kcal).append("\n");
                builder.append("Fiber(g): ").append(nutEntry.Fiber_TD_g).append("\n");
                builder.append("GmWt: ").append(nutEntry.GmWt_1).append("\n");
                builder.append("Iron(mg): ").append(nutEntry.Iron_mg).append("\n");
                builder.append("Lipid(g): ").append(nutEntry.Lipid_Tot_g).append("\n");
                builder.append("Potassium(mg): ").append(nutEntry.Potassium_mg).append("\n");
                builder.append("Protein(g): ").append(nutEntry.Protein_g).append("\n");
                builder.append("Sodium(mg): ").append(nutEntry.Sodium_mg).append("\n");
                builder.append("Water(g): ").append(nutEntry.Water_g).append("\n");
                
                builder.append("\n");
                
            }//end if
            
        }//end for e
        
        return builder.toString();
        
    }//end getFormattedPointInformationAt method
    
    
    public JMapPanel.mapPointInformation newMapPoint(NUTList.NutritionalDataEntry nutinfolist, Color primaryColor, Color alternateColor) {
        return new JMapPanel.mapPointInformation(nutinfolist, primaryColor, alternateColor);
    }

    public void addMapPoints(NUTList NutPatternSet, java.awt.Color vColor, java.awt.Color vAlternateColor) {
        Integer[] PatternKeyList = NutPatternSet.getNutritionKeys();
        //java.util.LinkedList<gui.JMapPanel.mapPointInformation> allPoints = new java.util.LinkedList<gui.JMapPanel.mapPointInformation>();
        for (Integer key : PatternKeyList) {
            NUTList.NutritionalDataEntry nutinfo = NutPatternSet.getNutritionEntry(key);

            JMapPanel.mapPointInformation mapPoint = new JMapPanel.mapPointInformation(nutinfo, vColor, vColor);
            if (mvarMapPoints.contains(mapPoint));
            mvarMapPoints.add(mapPoint);
            //System.out.printf("(%d, %d) %s\n", mapPoint.getCoordinate().x, mapPoint.getCoordinate().y, mapPoint.getLabel());
        }
        this.repaint();
    }

    public void setMapPoints(java.util.LinkedList<JMapPanel.mapPointInformation> MapPoints) {
        mvarMapPoints = MapPoints;
        this.repaint();
    }

    public void clearMapPoints() {
        mvarMapPoints.clear();
        this.repaint();
    }

    public void updateMapPoints() {
        //this.setVisible(true);
        //this.validate();
        //this.updateUI();
        this.repaint();
    }

    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (linked) {
            
            int CELL_WIDTH = this.getWidth() / this.GRID_WIDTH;
            int CELL_HEIGHT = this.getHeight() / this.GRID_HEIGHT;
            JMapPanel.mapPointInformation pointInformation;

            
            for (int i = 0; i < mvarMapPoints.size(); i++) {

                pointInformation = mvarMapPoints.get(i);
                pointInformation.updateInformation();

                java.awt.Point P = pointInformation.getCoordinate();
                java.awt.Color C1 = pointInformation.mvarPrimaryColor;
                java.awt.Color C2 = pointInformation.mvarAlternateColor;
                String Label = pointInformation.mvarNutInfo.Shrt_Desc;
                g.setColor(C1);
                g.fillRect(P.x * CELL_WIDTH, P.y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }//end for i
            
        }//end if

//        if(mvarMapPoints.size()>12) 
//            System.out.printf("#0 (%d, %d) vs #12 (%d, %d) \n", mvarMapPoints.get(0).mvarNeuron.x, mvarMapPoints.get(0).mvarNeuron.y, mvarMapPoints.get(12).mvarNeuron.x, mvarMapPoints.get(12).mvarNeuron.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    
    
    public class mapPointInformation {

        private NUTList.NutritionalDataEntry mvarNutInfo;
        private java.awt.Color mvarPrimaryColor;
        private java.awt.Color mvarAlternateColor;
        private Neuron mvarNeuron;// this changes!!!!!!
        private Point mvarCoordinatePoint;
        private String mvarLabel;

        public mapPointInformation(NUTList.NutritionalDataEntry nutinfo, Color primaryColor, Color alternateColor) {
            this.mvarNutInfo = nutinfo;
            this.mvarPrimaryColor = primaryColor;
            this.mvarAlternateColor = alternateColor;
            this.mvarNeuron = mvarSOM.Winner(nutinfo.getFeatureSet(mvarSOM.getSelectedFeatures()));
            mvarCoordinatePoint = new Point(this.mvarNeuron.x, this.mvarNeuron.y);
            mvarLabel = mvarNutInfo.Shrt_Desc;
        }

        public void updateInformation() {
            this.mvarNeuron = mvarSOM.Winner(mvarNutInfo.getFeatureSet(mvarSOM.getSelectedFeatures()));
            mvarCoordinatePoint = new Point(this.mvarNeuron.x, this.mvarNeuron.y);
        }

        public Color getAlternateColor() {
            return mvarAlternateColor;
        }

        public NUTList.NutritionalDataEntry getNutinfo() {
            return mvarNutInfo;
        }

        public Color getPrimaryColr() {
            return mvarPrimaryColor;
        }

        public Point getCoordinate() {
            return mvarCoordinatePoint;
        }

        public String getLabel() {
            return mvarLabel;
        }

        public Neuron getOutputNeuron() {
            this.mvarNeuron = mvarSOM.Winner(mvarNutInfo.getFeatureSet(mvarSOM.getSelectedFeatures()));
            return this.mvarNeuron;
        }
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}//end JMapPanel class
