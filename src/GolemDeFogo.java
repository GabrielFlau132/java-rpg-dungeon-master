
public class GolemDeFogo extends Personagem {

    public GolemDeFogo() {
        super("Golem de Fogo", 80, 40, 16, 6);
    }

    public void usarMagia() {
        if (mana >= 15) {
            mana = mana - 15;
            int dano = danoBase + 12;
            System.out.println(nome + " usou a habilidade INCINERAR e causou " + dano + " de dano");
            if (inimigo != null) {
                inimigo.defender(dano);
            }
        } else {
            System.out.println(nome + " esta sem energia.");
        }
    }
}
