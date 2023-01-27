package quandev.com.tree.btree;

import lombok.Data;
import org.junit.jupiter.api.Test;

public class BtreeTest {

    final int MAX = 3;
    final int MIN = 2;

    @Data
    private class Btree {
        public int[] data = new int[MAX + 1];
        public int count;
        public Btree[] link = new Btree[MAX + 1];
    }

    Btree root = new Btree();

    public Btree createNode(int data , Btree child) {
        Btree newNode = new Btree();
        newNode.data[1] = data;
        newNode.count = 1;
        newNode.link[0] = root;
        newNode.link[1] = child;
        return newNode;
    }

    void addNode(int data, int pos, Btree node , Btree child) {
        int j = node.count;
        while (j > pos) {
            node.data[j + 1] = node.data[j];
            node.link[j + 1] = node.link[j];
            j--;
        }
        node.data[j + 1] = data;
        node.link[j + 1] = child;
        node.count++;
    }

  //  void setData(int data , int pdate , Btree node , Btree child)

    @Test
    public void main() {
       // addNode(6);
    }


}
