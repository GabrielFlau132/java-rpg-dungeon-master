
public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        super(nome, 140, 40, 16, 10);
    }

    public void usarMagia() {
        if (mana >= 10) {
            mana = mana - 10;
            int dano = danoBase + 10;
            System.out.println(nome + " deu um Golpe Poderoso com a espada!");
            if (inimigo != null) {
                inimigo.defender(dano);
            }
        } else {
            System.out.println(nome + " esta cansado demais.");
        }
    }
}
