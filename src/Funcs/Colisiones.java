package Funcs;

public class Colisiones {

	//clase para colisionesss
	
	public static boolean checkRect(double x , double y ,double width , double height,
			double x2 , double y2 ,double width2 , double height2
			
			) {
		return x < x2 + width2 && x + width > x2
				&& y < y2 + height2 && y + height > y2;
		//si toda esta cuenta es cierto retorna true entonces significa que colisiono.
	}
	
	
	//is_on_floor detecta si el objeto esta tocando el suelo :::
	
	public static boolean is_on_floor(double x , double y ,double width , double height,
			double x2 , double y2 ,double width2 , double height2) {
		
		return x > x2 - width2 / 2
				&& x < x2 + width2 / 2
				&& Math.abs(y - (y2 - height2 / 2)) <= 5;
		
	}
	
	
}
