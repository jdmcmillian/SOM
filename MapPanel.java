

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 7528474872067939033L;
    private int GRID_WIDTH;
    private int GRID_HEIGHT;
    private SOM mvarSOM;
    private java.util.LinkedList<mapPointInformation> mvarMapPoints = new java.util.LinkedList<mapPointInformation>();

    public MapPanel(SOM SelfOrganizingMap) {
        mvarSOM = SelfOrganizingMap;
        GRID_WIDTH = mvarSOM.getWidth();
        GRID_HEIGHT = mvarSOM.getHeight();
    }

    public MapPanel.mapPointInformation newMapPoint(NUTList.NutritionalDataEntry nutinfolist, Color primaryColor, Color alternateColor) {
        return new mapPointInformation(nutinfolist, primaryColor, alternateColor);
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

    public void addMapPoints(NUTList NutPatternSet, java.awt.Color vColor, java.awt.Color vAlternateColor) {
        Integer[] PatternKeyList = NutPatternSet.getNutritionKeys();
        //java.util.LinkedList<gui.MapPanel.mapPointInformation> allPoints = new java.util.LinkedList<gui.MapPanel.mapPointInformation>();
        for (Integer key : PatternKeyList) {
            NUTList.NutritionalDataEntry nutinfo = NutPatternSet.getNutritionEntry(key);

            MapPanel.mapPointInformation mapPoint = new mapPointInformation(nutinfo, vColor, vColor);
            if (mvarMapPoints.contains(mapPoint));
            mvarMapPoints.add(mapPoint);
            //System.out.printf("(%d, %d) %s\n", mapPoint.getCoordinate().x, mapPoint.getCoordinate().y, mapPoint.getLabel());
        }
        this.repaint();
    }

    public void setMapPoints(java.util.LinkedList<mapPointInformation> MapPoints) {
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
        int CELL_WIDTH = this.getWidth() / this.GRID_WIDTH;
        int CELL_HEIGHT = this.getHeight() / this.GRID_HEIGHT;
        mapPointInformation pointInformation;

        for (int i = 0; i < mvarMapPoints.size(); i++) {

            pointInformation = mvarMapPoints.get(i);
            pointInformation.updateInformation();

            java.awt.Point P = pointInformation.getCoordinate();
            java.awt.Color C1 = pointInformation.mvarPrimaryColor;
            java.awt.Color C2 = pointInformation.mvarAlternateColor;
            String Label = pointInformation.mvarNutInfo.Shrt_Desc;
            g.setColor(C1);
            g.fillRect(P.x * CELL_WIDTH, P.y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
        }

//        if(mvarMapPoints.size()>12) 
//            System.out.printf("#0 (%d, %d) vs #12 (%d, %d) \n", mvarMapPoints.get(0).mvarNeuron.x, mvarMapPoints.get(0).mvarNeuron.y, mvarMapPoints.get(12).mvarNeuron.x, mvarMapPoints.get(12).mvarNeuron.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
