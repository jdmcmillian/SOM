

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author James D. McMillian
 */
public class SOM extends java.lang.Thread {

    //private gui.GUI mvarMainGUI;
    private Neuron[][] mvarOutputs;
    private int mvarIteration;
    private int mvarEpoch;
    private int mvarHeight;
    private int mvarWidth;
    private int mvarInputDimensions;
    private java.util.Set<NUTList.selectableFeatures> mvarSelectedFeatures;
    private java.util.Random rnd = new java.util.Random();
    private NUTList patterns;
    private ArrayList updateListeners = new ArrayList();

    public SOM(java.util.Set<NUTList.selectableFeatures> SelectedFeatures, int Width, int Height) {
        this.mvarWidth = Width;
        this.mvarHeight = Height;
        this.mvarInputDimensions = SelectedFeatures.size();
        this.mvarSelectedFeatures = SelectedFeatures;
        BuildSOMLayers();
    }

    public void addUpdateListener(UpdateEventListener listenerObject) {
        updateListeners.add(listenerObject);
    }//end addUpdateListener method

    private synchronized void fireUpdateEvenet(double error, int epoch, int iteration) {

        UpdateEvent event = new UpdateEvent(this, error, epoch, iteration);

        Iterator iterator = this.updateListeners.iterator();

        while (iterator.hasNext()) {
            UpdateEventListener listener = (UpdateEventListener) iterator.next();
            listener.handleUpdateEvent(event);
        }//end while

    }//end fireUpdateEvent method

    public void setPatterns(NUTList patterns) {
        this.patterns = patterns;
    }//end setPatterns method

    public int getWidth() {
        return this.mvarWidth;
    }

    public int getHeight() {
        return this.mvarHeight;
    }

    public java.util.Set<NUTList.selectableFeatures> getSelectedFeatures() {
        return mvarSelectedFeatures;
    }

    public void BuildSOMLayers() {
        mvarOutputs = new Neuron[mvarHeight][mvarHeight];
        for (int i = 0; i < mvarWidth; i++) {
            for (int j = 0; j < mvarHeight; j++) {
                mvarOutputs[i][j] = new Neuron(i, j, mvarHeight);
                mvarOutputs[i][j].Weights = new double[mvarInputDimensions];
                for (int k = 0; k < mvarInputDimensions; k++) {
                    mvarOutputs[i][j].Weights[k] = rnd.nextDouble();
                }
            }
        }
    }

    public void Train(double maxError, NUTList patterns, Integer UpdateInterval) {
        boolean update = false;
        int lvUpdateInterval = 0;

        if (Rules.UPDATE_INTERVAL > 0) {
            update = true;
            lvUpdateInterval = Rules.UPDATE_INTERVAL;
        }

        mvarIteration = 0;
        mvarEpoch = 0;
        Double currentError = Double.MAX_VALUE;

        Integer[] PatternKeyList;
        //preload Pattern List
        PatternKeyList = patterns.getNutritionKeys();

        runTraining = true;
        while (runTraining && currentError > maxError) {
            //mvarIteration = 0;
            currentError = 0.0;
            mvarEpoch++;

            NUTList.NutritionalDataEntry LARGENUTLIST[] = patterns.getNutrientTree().values().toArray(new NUTList.NutritionalDataEntry[patterns.Size()]);

            //shuffle entries
            //LinkedList<NUTList.NutritionalDataEntry> newpatternList = new LinkedList<NUTList.NutritionalDataEntry>();
            {// local temporary variables - This will speed things up, so that the variables arent constantly being created and destroyed
                NUTList.NutritionalDataEntry NUTE1;
                NUTList.NutritionalDataEntry NUTE2;
                int index1 = 0;
                int index2 = 0;
                for (index1 = 0; index1 < PatternKeyList.length; index1++) {
                    index2 = rnd.nextInt(PatternKeyList.length);    // pick another random entry
                    //  collect the key values
                    NUTE1 = LARGENUTLIST[index1];
                    NUTE2 = LARGENUTLIST[index2];
                    // SWAP
                    LARGENUTLIST[index2] = NUTE1;
                    LARGENUTLIST[index1] = NUTE2;
                }
            }

            for (int i = 0; runTraining && i < LARGENUTLIST.length; i++) {
                currentError += TrainPattern(LARGENUTLIST[i].getFeatureSet(mvarSelectedFeatures));
                if (update && (mvarIteration % Rules.UPDATE_INTERVAL) == 0) {
                    if (Rules.UPDATE_INTERVAL > 0) {
                        this.fireUpdateEvenet(-1, -1, mvarIteration);
                    }
                }
            }

            if (update) {// && (mvarIteration % lvUpdateInterval) == 0) {
                this.fireUpdateEvenet(currentError, mvarEpoch, mvarIteration);
            }

        } 
    }

    public Double TrainPattern(double[] pattern) {
        double error = 0;
        Neuron winner = Winner(pattern);
        for (int i = 0; i < mvarWidth; i++) {
            for (int j = 0; j < mvarHeight; j++) {
                error += mvarOutputs[i][j].UpdateWeights(pattern, winner, mvarEpoch);// mvarIteration);
            }
        }
        mvarIteration++;

        return Math.abs(error / (mvarHeight * mvarHeight));
    }

    public Neuron Winner(double[] pattern) {
        Neuron winner = null;
        Double min = Double.MAX_VALUE;
        for (int i = 0; i < mvarWidth; i++) {
            for (int j = 0; j < mvarHeight; j++) {
                Double d = EuclideanDistance(pattern, mvarOutputs[i][j].Weights);
                if (d < min) {
                    min = d;
                    winner = mvarOutputs[i][j];
                }
            }
        }
        return winner;
    }

    public Double EuclideanDistance(double[] vector1, double[] vector2) {
        Double value = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            value += Math.pow((vector1[i] - vector2[i]), 2);
        }
        return Math.sqrt(value);
    }

    @Override
    public void run() {
        this.Train(Rules.MAX_ERROR, this.patterns, Rules.UPDATE_INTERVAL);
    }//end run override
    private boolean runTraining = false;

//    Integer MUTEX=new Integer(1);
//
//    public void startTraining() {
//        synchronized (MUTEX) {
//            this.start();
//        }
//    }
    public void stopTraining() {
        runTraining = false;
    }

    public class UpdateEvent extends java.util.EventObject {

        public double error;
        public int epoch;
        public int iteration;

        public UpdateEvent(Object source, double error, int epoch, int iteration) {
            super(source);
            this.error = error;
            this.epoch = epoch;
            this.iteration = iteration;
        }//end constructor
    }//end UpdateEvent class

    public interface UpdateEventListener {

        public void handleUpdateEvent(UpdateEvent event);
    }//end UpdateEventListener interface
}//end SOM class
