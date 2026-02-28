package module2;

/**
 * Implementation of sorting algorithms: Bubble Sort, Merge Sort, and Quick Sort.
 * Each algorithm includes execution time measurement.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class SortingAlgorithms {

    // ==================== Bubble Sort ====================
    /**
     * Sorts an array using Bubble Sort algorithm.
     * Time Complexity: O(n^2) average and worst case.
     * 
     * @param arr the array to sort
     * @return a copy of the sorted array
     */
    public static int[] bubbleSort(int[] arr) {
        int[] copy = arr.clone();
        int n = copy.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (copy[j] > copy[j + 1]) {
                    // Swap elements
                    int temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                    swapped = true;
                }
            }
            // Optimization: if no swaps occurred, array is already sorted
            if (!swapped) break;
        }
        return copy;
    }

    // ==================== Merge Sort ====================
    /**
     * Sorts an array using Merge Sort algorithm.
     * Time Complexity: O(n log n) in all cases.
     * 
     * @param arr the array to sort
     * @return a copy of the sorted array
     */
    public static int[] mergeSort(int[] arr) {
        int[] copy = arr.clone();
        if (copy.length <= 1) return copy;
        mergeSortHelper(copy, 0, copy.length - 1);
        return copy;
    }

    private static void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        // Copy data to temp arrays
        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        // Merge the temp arrays back
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        // Copy remaining elements
        while (i < n1) arr[k++] = leftArr[i++];
        while (j < n2) arr[k++] = rightArr[j++];
    }

    // ==================== Quick Sort ====================
    /**
     * Sorts an array using Quick Sort algorithm.
     * Time Complexity: O(n log n) average, O(n^2) worst case.
     * 
     * @param arr the array to sort
     * @return a copy of the sorted array
     */
    public static int[] quickSort(int[] arr) {
        int[] copy = arr.clone();
        if (copy.length <= 1) return copy;
        quickSortHelper(copy, 0, copy.length - 1);
        return copy;
    }

    private static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSortHelper(arr, low, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // Use median-of-three pivot selection for better performance
        int mid = low + (high - low) / 2;
        if (arr[mid] < arr[low]) swap(arr, low, mid);
        if (arr[high] < arr[low]) swap(arr, low, high);
        if (arr[mid] < arr[high]) swap(arr, mid, high);
        int pivot = arr[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // ==================== Timing Utilities ====================
    /**
     * Measures execution time of Bubble Sort.
     * @param arr the array to sort
     * @return execution time in nanoseconds
     */
    public static long timeBubbleSort(int[] arr) {
        long start = System.nanoTime();
        bubbleSort(arr);
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Measures execution time of Merge Sort.
     * @param arr the array to sort
     * @return execution time in nanoseconds
     */
    public static long timeMergeSort(int[] arr) {
        long start = System.nanoTime();
        mergeSort(arr);
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Measures execution time of Quick Sort.
     * @param arr the array to sort
     * @return execution time in nanoseconds
     */
    public static long timeQuickSort(int[] arr) {
        long start = System.nanoTime();
        quickSort(arr);
        long end = System.nanoTime();
        return end - start;
    }
}
