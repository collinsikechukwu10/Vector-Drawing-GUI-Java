package model.shapes;

import java.util.Stack;

public class HistoryStack<T> extends Stack<T> {
    private int undoRedoIndex = -1;

    @Override
    public T push(T item) {
        // check if there are redo possibilities
        // if so, delete those entries
        while (canRedo()) {
            this.pop();
        }
        // add object and then change the current index for redo and undo
        T res = super.push(item);
        undoRedoIndex++;
        System.out.println("Pushing item.... currently->" + this.elementData);
        System.out.println("PUSH -> Size: " + this.size() + "index: " + undoRedoIndex);

        return res;
    }


    public T getState() {
        System.out.println("Getting state...., currently ->" + this.elements());

        return this.get(undoRedoIndex);
    }

    public boolean hasState(){
        // technically you can only have a state, if you can also undo the state,
        return canUndo();
    }

    public void undo() {
        if (canUndo()) {
            undoRedoIndex--;
        }
        System.out.println("UNDO-> Size: " + this.size() + "index" + undoRedoIndex);

    }

    public void redo() {
        if (canRedo()) {
            undoRedoIndex++;
        }
        System.out.println("REDO-> Size: " + this.size() + "index: " + undoRedoIndex);

    }

    public boolean canUndo() {
        return undoRedoIndex > 0;
    }

    public boolean canRedo() {
        return undoRedoIndex < this.size() - 1;
    }
}
