
public abstract class Abonne implements Contenu {
	protected String article;
	protected String resume;
	
	public Abonne(String article, String resume) {
		this.article = article;
		this.resume = resume;
	}
	
	
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getResume() {
		return resume;
	}
	
	public void setResume(String resume) {
		this.resume = resume;
	}
	
	public abstract void afficherContenu();
}
