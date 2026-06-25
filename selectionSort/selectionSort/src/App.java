import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<Integer> array = new ArrayList<>(Arrays.asList(40, 52, 33, 15, 18, 420, -330, -12, 0, 27));
        System.out.println(array);
        
        QuickSort.inPlace(array, 0, array.size() - 1);

        System.out.println(array);
    }
}
