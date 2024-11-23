

//this class for helping us with AVL Classes 

@SuppressWarnings("hiding")
public class AVLTree<K extends Comparable<K>, T>{

   
    class AVLNode<K extends Comparable<K>, T> {
            public K key;
            public T data;
            AVLNode<K, T> parent, left, right;
            int bf;
                

            // O(1)
                public AVLNode() {
                    this.key = null;  
                    this.data = null;
                    this.parent = this.left = this.right = null;
                    this.bf = 0;
            }
//O(1)
            public AVLNode(K key, T data) {
                    this.key = key  ;  
                    this.data = data;
                    this.parent = this.left = this.right = null;
                    this.bf = 0;
            }
//O(1)
                public AVLNode(K key, T data, AVLNode<K,T> p, AVLNode<K,T> l, AVLNode<K,T> r){
                        this.key = key  ;  
                        this.data = data;
                        left = l;
                        right = r;
                        parent = p;
                        bf =0;
                }

                public AVLNode<K,T> getLeft()
                {
                    return left;
                }
                   //O(1)
                public AVLNode<K,T> getRight()
                {
                    return right;
                }
 //O(1)
                public T getData()
                {
                    return data;
                }
                
                @Override
                //O(1)
                public String toString() {
                    return "AVL Node{" + "key=" + key + ", data =" + data + '}';
                }
        }


private AVLNode<K, T> root, curr;
private int count;
 //O(1)
    public AVLTree() {
            root = curr = null;
            count = 0;
    }
 //O(1)
    public boolean empty() {
        return root == null;
    }
 //O(1)
    public int size() {
        return count;
    }


     //O(1) 
    public void clear()
    {
        root = curr = null;
        count = 0;
    }

     //O(1)
    public T retrieve() {
            return (curr != null) ? curr.data : null;
        }
 //O(1)
  
    public void update(T e)
    {
        if (curr != null)
            curr.data = e;
    }

 //O(logn)
    private T searchTreeHelper(AVLNode<K, T> node, K key) {
            if (node == null) return null;
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                curr = node;
                return node.data;
            } else if (cmp < 0) {
                return searchTreeHelper(node.left, key);
            } else {
                return searchTreeHelper(node.right, key);
            }
        }
    
   // O(logn)
    private void updateBalance(AVLNode<K,T> node) {
            if (node.bf < -1 || node.bf > 1) {
                    rebalance(node);
                    return;
            }

            if (node.parent != null) {
                    if (node == node.parent.left) {
                            node.parent.bf -= 1;
                    } 

                    if (node == node.parent.right) {
                            node.parent.bf += 1;
                    }

                    if (node.parent.bf != 0) {
                            updateBalance(node.parent);
                    }
            }
    }
//O(1)
    void rebalance(AVLNode<K,T> node) {
            if (node.bf > 0) {
                    if (node.right.bf < 0) {
                            rightRotate(node.right);
                            leftRotate(node);
                    } else {
                            leftRotate(node);
                    }
            } else if (node.bf < 0) {
                    if (node.left.bf > 0) {
                            leftRotate(node.left);
                            rightRotate(node);
                    } else {
                            rightRotate(node);
                    }
            }
    }
   // O(logn)
    public boolean find(K key) {
            T data = searchTreeHelper(this.root, key);
            if ( data != null)
                return true;
            return false;
    }

 //O(1)
    void leftRotate(AVLNode<K,T> x) {
        AVLNode<K,T> y = x.right;
        x.right = y.left;
        if (y.left != null) {
                y.left.parent = x;
        }
        
        y.parent = x.parent;
        if (x.parent == null) {
                this.root = y;
        } else if (x == x.parent.left) {
                x.parent.left = y;
        } else {
                x.parent.right = y;
        }
        y.left = x;
        x.parent = y;

 
        x.bf = x.bf - 1 - Math.max(0, y.bf);
        y.bf = y.bf - 1 + Math.min(0, x.bf);
    }

 //O(1)
    void rightRotate(AVLNode<K,T> x) {
            AVLNode<K,T> y = x.left;
            x.left = y.right;
            if (y.right != null) {
                    y.right.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                    this.root = y;
            } else if (x == x.parent.right) {
                    x.parent.right = y;
            } else {
                    x.parent.left = y;
            }
            y.right = x;
            x.parent = y;

        
            x.bf = x.bf + 1 - Math.min(0, y.bf);
            y.bf = y.bf + 1 + Math.max(0, x.bf);
    }

    
   // O(logn) 
    public boolean insert(K key, T data) {
        AVLNode<K,T> node = new AVLNode<K,T>(key, data);

        AVLNode<K,T> p = null;
        AVLNode<K,T> current = this.root;

        while (current != null) {
                p = current;
                if (node.key.compareTo(current.key) ==0) {
                        return false;
                }else if (node.key.compareTo(current.key) <0 ) {
                        current = current.left;
                } else {
                        current = current.right;
                }
        }
      
        node.parent = p;
        if (p == null) {
                root = node;
                curr = node;
        } else if (node.key.compareTo(p.key) < 0 ) {
                p.left = node;
        } else {
                p.right = node;
        }
        count ++;

 
        updateBalance(node);
        return true;        
    }
 //O(logn) 
