package td1.ex3.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import td1.ex3.abonne.AbonneFullText;
import td1.ex3.abonne.AbonneFullTextOnly;
import td1.ex3.journal.Article;
import td1.ex3.journal.Resume;

public class AbonneFullTextOnlyTest {

	@Test
	public void test() 
	{
		Article a = new Article();
		a.setTitre("Titre article");
		a.setDescription("texte article");
		
		Resume r = new Resume();
		r.setTitre("Titre resume");
		r.setDescription("texte resume");
		r.setArticle(a);
		a.setResume(r);
		
		AbonneFullTextOnly ab = new AbonneFullTextOnly();
		ab.addArticle(a);
		
		try
		{
			ab.lireArticle(0);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("Erreur d'indice");
			fail("Erreur d'indice");
		}
		
		try
		{
			ab.lireArticle(1);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("Erreur d'indice");
			fail("Erreur d'indice");
		}
	}

}
