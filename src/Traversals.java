import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {
    if (node == null) return 0;
    if (node.left == null && node.right == null) return node.value;
    return sumLeafNodes(node.left)+sumLeafNodes(node.right);
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    if (node == null) return 0;
    if (node.left != null || node.right != null) return 1 + countInternalNodes(node.left) + countInternalNodes(node.right);
    return 0;
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {
    if (node == null) return "";
    return buildPostOrderString(node.left) + buildPostOrderString(node.right) + node.value.toString();
  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {
    ArrayList<T> list = new ArrayList<>();
    Queue<TreeNode<T>> queue = new LinkedList<>();
    queue.add(node);

    while (!queue.isEmpty()) {
      TreeNode<T> current = queue.poll();
      if (current == null) continue;
      list.add(current.value);
      queue.add(current.left);
      queue.add(current.right);
    }

    return list;
  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
    Stack<TreeNode<Integer>> stack = new Stack<>();
    Set<Integer> values = new HashSet<>();
    int distinctValues = 0;

    stack.push(node);

    while (!stack.isEmpty()) {
      TreeNode<Integer> current = stack.pop();
      if (current == null) continue;
      if (!values.contains(current.value)) {
        values.add(current.value);
        distinctValues++;
      }
      stack.push(current.left);
      stack.push(current.right);
    }

    return distinctValues;
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    // If the first node is null, return false
    if (node == null) return false;
    Stack<TreeNode<Integer>> stack = new Stack<>();
    stack.push(node);

    while (!stack.isEmpty()) {
      TreeNode<Integer> current = stack.pop();
      // If either child of this node has a greater value, add it to the stack of nodes to check
      if (current.left != null && current.left.value > current.value) stack.push(current.left);
      if (current.right != null && current.right.value > current.value) stack.push(current.right);
      // If this node has no children, it is a leaf and was successfully reached
      if (current.left == null && current.right == null) return true;
    }

    // If a leaf was never reached, return false
    return false;
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    // If these are literally the same nodes, or are both null, return true
    if (nodeA == nodeB)
      return true;
    if (nodeA == null || nodeB == null) 
      return false;

    return haveSameShape(nodeA.left,nodeB.left) && haveSameShape(nodeA.right,nodeB.right);
  }


  // For a Tree of size one, I need to return [[rootValue]]
  // For a full Tree 2 layers deep, I need to return
  // [[rootValue,left],[rootValue,right]]
  // For a full Tree 3 layers deep, I need to return
  // [[rootValue,left,left2],[rootValue,left2,leftright],[rootValue,right,rightleft],[rootValue,right,right2]]

  // What if instead of working downwards to create the list, I worked upwards?
  // Every leaf will return to the root, so find every leaf, and for every leaf,
  // create a list.

  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    List<List<T>> listOfLists = new ArrayList<>();
    if (node == null) return listOfLists;
    
    // Call the helper method
    List<Stack<T>> leafStacks = findLeafPaths(node);

    // Convert to stacks to lists
    for (Stack<T> leafStack : leafStacks) {
      List<T> newList = new ArrayList<>();
      while (!leafStack.isEmpty()) {
        newList.add(leafStack.pop());
      }
      listOfLists.add(newList);
    }

    return listOfLists;
  }

  // Returns a List of Stacks representing a path from the given node to every leaf.
  // Uses a Stack specifically for layering purposes. 
  // Should probably use a List instead.
  private static <T> List<Stack<T>> findLeafPaths(TreeNode<T> node) {
    if (node == null) return new ArrayList<>();
    // If this is a leaf, create a new path to return to the top with
    if (node.left == null && node.right == null) {
      Stack<T> leafPath = new Stack<>();
      leafPath.push(node.value);
      // Create a list to store this INDIVIDUAL path.
      List<Stack<T>> newList = new ArrayList<>();
      newList.add(leafPath);
      return newList;
    } 
    else {
      // Get the list of paths to the left and right
      List<Stack<T>> leafPathsLeft = findLeafPaths(node.left);
      List<Stack<T>> leafPathsRight = findLeafPaths(node.right);
      // If there is a direct leaf, there is only one Stack here.
      for (Stack<T> path : leafPathsLeft) {
        // Add self to the top of all of the left paths
        path.push(node.value);
      }
      for (Stack<T> path : leafPathsRight) {
        // Add self to the top of all of the right paths
        path.push(node.value);
      }

      // Combine all the paths into one list
      List<Stack<T>> leafPaths = new ArrayList<>();
      leafPaths.addAll(leafPathsLeft);
      leafPaths.addAll(leafPathsRight);
      return leafPaths;
    }
  }
}
