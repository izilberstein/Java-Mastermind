public class Key {

    public int taps;
    public int hits;
    public Key (int h, int t) {
	taps = t;
	hits = h;
    }
    public String toString() {
	return "" + taps + " taps and " + hits + " hits";
    }
}
 
