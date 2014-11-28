package simpletype;

public class SimpleType {
/**
*
@param args the command line arguments
*/
	
public static void main(String[] args) {
	//TODO code application logic here
	byte b = java.lang.Byte.MAX_VALUE;
	int i = java.lang.Integer.MAX_VALUE;
	double d = java.lang.Double.MAX_VALUE;
	System.err.println("byte:" +b+ " / int:" +i+ " / double:" +d);
	b++;
	i++;
	d++;
	System.err.println("byte:"+b+" / int:"+i+" / double:"+d);
	/**
	 * lorsqu'on incrémente b , on obtient la valeur MIN_VALUE d'un byte car il y a un débordement (Cf : Complément)
	 * lorsque l'on incrémente i, on obtient la valeur MIN_VALUE d'Integer car il y a également un débordement ici
	 * c'est le même principe que pour le byte.
	 * 
	 * Idem pour le d
	 */
	 d-=java.lang.Double.MAX_VALUE;
	 i--;
	 b--;
	 System.err.println("byte:"+b+" / int:"+i+" / double:"+d);
	/**
	 * lorsque l'on décremente b, on obtient la valeur MAX_VALUE d'un byte car il y a un débordement
	 * lorsque l'on décrémente i, le phénomène de débordement se reproduit
	 * un int est codé sur 32 bits, donc: lorsqu'on incrémente d, on obtient 0 car débordement
	 */

	}
}