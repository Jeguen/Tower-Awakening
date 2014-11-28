package LayeredBattleField;

import java.util.HashMap;
/**
 * Matrice creuse compos� de GenericData
 * @author S Firegreen
 *
 * @param <GenericData> composant de la matrice
 */
public class BattleFieldMatrix<GenericData>{
	
	static int INDEXMAX=1000;
	/**
	 * Index permettant d'acceder � une BattleFieldMatrix
	 * @author S Firegreen
	 *
	 */
	public static class Index
	{
		private int ligne,colonne;
		
		public Index(int ligne, int colonne)
		{
			this.ligne=ligne; this.colonne = colonne;
		}
		
		public int getL() {
			return ligne;
		}

		public int getC() {
			return colonne;
		}

		public String toString()
		{
			return "i =" + ligne + " j =" + colonne;
		}
		public int hashCode()
		{
			return colonne*INDEXMAX+ligne;
		}
		
		public boolean equals(Object o)
		{
			if(o.getClass().equals(Index.class))
			{
				Index i = (Index)o;
				return this.ligne==i.ligne && this.colonne== i.colonne;
			}
			else
				return false;
		}
	}
	/**
	 * Matrice creuse de GenericData prenant des index comme cl�s
	 */
	protected HashMap<Index, GenericData> matrice = new HashMap<Index, GenericData>();
		
	/**
	 * Recup�re le GenericData aux coordonn�es sp�cifi�s
	 * @param numeroLigne 
	 * @param numeroColonne
	 * @return GenericData � la case(numeroLigne, numeroColonne)
	 */
	public GenericData get(int numeroLigne, int numeroColonne)
	{
		return matrice.get(new Index(numeroLigne, numeroColonne));
	}
	
	/**
	 * Rajoute un GenericData aux coordonn�es sp�cifi�s
	 */
	public void set(int numeroLigne, int numeroColonne, GenericData data)
	{
		if(numeroLigne<INDEXMAX && numeroColonne<INDEXMAX)
		matrice.put(new Index(numeroLigne, numeroColonne), data);
	}
	
	public void dispIndex()
	{
		for(Index i : matrice.keySet())
		{
			System.out.println(i);
		}
	}
	
	/**
	 * Verifie que le case de coordonn�e numeroLigne, num�roColonne n'est pas vide
	 * @param numeroLigne
	 * @param numeroColonne
	 * @return
	 */
	public boolean exists(int numeroLigne, int numeroColonne)
	{
		return matrice.containsKey(new Index(numeroLigne, numeroColonne));
	}
	public BattleFieldMatrix() {}

}
