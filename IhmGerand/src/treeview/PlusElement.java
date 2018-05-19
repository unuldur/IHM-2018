package treeview;

import model.IElement;

import java.util.List;
import java.util.Objects;

public class PlusElement implements IElement {

    private String name;

    public PlusElement(String name) {
        this.name = name;
    }

    public PlusElement() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlusElement that = (PlusElement) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
