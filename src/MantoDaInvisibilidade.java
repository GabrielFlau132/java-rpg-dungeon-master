
public class MantoDaInvisibilidade extends Item {

    public MantoDaInvisibilidade() {
        super("Manto da Invisibilidade");
    }

    @Override
    public void usar(Personagem personagem) {
        personagem.curarVida(40);
        System.out.println(personagem.getNome() + " ficou invisivel com o manto e recuperou 40 de vida !");
    }
}
