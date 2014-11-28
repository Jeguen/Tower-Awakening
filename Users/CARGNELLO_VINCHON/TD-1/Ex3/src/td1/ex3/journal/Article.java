package td1.ex3.journal;

/**
 * Article
 * 
 * @author rodolphe-c
 * @author k-vinchon
 * 
 */
public class Article 
{
	private String titre;
	private String description;
	private Resume resume;
	
	/**
	 * Constructeur
	 */
	public Article()
	{
		this.titre = new String();
		this.description = new String();
	}

	/**
	 * Retourne le titre de l'article
	 * 
	 * @return titre Titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * Modifie le titre de l'article
	 * 
	 * @param titre Titre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * Retourne la description de l'article
	 * 
	 * @return description Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Modifie la description de l'article
	 * 
	 * @param description Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retourne le résumé de l'article
	 * 
	 * @return resume Résumé
	 */
	public Resume getResume() {
		return resume;
	}

	/**
	 * Modifie le résumé de l'article
	 * 
	 * @param resume Résumé
	 */
	public void setResume(Resume resume) {
		this.resume = resume;
	}
}
