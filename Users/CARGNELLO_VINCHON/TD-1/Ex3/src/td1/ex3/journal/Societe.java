package td1.ex3.journal;

import java.util.ArrayList;

import td1.ex3.abonne.Abonne;
import td1.ex3.abonne.AbonneFullText;
import td1.ex3.abonne.AbonneFullTextOnly;
import td1.ex3.abonne.AbonneSMS;

/**
 * Societe
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class Societe 
{
	private ArrayList<Abonne> listeAbonnes;
	private ArrayList<Article> listeArticles;
	
	/**
	 * Constructeur
	 */
	public Societe()
	{ 
		listeAbonnes = new ArrayList<Abonne>();
		listeArticles = new ArrayList<Article>();
	}
	
	/**
	 * Main
	 * 
	 * @param args Argument
	 */
	public static void main(String[] args)
	{
		Societe s = new Societe();
		
		Article a1 = new Article();
		a1.setTitre("Article 1");
		a1.setDescription("Je suis le texte de l'article 1");
		
		Resume r1 = new Resume();
		r1.setTitre("Résumé 1");
		r1.setDescription("Je suis le texte du résumé 1");
		r1.setArticle(a1);
		
		a1.setResume(r1);
		
		s.addArticle(a1);
		
		AbonneSMS ab1 = new AbonneSMS("Smith", "John", "john.smith@email.com");
		AbonneFullTextOnly ab2 = new AbonneFullTextOnly("Dupond", "Daniel", "daniel.dupond@email.com");
		AbonneFullText ab3 = new AbonneFullText("Dubois","Henri","henri.dubois@email.com");
		ab1.addArticle(a1);
		ab2.addArticle(a1);
		ab3.addArticle(a1);
		
		s.addAbonne(ab1);
		s.addAbonne(ab2);
		s.addAbonne(ab3);
		
		System.out.println("Abonné SMS :");
		ab1.lireResume(0);
		
		System.out.println("Abonné FullTextOnly :");
		ab2.lireArticle(0);
		
		System.out.println("Abonné FullText :");
		ab3.lireArticle(0);
		ab3.lireResume(0);
	}
	
	/**
	 * Ajouter un abonné dans la liste
	 * 
	 * @param a Abonné
	 */
	public void addAbonne(Abonne a)
	{
		listeAbonnes.add(a);
	}
	
	/**
	 * Ajouter un article dans la liste
	 * 
	 * @param a Article
	 */
	public void addArticle(Article a)
	{
		listeArticles.add(a);
	}
}
