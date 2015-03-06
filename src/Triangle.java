import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Belton Zhong
 *
 * This class is a solution for the Triangle problem. It uses a Tree-like
 * structure of Nodes that holds all of the numbers in the triangle. The
 * algorithm then "collapses" the triangle from top to bottom by looking
 * at each Node in the structure and adding the largest of its two parents
 * to it. By iterating over the entire structure and doing this at every
 * level, the maximum total ends up as the largest value on the last row
 * of the structure.
 */
public class Triangle {
  private static final int ROWS = 100;
  //NUM_NUMBERS is the total number of numbers in the structure
  private static final int NUM_NUMBERS = (ROWS + 1) * ROWS / 2;
  private static final String fileName = "triangle.txt";

  /**
   * Nodes make up the infrastructure of the tree. Each Node tracks its own
   * value as well as the value of its largest parent Node. There is no need
   * to store references to the Node's children because the structure allows
   * easy access to to a Node's children through a level-order traversal.
   */
  static class Node {
    private int value;
    private int largestParent;

    public Node(int value) {
      this.value = value;
      largestParent = 0;
    }

    public int getValue() {
      return value;
    }

    public int getLargestParent() {
      return largestParent;
    }

    /**
     * This is the main logic of the algorithm. When a Node is
     * collapsed, its left parent is first added to its value
     * and largestParent is given the value of the left parent.
     * It is important to note that this step can be undone if the
     * Node's right parent is greater than the left parent. The original
     * value of this Node is equal to value - largestParent. After the
     * left parent is added to this Node, if the right parent's value is
     * larger than this Node's largestParent value, then this Node's original
     * value is restored and then the right parent's value is added.
     * This ensures that each Node becomes the maximum possible sum of its
     * ancestors.
     */
    public void add(Node parent) {
      if(parent.getValue() > this.getLargestParent()) {
        value -= largestParent;
        value += parent.getValue();
        largestParent = parent.getValue();
      }
    }
  }

  public static void main(String[] args) {
    Scanner fileInput = null;
    try {
      fileInput = new Scanner(new File(fileName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(0);
    }
    Node[] triangle = new Node[NUM_NUMBERS];
    //Reading in the numbers from the file in order creates a level-order
    //traversal of the tree-like structure
    for(int i = 0; fileInput.hasNext(); i++)
      triangle[i] = new Node(Integer.parseInt(fileInput.next()));
    System.out.println(findMax(triangle, ROWS));
  }

  private static int findMax(Node[] triangle, int rows) {
    for(int i = 0; i < rows - 1; i++)
      collapseRow(triangle, i); //Collapse each row from the top to bottom
    int lastRowIndex = 0; //This is used to find the beginning of the last row
    for(int i = 0; i < ROWS; i++)
      lastRowIndex += i;
    int maxValue = Integer.MIN_VALUE;
    for(int i = 0; i < ROWS; i++) //Find the largest value in the last row
      if(triangle[lastRowIndex + i].getValue() > maxValue)
        maxValue = triangle[lastRowIndex + i].getValue();
    return maxValue;
  }

  private static void collapseRow(Node[] triangle, int row) {
    //The key here is that because triangle holds the level-order traversal
    //of the structure, the left child of a node at index is always stored at
    //index + row + 1, where row is the row of the Node at index. Similarly,
    //the right child is stored at index + row + 2.
    int index = 0;
    for(int i = 0; i <= row; i++)
      index += i;
    for(int i = 0; i <= row; i++) {
      triangle[index + row + 1].add(triangle[index]);
      triangle[index + row + 2].add(triangle[index]);
      index++;
    }
  }
}