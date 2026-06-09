import java.util.ArrayList;
import java.util.Collections;

public abstract class SelectionSort {

    public static void inPlace(ArrayList<Integer> array) {

        Integer menorValor;
        int indiceMenorValor;
        int i,j;

        for (i = 0; i < array.size(); i++)
        {
            menorValor = array.get(i);
            indiceMenorValor = i;
            for (j = i + 1; j < array.size(); j++)
            {
                if (array.get(j) < menorValor)
                {
                    indiceMenorValor = j;
                    menorValor = array.get(j);
                }
            }
            Collections.swap(array, i, indiceMenorValor);
        }

        return;
      
    }






}
