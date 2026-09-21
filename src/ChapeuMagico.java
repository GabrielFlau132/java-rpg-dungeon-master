
public class ChapeuMagico extends Item {

    public ChapeuMagico() {
        super("Chapeu Magico");
    }

    public void usar(Personagem personagem) {
        personagem.curarMana(40);
        System.out.println("O chapeu magico soltou uma magia e recuperou 40 de mana para " + personagem.getNome() + ".");
    }
}
