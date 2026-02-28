package module3;

import java.util.Arrays;

/**
 * Implementation of searching algorithms: Linear Search and Binary Search.
 * Includes execution time measurement for performance analysis.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class SearchingAlgorithms {

    // ==================== Linear Search ====================
    /**
     * Performs Linear Search on an array.
     * Time Complexity: O(n)
     * 
     * @param arr the array to search
     * @param target the value to find
     * @return index of the target, or -1 if not found
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // ==================== Binary Search ====================
    /**
     * Performs Binary Search on a sorted array.
     * Time Complexity: O(log n)
     * Note: Array must be sorted before calling this method.
     * 
     * @param arr the sorted array to search
     * @param target the value to find
     * @return index of the target, or -1 if not found
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    // ==================== Timing Utilities ====================

    /**
     * Measures execution time of Linear Search.
     * @param arr the array to search
     * @param target the value to find
     * @return execution time in nanoseconds
     */
    public static long timeLinearSearch(int[] arr, int target) {
        long start = System.nanoTime();
        linearSearch(arr, target);
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Measures execution time of Binary Search (array must be sorted).
     * @param arr the sorted array to search
     * @param target the value to find
     * @return execution time in nanoseconds
     */
    public static long timeBinarySearch(int[] arr, int target) {
        long start = System.nanoTime();
        binarySearch(arr, target);
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Measures execution time of Binary Search including sorting time.
     * @param arr the unsorted array to search
     * @param target the value to find
     * @return execution time in nanoseconds (sort + search)
     */
    public static long timeBinarySearchWithSort(int[] arr, int target) {
        long start = System.nanoTime();
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        binarySearch(sorted, target);
        long end = System.nanoTime();
        return end - start;
    }
}
