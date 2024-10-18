package org.example.Generator;



import java.text.DecimalFormat;

public class Generator {
    public static void main(String[] args) {
        System.out.println((int)(Math.random()*200));
    }
    public static Double generate(int max, int min, int round){
        int delta = max-min;
        double val = min+Math.random()*delta;
        return round(val);
    }
//    @Scheduled(fixedDelayString = "300")
//    public void update()  {
//        System.out.println("123");
//
//    }
    public static Double round(double val){
        String pattern = "#.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String result1 = decimalFormat.format(val);
        return Double.valueOf((result1.replace(",",".")));
    }
}
