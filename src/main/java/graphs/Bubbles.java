package graphs;


import java.util.*;

/**
 * Sophie and Marc want to reduce the bubbles of contacts in the belgian population
 * to contain an evil virus (weird idea but nevertheless inspired by a true belgian story in 2020, don't ask ...).
 *
 * Help them!
 *
 * The Belgian government has imposed on the population to limit the number of contacts, via "bubbles".
 *
 * The principle is quite simple. If you have a (close) contact with someone,
 * You are then in his bubble, and he is in yours.
 *
 * Let's say the following contacts have taken place: [
 * [Alice, Bob], [Alice, Carol], [Carol, Alice], [Eve, Alice], [Alice, Frank],
 * [Bob, Carole], [Bob, Eve], [Bob, Frank], [Bob, Carole], [Eve, Frank]
 * ].
 *
 * Note that the contacts are two-by-two and can occur several times. The order within
 * of a contact does not matter.
 *
 * The resulting bubbles are :
 *
 * - Alice's bubble = [Bob, Carol, Eve, Frank]
 * - Bob's bubble = [Alice, Carol, Eve, Frank]
 * - Carole's bubble = [Alice, Bob]
 * - Frank's Bubble = [Alice, Bob, Eve]
 *
 * Note that the relationship is symmetric
 * (if Alice is in Bob's bubble, then Bob is in Alice's bubble)
 * but not transitive (if Bob is in Alice's bubble, then Bob is in Alice's bubble)
 * and Carol is in Bob's bubble, Carol is not necessarily in Alice's.
 *
 * Since at most n people can be in someone's bubble without him being outlaw
 * given a list of contacts, select pairs of people that you will forbid to meet, so that eventually
 * each person has a bubble of NO MORE than n people (not counting themselves).
 * You need to ban AS FEW AS POSSIBLE (pairs of) people to meet.
 *
 * For example, if n=3, in the example above, you could forbid Alice and Carol to see each other, but also
 * Bob and Carol. This removes 2 links (even though Alice and Carol appear twice in the contacts!).
 * But there is a better solution: prevent Alice and Bob from seeing each other, which only removes one link.
 * Finding an algorithm that solves this problem is complex, that's why we give you a rather vague idea of an algorithm:
 *
 * - As long as there are links between two bubbles each "too big", remove one of these links;
 * - Then, as long as there are bubbles that are too big, remove any link connected to one of these bubbles
 * (removing is equivalent to "adding the link to the list of forbidden relationships")
 *
 * Implementing this algorithm as it is a bad idea. Think of an optimal way to implement it.
 *
 * You CANNOT modify the `contacts` list directly (nor the lists inside)
 * If you try, you will receive an UnsupportedOperationException.
 *
 * @returns a list of people you are going to forbid to see each other. There MUST NOT be any duplicates.
 * the order doesn't matter, both within the ForbiddenRelation and in the list.
 */
public class Bubbles {

    private static void update(HashMap<String, Integer> map, String s){
        map.putIfAbsent(s, 0);
        map.replace(s, map.get(s) + 1);
    }

    private static class Node implements Comparable{
        Contact c;
        int weight_a;
        int weight_b;

        Node(Contact c, int i, int j){
            this.c = c;
            weight_a = i;
            weight_b = j;
        }

        @Override
        public int compareTo(Object o) {
            Node n = (Node) o;
            return n.weight_a + n.weight_b - weight_a - weight_b;
        }
    }

    public static List<ForbiddenRelation> cleanBubbles(List<Contact> contacts, int n) {

        Set<Contact> uniq = new HashSet<>(contacts);
        List<ForbiddenRelation> ret = new LinkedList<>();
        HashMap<String, Integer> counts = new HashMap<>();
        for (Contact c : uniq){
            update(counts, c.a);
            update(counts, c.b);
        }
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for (Contact c : uniq){
            pq.add(new Node(c, counts.get(c.a), counts.get(c.b)));
        }
        while (true){
            Node node = pq.remove();
            int count_a = counts.get(node.c.a);
            int count_b = counts.get(node.c.b);
            if (node.weight_b != count_b || node.weight_a != count_a){
                pq.add(new Node(node.c, count_a, count_b));
                continue;
            }
            if (node.weight_a <= n && node.weight_b <= n)
                break;
            ret.add(new ForbiddenRelation(node.c.a, node.c.b));
            counts.replace(node.c.a, count_a - 1);
            counts.replace(node.c.b, count_b - 1);
        }
        while (!pq.isEmpty()){
            Node node = pq.remove();
            int count_a = counts.get(node.c.a);
            int count_b = counts.get(node.c.b);
            if (node.weight_b != count_b || node.weight_a != count_a){
                pq.add(new Node(node.c, count_a, count_b));
                continue;
            }
            if (node.weight_b <= n && node.weight_a <= n)
                continue;
            ret.add(new ForbiddenRelation(node.c.a, node.c.b));
            counts.replace(node.c.a, count_a - 1);
            counts.replace(node.c.b, count_b - 1);
        }

        return ret;

    }

    public static List<ForbiddenRelation> cleanBubblesSol(List<Contact> contacts, int n) {

        HashMap<String, HashSet<String>> relations = new HashMap<>();

        for (Contact c : contacts){
            relations.putIfAbsent(c.a, new HashSet<>());
            relations.get(c.a).add(c.b);
            relations.putIfAbsent(c.b, new HashSet<>());
            relations.get(c.b).add(c.a);
        }

        List<ForbiddenRelation> ret = new LinkedList<>();

        for (Contact c : contacts){
            if (!relations.get(c.a).contains(c.b)) continue;
            if (relations.get(c.a).size() > n && relations.get(c.b).size() > n){
                ret.add(new ForbiddenRelation(c.a, c.b));
                relations.get(c.a).remove(c.b);
                relations.get(c.b).remove(c.a);
            }
        }

        for (Contact c : contacts){
            if (!relations.get(c.a).contains(c.b)) continue;
            if (relations.get(c.a).size() > n || relations.get(c.b).size() > n){
                ret.add(new ForbiddenRelation(c.a, c.b));
                relations.get(c.a).remove(c.b);
                relations.get(c.b).remove(c.a);
            }
        }

        return ret;
    }

}



class Contact {
    public final String a, b;

    public Contact(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(a, contact.a) && Objects.equals(b, contact.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}

class ForbiddenRelation implements Comparable<ForbiddenRelation> {
    public final String a, b;

    public ForbiddenRelation(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ForbiddenRelation)
            return a.equals(((ForbiddenRelation) obj).a) && b.equals(((ForbiddenRelation) obj).b);
        return false;
    }

    @Override
    public int compareTo(ForbiddenRelation o) {
        if(a.equals(o.a))
            return b.compareTo(o.b);
        return a.compareTo(o.a);
    }
}
