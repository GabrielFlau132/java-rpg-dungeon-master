
public class Paladino extends Personagem {

    public Paladino(String nome) {
        super(nome, 120, 80, 13, 8);
    }

    public void usarMagia() {
        if (mana >= 20) {
            mana = mana - 20;
            curarVida(40);
            System.out.println(nome + " usou Luz Divina e se curou 40 de vida!");
        } else {
            System.out.println(nome + " esta sem fe para se curar.");
        }
    }
}
