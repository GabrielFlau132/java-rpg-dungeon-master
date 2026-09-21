
public class Bastao extends Item {

    public Bastao() {
        super("Bastao");
    }

    public void usar(Personagem personagem) {
        System.out.println("Voce treinou acrobacias com o bastao e ganhou 30 pontos de experiencia.");
        personagem.ganharExp(30);
    }
}
