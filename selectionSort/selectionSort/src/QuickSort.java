import java.util.ArrayList;
import java.util.Collections;

public class QuickSort {

public static void inPlace(ArrayList<Integer> array, int left, int right) {

        int i = left;
        int j = right;

        int pivot = array.get((left + right) / 2);

        while (i <= j)
        {
            while (array.get(i) < pivot){
                i += 1;
            }

            while (array.get(j) > pivot) {
                j -= 1;
            }

            if (i <= j)
            {
                Collections.swap(array, i, j);

                i += 1;
                j -= 1;
            }

        }

        if (left < j) 
            QuickSort.inPlace(array, left, j);

        if (i < right) 
            QuickSort.inPlace(array, i, right);
        }      
    }
