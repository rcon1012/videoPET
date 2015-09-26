/*
 * Abstract class for State
 */

public abstract class State {

    protected StateManager state_manager;

    public State (StateManager state_manager){
        this.state_manager = state_manager;
    }

    public abstract void handleInput();
    public abstract void display();
}
