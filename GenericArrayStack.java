public class GenericArrayStack<E> implements Stack<E> {
   
   // ADD YOUR INSTANCE VARIABLES HERE
  private E[] elems;
  private int top;
  
  @SuppressWarnings( "unchecked" )
  
   // Constructor
    public GenericArrayStack( int capacity ) { 
      elems= (E[]) new Object[capacity];
      top=0;
    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
      return (top==0);
    }

    public void push( E elem ) {  
      //pre-condition: stack is not full
      elems[top]=elem;
      top++;
    }
    public E pop() {
      //pre-condition: isEmpty()==false;
      E saved=elems[--top];
      elems[top]=null;
      return saved;
    }

    public E peek() {      
      //pre-condition: isEmpty()==false;
      return elems[top-1];

    }
}
