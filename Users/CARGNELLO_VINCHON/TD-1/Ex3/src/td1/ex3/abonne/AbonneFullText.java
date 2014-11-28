package td1.ex3.abonne;

import java.util.ArrayList;

import td1.ex3.interfaces.InterfaceArticle;
import td1.ex3.interfaces.InterfaceResume;
import td1.ex3.journal.*;

/**
 * AbonneFullText
 * 
 * @author rodolphe-c
 * @author k-vinchon
 * 
 */
public class AbonneFullText extends Abonne implements InterfaceArticle, InterfaceResume
{
	/**
	 * Constructeur
	 */
	public AbonneFullText()
	{
		this.nom = new String("Unknown");
		this.prenom = new String("Unknown");
		this.email = new String("Unknown");
		this.listeArticles = new ArrayList<Article>();
	}
	
	/**
	 * Constructeur
	 * 
	 * @param nom Nom
	 * @param prenom Prenom
	 * @param email Email
	 */
	public AbonneFullText(String nom, String prenom, String email)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.listeArticles = new ArrayList<Article>();
	}
	
	@Override
	public void lireArticle(int i) 
	{
		if (i > this.listeArticles.size())
		{
			throw new NullPointerException();
		}
		else
		{
			System.out.println(this.listeArticles.get(i).getTitre() + "\n");
			System.out.println(this.listeArticles.get(i).getDescription() + "\n");
		}
		
	}

	@Override
	public void lireResume(int i) throws IndexOutOfBoundsException
	{
		if (i > this.listeArticles.size())
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			System.out.println(this.listeArticles.get(i).getResume().getTitre() + "\n");
			System.out.println(this.listeArticles.get(i).getResume().getDescription() + "\n");
		}
	}

}
