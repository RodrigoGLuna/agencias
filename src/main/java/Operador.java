import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class Operador {

    public  static <T extends Comparable<T>> void ordenarArray (T a[]) throws ArrayIndexOutOfBoundsException{
        if (a.length==0){
            throw new ArrayIndexOutOfBoundsException("El array esta vacio");
        }
        Arrays.sort(a, Comparator.nullsLast(T::compareTo));

    }

}
