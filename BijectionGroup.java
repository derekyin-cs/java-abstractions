import java.util.*;
import java.util.stream.*;
import java.util.function.UnaryOperator;
public class BijectionGroup {
    // your code goes here

    public static <T> Set<UnaryOperator<T>> bijectionsOf(Set<T> domain){
        List<T> setAsList = new ArrayList<T>();
        setAsList.addAll(domain);
        List<List<T>> perms = new ArrayList<>();
        Set<UnaryOperator<T>> setOfFunctions = new HashSet<>();
        getAllPermutations(setAsList.size(), setAsList, perms);
//        for (List<T> lst : perms){
//            System.out.println(lst.toString());
//        }
        for (List<T> permutation : perms){
            setOfFunctions.add(f -> permutation.get(setAsList.indexOf(f)));
        }
        return setOfFunctions;


        // get list of all permutations of domain set
        // For each permutation, add lambda function (f -> permutation.get(list.indexOf(f))) to a list of functions
        // convert to set and return

    };

    private static <T> void addPermutation(List<T> input, List<List<T>> perms) {
        System.out.print('\n');
        List<T> perm = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
            perm.add(input.get(i));
        }
        perms.add(perm);
    }

    private static <T> void swap(List<T> input, int a, int b) {
        T tmp = input.get(a);
        input.set(a, input.get(b));
        input.set(b, tmp);
    }

    public static <T> void getAllPermutations(int n, List<T> elements, List<List<T>> perms) {
        if(n == 1) {
            addPermutation(elements, perms);
        } else {
            for(int i = 0; i < n-1; i++) {
                getAllPermutations(n - 1, elements, perms);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            getAllPermutations(n - 1, elements, perms);
        }
    }

    public static <T> Group<UnaryOperator<T>> bijectionGroup(Set<T> input){
        Group<UnaryOperator<T>> grp = new Group<UnaryOperator<T>>() {
            Set<UnaryOperator<T>> bijections = bijectionsOf(input);
            public UnaryOperator<T> binaryOperation(UnaryOperator<T> one, UnaryOperator<T> other){
                    return (UnaryOperator<T>) (other.andThen(one));
            }

            public UnaryOperator<T> identity(){
                for (UnaryOperator<T> g : bijections ){
                    if (input.stream().allMatch( x -> g.apply(x).equals(x)) )return g;
                }

                return null;
            }

            public UnaryOperator<T> inverseOf(UnaryOperator<T> f){
                for (UnaryOperator<T> g : bijections ){
                    if (input.stream().allMatch( x -> g.apply(f.apply(x)).equals(x)))return g;
                }

                return f;
            }
        };


        // The inverse of a bijection is a bijection.



        return grp;
    }
    public static void main(String... args) {
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
// you have to figure out the data type in the line below
        Set<UnaryOperator<Integer>> bijections = bijectionsOf(a_few);
        bijections.forEach(aBijection -> {
            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
            System.out.println();
        });
// you have to figure out the data types in the lines below
        Group<UnaryOperator<Integer>> g = bijectionGroup(a_few);
        UnaryOperator<Integer> f1 = bijectionsOf(a_few).stream().findFirst().get();
        UnaryOperator<Integer> f2 = g.inverseOf(f1);
        System.out.printf("%d --> %d; ", (Integer) 3, f2.apply(3));
        System.out.println();
        UnaryOperator<Integer> id = g.identity();
        System.out.printf("%d --> %d; ", (Integer) 3, id.apply(3));

    }

}

