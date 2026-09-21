
public class GolemDeTerra extends Personagem {

    public GolemDeTerra() {
        super("Golem de Terra", 90, 30, 12, 12);
    }

    public void usarMagia() {
        if (mana >= 10) {
            mana = mana - 10;
            int dano = danoBase + 8;
            System.out.println(nome + " Atirou um pilar de pedra causando " + dano + " de dano");
            if (inimigo != null) {
                inimigo.defender(dano);
            }
        } else {
            System.out.println(nome + " esta sem energia.");
        }
    }
}
