
public class Clava extends Item {

    public Clava() {
        super("Clava");
    }

    public void usar(Personagem personagem) {
        personagem.aumentarDano(5);
        System.out.println("A clava deixou seus ataques mais fortes! O dano base de " + personagem.getNome() + " aumentou em 5.");
    }
}
