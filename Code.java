public class Code {

    public int[] a;
    
    public Code (int[] a) {
	this.a = a;    
    }

    public String toString() {
	return "" + a[0] + " " + a[1] + " " + a[2] + " " + a[3];
    }
    
    public Key check(Code guess) {

	int[] x = new int[MaxVal.MAX_VAL+1]; // count values in guess 
	int[] y = new int[MaxVal.MAX_VAL+1]; // count values in this
	int[] z = new int[MaxVal.MAX_VAL+1]; // counts hits
	Key match = new Key (0,0);
	for (int i=0; i<4; i++) {
	    x[guess.a[i]]++;
	    y[this.a[i]]++;
	    if (guess.a[i] == a[i]) z[a[i]]++;
	}
	for (int i=0; i<=MaxVal.MAX_VAL; i++) {
	    match.taps += Math.min(x[i], y[i]);
	    match.hits += z[i];	
	}
	match.taps -= match.hits; 
	return match;
    }

}
    


