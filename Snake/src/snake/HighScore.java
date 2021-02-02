package snake;

import java.util.Comparator;
import java.util.Objects;

public class HighScore implements Comparator<HighScore> {
    public final String name;
    public final int score;
    
    public HighScore(String name, int score){
        this.name = name;
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + this.score;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HighScore other = (HighScore) obj;
        if (this.score != other.score) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }   

    @Override
    public String toString() {
        return name + "-" + score;
    }

    @Override
    public int compare(HighScore o1, HighScore o2) {
        if(o1.score  > o2.score){
            return 1;
        }else{
            return -1;
        }
    }
}
