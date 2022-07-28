package Mypack;

public class PredictionAnalysis {

    public static double predict(double previousVal, double slope, long tNext,long tPrev) {
        System.out.println("Predict "+previousVal+" "+ slope+" "+ tNext);
        double next = previousVal + slope*(tNext-tPrev);
        return next;
    }
    public static double slope(double a, double b,long ta, long tb) {
        System.out.println("slope "+a +" "+ b +" "+  ta +" "+ tb);
        double m = (a-b) / (ta-tb);
        return m;
        
    }
}
