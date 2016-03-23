

/**
 *
 * @author James D. McMillian
 */
public class Neuron {

    public double[] Weights;
    public int x;
    public int y;
    private int length;
    private double nf;
    
    private static final int magicnumber = 50;

    public Neuron(int x, int y, int length) {
        this.x = x;
        this.y = y;
        this.length = length;
        nf = magicnumber / Math.log(length);
    }

    public double Gauss(Neuron win, int it) {
        double distance = Math.sqrt(Math.pow(win.x - this.x, 2) + Math.pow(win.y - this.y, 2));
        return Math.exp(-Math.pow(distance, 2) / (Math.pow(Strength(it), 2)));
    }

    public double LearningRate(int it) {
        return Math.exp(-it / magicnumber) * 0.1;
    }

    public double Strength(int it) {
        return Math.exp(-it / nf) * length;
    }

    public double UpdateWeights(double[] pattern, Neuron winner, int iteration) {
        double sum = 0;
        for (int i = 0; i < Weights.length; i++) {
            double delta = LearningRate(iteration) * Gauss(winner, iteration) * (pattern[i] - Weights[i]);
            Weights[i] += delta;
            sum += delta;
        }
        return sum/Weights.length;
    }
}
