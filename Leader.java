public class Leader {

    public int position;
    public int score;
    public String name;
    public Leader (int p, int s, String n) {
	position = p;
	score = s;
	name = n;
    }
    public String toString() {
	return "" + position + ": " + name + ": " + score;
    }
}
