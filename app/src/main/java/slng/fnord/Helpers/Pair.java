package slng.fnord.Helpers;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair() {

    }

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public Pair<F, S> clone() {
        return new Pair<>(first, second);
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }
}
