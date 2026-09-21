
public class GolemDeVento extends Personagem {

    public GolemDeVento() {
        super("Golem de Vento", 85, 45, 15, 6);
    }

    public void usarMagia() {
        if (mana >= 15) {
            mana = mana - 15;
            int dano = danoBase + 11;
            System.out.println(nome + " criou um tornado causando " + dano + " de dano");
            if (inimigo != null) {
                inimigo.defender(dano);
            }
        } else {
            System.out.println(nome + " esta sem energia.");
        }
    }
}
