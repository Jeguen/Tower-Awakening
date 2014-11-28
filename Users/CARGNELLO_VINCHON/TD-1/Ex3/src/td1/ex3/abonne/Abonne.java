package td1.ex3.abonne;

import java.util.ArrayList;

import td1.ex3.journal.Article;

/**
 * Abonne
 * 
 * @author rodolphe-c
 * @author k-vinchon
 */
public abstract class Abonne 
{
	protected String nom;
	protected String prenom;
	protected String email;
	
	protected ArrayList<Article> listeArticles;

	/**
	 * Ajoute un article dans la liste
	 * 
	 * @param a Article
	 */
	public void addArticle(Article a)
	{
		this.listeArticles.add(a);
	}
	
}
