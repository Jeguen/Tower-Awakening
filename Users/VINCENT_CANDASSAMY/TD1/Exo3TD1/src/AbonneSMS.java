
public class AbonneSMS extends Abonne{

	public AbonneSMS(String article, String resume) {
		super(article, resume);
		// TODO Auto-generated constructor stub
	}

	public void afficherContenu(){
		System.out.println("Affichage du résume : "+this.getResume()+" ");
		
	}
}
