
public class Mago extends Personagem {

    public Mago(String nome) {
        super(nome, 80, 100, 12, 4);
    }

    public void usarMagia() {
        if (mana >= 20) {
            mana = mana - 20;
            int dano = danoBase + 15;
            System.out.println(nome + " lancou uma IMENSA e IRRESPONSAVEL Bola de Fogo!");
            if (inimigo != null) {
                inimigo.defender(dano);
            }
        } else {
            System.out.println(nome + " esta sem mana para usar magia.");
        }
    }
}
