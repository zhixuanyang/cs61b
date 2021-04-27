public class NBody {
    public static double readRadius(String Input){
        In in = new In(Input);
        int NumberOfPlanets = in.readInt();
        double Radius = in.readDouble();
        return Radius;
    }

    public static Planet[] readPlanets(String Input){
        In in = new In(Input);
        int NumberOfPlanets = in.readInt();
        double Radius = in.readDouble();
        Planet[] P = new Planet[NumberOfPlanets];
        for(int i = 0; i < NumberOfPlanets; i++){
            P[i] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return P;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        Planet[] P = NBody.readPlanets(filename);
        StdDraw.setScale(-radius, radius);
        String imageToDraw = "images/starfield.jpg";
        StdDraw.picture(0, 0, imageToDraw);
        for(int i = 0; i < P.length;i++){
            P[i].draw();
        }
        StdDraw.enableDoubleBuffering();
        for(double time = 0; time < T; time += dt){
            double[] xForces = new double[P.length];
            double[] yForces = new double[P.length];
            for(int i = 0; i < P.length; i++) {
                xForces[i] = P[i].calcNetForceExertedByX(P);
                yForces[i] = P[i].calcNetForceExertedByY(P);
            }
            for(int i = 0; i<P.length;i++) {
                P[i].update(time, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, imageToDraw);
            for(int i = 0; i<P.length;i++){
                P[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", P.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < P.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    P[i].xxPos, P[i].yyPos, P[i].xxVel,
                    P[i].yyVel, P[i].mass, P[i].imgFileName);
        }
    }
}
