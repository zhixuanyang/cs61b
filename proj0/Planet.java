public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 6.67e-11;
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet P){
        xxPos = P.xxPos;
        yyPos = P.yyPos;
        xxVel = P.xxVel;
        yyVel = P.yyVel;
        mass = P.mass;
        imgFileName = P.imgFileName;
    }
    public double calcDistance(Planet P){
        double result;
        double x_result;
        double y_result;
        x_result = P.xxPos - this.xxPos;
        y_result = P.yyPos- this.yyPos;
        result = x_result * x_result + y_result * y_result;
        return Math.sqrt(result);
    }
    public double calcForceExertedBy(Planet P){
        double force;
        force = (Planet.G * this.mass * P.mass)/(this.calcDistance(P) * this.calcDistance(P));
        return force;
    }

    public double calcForceExertedByX(Planet P){
        double result;
        result = (this.calcForceExertedBy(P) * (P.xxPos - this.xxPos))/this.calcDistance(P);
        return result;
    }

    public double calcForceExertedByY(Planet P){
        double result;
        result = (this.calcForceExertedBy(P) * (P.yyPos - this.yyPos))/this.calcDistance(P);
        return result;
    }

    public double calcNetForceExertedByX(Planet[] P){
        double result = 0;
        for(int i = 0; i < P.length; i++){
            if(this.equals(P[i])){
                continue;
            }
            else {
                result += this.calcForceExertedByX(P[i]);
            }
        }
        return result;
    }

    public double calcNetForceExertedByY(Planet[] P){
        double result = 0;
        for(int i = 0; i < P.length; i++){
            if(this.equals(P[i])){
                continue;
            }
            else{
                result += this.calcForceExertedByY(P[i]);
            }
        }
        return result;
    }

    public void update(double dt, double fX, double fY){
        double acceleration_x = fX/this.mass;
        double acceleration_y = fY/this.mass;
        this.xxVel = this.xxVel + dt * acceleration_x;
        this.yyVel = this.yyVel + dt * acceleration_y;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}
