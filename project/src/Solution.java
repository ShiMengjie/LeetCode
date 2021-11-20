import java.util.Stack;

/**
 * @author shimengjie
 * @date 2021/9/23 12:59
 **/
public class Solution {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // 是左括号就 push 进栈中
            if (isLeft(ch)) {
                stack.push(ch);
            } else {
                // 如果栈中没有前置字符，说明只有右括号没有左括号
                if (stack.isEmpty()) {
                    return false;
                }
                // 判断是否是闭合的括号
                if (!isClosed(stack.pop(), ch)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean isLeft(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    private boolean isClosed(char left, char right) {
        return (left == '(' && right == ')')
                || (left == '[' && right == ']')
                || (left == '{' && right == '}');
    }
}


/**
 * 表示二维数组中每个位置上的元素
 */
class QueenNode {

    int row, col, k1, k2;

    public QueenNode(int row, int col) {
        this.row = row;
        this.col = col;
        this.k1 = row + col;
        this.k2 = row - col;
    }

    public int getK1() {
        return k1;
    }

    public int getK2() {
        return k2;
    }

    public String toString(int n) {
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            if (i == col) {
                chs[i] = 'Q';
            } else {
                chs[i] = '.';
            }
        }
        return new String(chs);
    }
}

class TreeNode {
    int val;
    int num;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val, int num) {
        this.val = val;
        this.num = num;
    }

    TreeNode(int val) {
        this.val = val;
    }

    public void setChild(TreeNode node) {
        if (left == null) {
            left = node;
        } else {
            right = node;
        }
    }

//    @Override
//    public String toString() {
//        return preorderTraversal(this).toString();
//    }
//
//
//    public List<Integer> preorderTraversal(TreeNode root) {
//        List<Integer> list = new LinkedList<>();
//        pre(list, root);
//        return list;
//    }
//
//    private void pre(List<Integer> list, TreeNode root) {
//        if (root != null) {
//            // 遍历根节点
//            list.add(root.val);
//            // 遍历左子树
//            pre(list, root.left);
//            // 遍历右子树
//            pre(list, root.right);
//        }
//    }
}

class ListNode {
    public int val;
    public ListNode prev;
    public ListNode next;
    public ListNode child;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode prev, ListNode next) {
        this.val = val;
        this.prev = prev;
        this.next = next;
    }
}