public boolean remove(K key) {
    K k1 = key;
    AVLNode<K,T>  p = root;
    AVLNode<K,T>  q = null; 

    while (p != null) 
    {
            if (k1.compareTo(p.key) <0)
            {
                q =p;
                p = p.left;
            } 
            else if (k1.compareTo(p.key) >0)
            {    
                q = p;
                p = p.right;
            }
            else 
            { 
               
                if ((p.left != null) && (p.right != null)) 
                { 
                    
                    AVLNode<K,T> min = p.right;
                    q = p;
                    while (min.left != null) 
                    {
                        q = min;
                        min = min.left;
                    }
                    p.key = min.key;
                    p.data = min.data;
                    k1 = min.key;
                    p = min;
                   
                }
               
                if (p.left != null) 
                { 
                
                    p = p.left;
                } 
                else 
                { 
                 
                    p = p.right;
                }
                if (q == null)
                { 
               
                    root = p;
                    this.updateBalance(p);
                } 
                else 
                {
                    if (k1.compareTo(q.key) <0)
                        q.left = p;
                    else 
                        q.right = p;
                    this.updateBalance(q);
                }
                count--;
                curr = root;
                return true;    
        } 
    } 
    return false;
}

// O(n)
   public void Traverse()
    {
        if (root != null)
            traverseTree(root);
    }
    // O(n)
    private void traverseTree (AVLNode<K,T> node  )
    {
        if (node == null)
            return;
        traverseTree( node.left);
        System.out.println(node.data);
        traverseTree( node.right);
        
    }

    
   // O(n)
   public void TraverseT()
    {
        if (root != null)
            traverseTreeT(root);
    }
    
    @SuppressWarnings("unchecked")

    
// O(n)
    private void traverseTreeT (AVLNode<K,T> node)
    {
        if (node == null)
            return;
        traverseTreeT( node.left );
        if (node.getData() instanceof AVLTree )
        {
            System.out.println( "Node key ==== "+ node.key);
            ((AVLTree <String,Integer>) node.getData()).Traverse();
        }
        else
            System.out.println(node.data);
        
        traverseTreeT( node.right);
        
    }
    // O(n)
    public void displayData() {
        if (root != null)
            displayDataRecursive(root);
    }
    // O(n)
    private void displayDataRecursive(AVLNode<K,T> node) {
    
       
                
    {
        if (node == null)
            return;
            displayDataRecursive( node.left );
       
       System.out.print(node.key);
        if (node.getData() instanceof TermTF )
        {
            System.out.print("   docs: ");
            boolean [] docs = ((TermTF) node.data).getDocs();
            for ( int i  = 0 ; i < 50 ; i++)
                if ( docs[i])
                    System.out.print( " " + i + " " );
            System.out.println("");
        }
            
        
        displayDataRecursive( node.right);
        
    
}}}

