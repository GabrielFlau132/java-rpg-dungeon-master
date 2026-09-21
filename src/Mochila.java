import java.util.ArrayList;

public class Mochila {

    private ArrayList<Item> itens;

    public Mochila() {
        itens = new ArrayList<Item>();
    }

    public void adicionar(Item item) {
        itens.add(item);
    }

    public void mostrarItens() {
        if (itens.size() == 0) {
            System.out.println("A mochila esta vazia.");
        } else {
            for (int i = 0; i < itens.size(); i++) {
                System.out.println((i + 1) + " - " + itens.get(i).getNome());
            }
        }
    }

    public void usarItem(int numero, Personagem dono) {
        int indice = numero - 1;
        if (indice >= 0 && indice < itens.size()) {
            Item item = itens.get(indice);
            item.usar(dono);
            itens.remove(indice);
        } else {
            System.out.println("Item invalido.");
        }
    }

    public int quantidade() {
        return itens.size();
    }

    public ArrayList<Item> getItens() {
        return itens;
    }
}
