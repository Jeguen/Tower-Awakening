
public class AbonnetxtOnly extends Abonne{
	
	

	public AbonnetxtOnly(String article, String resume) {
		super(article, resume);
	}

	public void afficherContenu(){
		System.out.println("Affichage de l'article : "+this.getArticle() + " ");
		
	}
}

