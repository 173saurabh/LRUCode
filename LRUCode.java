package lruDesign;

import java.util.HashMap;
import java.util.Scanner;
//LRU->( Least Recently Used ) DESIGN USING DOUBLY LISNKED LIST USING HASHSET
class Node{
	int data;
	Node next,prev;
	Node(int x){
		data = x;
		next = null;
		prev = null;
	}
}

public class LRU {
	static Node head;
	static HashMap<Integer,Node> hmap = new HashMap<>();
	static int cacheSize;
	static int currentSize = 0;
	/*Inseting the elements at the beginning of the doubly linked list
	 * Step 1:- 1st check whether the element is present or not the HashSet
	 * Step 2 (NOT PRESENT CASE) :- If not present add in hashset.
	 * Step 2.5 (PRESENT CASE) :- If present take the prev & next of that node & delete that
	 * 			node from the dll.
	 * Step 3:- Add at the beginning of dll.
	 */
	//Insert At Beginning
	public static void insertAtBeginning(Node node)
	{
		//Base case for cache size if it is 0
		if(cacheSize == 0)
		{
			head = null;
			return;
		}
		//Base case
		if(head == null || (currentSize == cacheSize && currentSize == 1)) {
			head = node;
			hmap.put(node.data,node);
			currentSize++;
			return;
		}
		if(hmap.containsKey(node.data))
		{
			//For present case:-
			
			//Base Case:-
			if(node.data == head.data)
				return;
			
			//Removing the present node
			Node dummy = hmap.get(node.data);
			dummy.prev.next = dummy.next;
			if(dummy.next != null)
				dummy.next.prev = dummy.prev;
			
			//Updating the node previous node & next node new references to hashmap
			hmap.put(dummy.prev.data, dummy.prev);
			if(dummy.next != null)
				hmap.put(dummy.next.data, dummy.next);
		
			//Making the new node as head
			node.next = head;
			head.prev = node;
			hmap.put(head.data, head);
			hmap.put(node.data,node);
			head = head.prev;
		}
		else {
			//For not present case:-
			
			//If currentSize == cacheSize delete the last node:-
			if(currentSize == cacheSize)
			{
				deleteLastNode();
				currentSize--;
			}
				
			node.next = head;
			head.prev = node;
			hmap.put(head.data, head);
			hmap.put(node.data,node);
			head = head.prev;
			currentSize++;
		}
	}
	
	//Delete last node:-
	public static void deleteLastNode()
	{
		Node temp = head;
		while(temp.next.next!=null)
		{
			temp = temp.next;
		}
		int data = temp.next.data;
		temp.next = null;
		hmap.remove(data);
	}
	
	//Print list
	public static void printList()
        {
            Node temp = head;
            while(temp!=null)
            {
                System.out.println(temp.data+" ");
                temp = temp.next;
            }
        }
	
	//Main method Driver Code
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the cache size: ");
        cacheSize = sc.nextInt();
        
        System.out.println("Enter the no* of elements you want to insert: ");
        int n = sc.nextInt();
        
        System.out.println("Enter the elements");
        while(n-->0)
        {
            Node node = new Node(sc.nextInt());
            insertAtBeginning(node);
        }
        
        System.out.println("The final list is: ");
        printList();
    }
}
