
public class AbonneFull extends Abonne{
	

	public AbonneFull(String article, String resume) {
		super(article, resume);
		// TODO Auto-generated constructor stub
	}

	public void afficherContenu(){
		System.out.println("Affichage du résume : "+this.getResume()+" Affichage de l'article : "+this.getArticle());
		
	}
	
	public static void main (String[] args){
		
		AbonneFull aFull = new AbonneFull("Supernova", "L'histoire du soleil");
		aFull.afficherContenu();
		
		AbonneSMS aSMS = new AbonneSMS("Supernova", "L'histoire du soleil");
		aSMS.afficherContenu();
		
		AbonnetxtOnly aTxtOnly = new AbonnetxtOnly("Supernova", "L'histoire du soleil");
		aTxtOnly.afficherContenu();
		
	}
	
	
	
	
}
