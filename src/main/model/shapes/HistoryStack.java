package main.model.shapes;

import java.util.Arrays;
import java.util.Stack;

/**
 * History Stack.
 * Houses the Undo and Redo Implementations.
 *
 * @author 210032207
 */
public class HistoryStack<T> extends Stack<T> {
    private int undoRedoIndex = -1;

    /**
     * Pushes item to the stack and updates the undoRedoIndex.
     *
     * @param item item
     * @return item
     */
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
        System.out.println("Pushing item.... currently->" + Arrays.toString(this.elementData));
        System.out.println("PUSH -> Size: " + this.size() + "index: " + undoRedoIndex);
        return res;
    }

    /**
     * Gets the current state.
     *
     * @return state containing item
     */
    public T getState() {
        System.out.println("Getting state...., currently ->" + this);

        return this.get(undoRedoIndex);
    }

    /**
     * Checks if the current index has a state.
     *
     * @return true if the current index has a state
     */
    public boolean hasState() {
        // technically you can only have a state, if you can also undo the state,
        return undoRedoIndex > -1;
    }

    /**
     * Performs undo operation.
     */
    public void undo() {
        if (canUndo()) {
            undoRedoIndex--;
        }
        System.out.println("UNDO-> Size: " + this.size() + "index" + undoRedoIndex);

    }

    /**
     * Performs redo operation.
     */
    public void redo() {
        if (canRedo()) {
            undoRedoIndex++;
        }
        System.out.println("REDO-> Size: " + this.size() + "index: " + undoRedoIndex);

    }

    /**
     * Checks if you can perform undo operations.
     *
     * @return true if undo operation can be performed
     */
    public boolean canUndo() {
        return undoRedoIndex > 0;
    }

    /**
     * Checks if you can perform redo operation.
     *
     * @return true if redo operation can be performed
     */
    public boolean canRedo() {
        return undoRedoIndex < this.size() - 1;
    }
}
