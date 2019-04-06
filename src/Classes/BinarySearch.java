package Classes;

import java.util.Stack;

public class BinarySearch {
    public BinarySearch() {
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Stack s = new Stack();
        s.push(0);
        s.push(nums.length - 1);
        byte mark = 12;

        while(!s.empty()) {
            int h = (Integer)s.pop();
            int l = (Integer)s.pop();
            int m = (h + l) / 2;
            if (nums[m] == mark) {
                System.out.println(m);
            } else if (nums[m + 1] == mark) {
                System.out.println(m + 1);
            } else if (nums[m] < mark) {
                s.push(m);
                s.push(h);
            } else if (nums[m] > mark) {
                s.push(l);
                s.push(m);
            }
        }

    }
}
