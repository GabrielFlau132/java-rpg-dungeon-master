
public class ArcoDeEnergia extends Item {

    public ArcoDeEnergia() {
        super("Arco de Energia");
    }

    public void usar(Personagem personagem) {
        personagem.curarVida(20);
        personagem.curarMana(20);
        System.out.println("A energia do arco recuperou 20 de vida e 20 de mana!");
    }
}
