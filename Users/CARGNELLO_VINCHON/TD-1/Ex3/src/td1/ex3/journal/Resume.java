package td1.ex3.journal;

/**
 * Resume
 * 
 * @author rodolphe-c
 * @author k-vinchon
 *
 */
public class Resume 
{
	private String titre;
	private String description;
	private Article article;
	
	/**
	 * Constructeur
	 */
	public Resume()
	{
		this.titre = new String();
		this.description = new String();
		article = new Article();
	}

	/**
	 * Retourne le titre du résumé
	 * 
	 * @return titre Titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * Modifie le titre du résumé 
	 * 
	 * @param titre Titre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * Retourne la description du résumé
	 * 
	 * @return description Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Modifie la description du résumé
	 * 
	 * @param description Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retourne l'article du résumé
	 * 
	 * @return article Article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * Modifie l'article du résumé
	 * 
	 * @param article Article
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
}
