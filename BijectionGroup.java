public class BijectionGroup {
    // your code goes here
    public static void main(String... args) {
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
// you have to figure out the data types in the lines below
        __________________________ g = bijectionGroup(a_few);
        __________________________ f1 = bijectionsOf(a_few).stream().findFirst().get();
        __________________________ f2 = g.inverseOf(f1);
        __________________________ id = g.identity();
    }