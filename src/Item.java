
public abstract class Item {

    protected String nome;

    public Item(String nome) {
        this.nome = nome;
    }

    public abstract void usar(Personagem personagem);

    public String getNome() {
        return nome;
    }
}
