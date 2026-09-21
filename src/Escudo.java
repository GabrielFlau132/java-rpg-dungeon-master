
public class Escudo extends Item {

    public Escudo() {
        super("Escudo");
    }

    public void usar(Personagem personagem) {
        personagem.aumentarDefesa(5);
        System.out.println("O escudo aumentou a defesa de " + personagem.getNome() + " em 5 pontos");
    }
}
