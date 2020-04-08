package testp;

import java.util.Objects;

public class MyVertex {
    int i;

    public MyVertex(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyVertex myVertex = (MyVertex) o;
        return i == myVertex.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }

    @Override
    public String toString() {
        return "MyVertex{" +
                "i=" + i +
                '}';
    }
}
