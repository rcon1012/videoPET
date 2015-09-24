import java.util.Stack;

/**
 * State Manager implemented using a stack
 */
public class StateManager {

    private Stack<State> ss;

    public StateManager(){
        ss = new Stack<State>();
    }

    public void push(State s){
        ss.push(s);
    }

    public void pop(){
        ss.pop();
    }

    public State peek(){
        return ss.peek();
    }

    public void set(State s){
        ss.pop();
        ss.push(s);
    }
}
