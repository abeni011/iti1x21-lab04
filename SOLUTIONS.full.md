## Question 1:
### Likeable:

Implement the interface Likeable. It declares two methods: **like()** and **int getLikes()**.

### Solutions

```java
 public interface Likeable {
    public abstract void like();
    public abstract int getLikes();
}
```

## Question 2:
### Post:
Write the implementation of the class Post. It implements the characteristics that are common to its sub-classes, here **TextPost** and **PhotoPost**.

*   **Post** implements the **interface Likeable**.
*   All the **Post** messages have a user name, a time stamp (of type **java.util.Date**), as well as a **count** for the number of **likes**.
*   The value of the time stamp is automatically assigned when an object is created. Use **java.util.Calendar.getInstance().getTime()** to obtain a **Date** object representing the current time. A **Date** object has a method **toString()** that converts this date to a **String**

```java
        Date rightNow = Calendar.getInstance().getTime();
        System.out.println(rightNow);
```
*   Each call to the method **like()** increases the number of likes for this message.
*   **Post** implements the **interface Comparable**. This interface allows you to compare two **Post** according to specific criteria. In this case the criteria will be the **date** of the post. For more information, refer to the [documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html).
*   Add the method isPopular. This method returns true if the post is considered popular (more than 100 likes), false otherwise.
*   Do not forget the method **toString()**!

### Solutions

```java
import java.util.Calendar;
import java.util.Date;

public class Post implements Likeable, Comparable<Post> {

    protected int likes;
    private Date timeStamp;
    private String userName;

    public Post(String userName) {
	this.userName = userName;
	timeStamp = Calendar.getInstance().getTime();
	likes = 0;
    }

    public String getUserName() {
	return userName;
    }

    public Date getTimeStamp() {
	return timeStamp;
    }

    public void like() {
	likes++;
    }

    public int getLikes() {
	return likes;
    }

    public String toString() {
	String str = new String();
	str = getClass().getName() + ": " + timeStamp + ", " + userName + ", likes = " + likes;
	return  str;
    }


	public int compareTo(Post other){
		return timeStamp.compareTo(other.timeStamp);
	}

	public boolean isPopular(){
		if (likes > 100)
			return true;

		return false;
	}

}
```
## Question 3:
### PhotoPost

Implement the class **PhotoPost**. A **PhotoPost** is a specialized **Post**. It stores a **file name** and a **caption**. Override the method **toString()** by using the keyword **"super"** in your implementation.

### Solutions

```java
public class PhotoPost extends Post {

    private String fileName;
    private String caption;

    public PhotoPost(String userName, String fileName, String caption) {
	super(userName);
	this.fileName = fileName;
	this.caption = caption;
    }

    public String getCaption() {
	return caption;
    }

    public String getFileName() {
	return fileName;
    }

    public String toString() {
	String str = new String();
	str = super.toString() + ", " + fileName + ", " + caption;
	return str;
    }

}
```
## Question 4:
### TextPost

Implement the class **TextPost**. A **TextPost** is a specialized **Post**. It stores a **text message** (String). Override the method **toString** using the keyword **“super”** in your implementation. A **TextPost** is considered popular if the post gets more than **50 likes**.
### Solutions

```java
public class TextPost extends Post {

    private String message;

    public TextPost(String userName, String message) {
	super(userName);
	this.message = message;
    }

    public String getMessage() {
	return message;
    }

    public String toString() {
	String str = new String();
	str = super.toString() + ", " + message;
	return  str;
    }

	public boolean isPopular() {
		if(likes > 50){
			return true;
		}

		return false;
	}

}

```

## Question 5:
### NewsFeed

Write the implementation of the class **NewsFeed**. A **NewsFeed** object stores **Post** messages

*   It uses a fixed size array of some constant size **MAX_SIZE** to **store Post messages** . For this implementation will only accept up to **25** Post messages.
*   It has a method for **adding a Post message**. The message is added after the last message added.
*   It has a method **sort** in which the Post are sorted from the oldest to the most recent.
*   It has a method for returning the message found at a given index, **Post get(int index)**.
*   It has a method **size** that returns the number of messages currently stored.
*   It has a method **getPhotoPost** that returns a new object of type **NewsFeed** containing only the **PhotoPost**
*   It has an instance method **plus** that has one formal parameter of type **NewsFeed**. This method returns a new object of type **NewsFeed** that represents the **combination of the two NewsFeed**. The **Post** of the **new NewsFeed** have to be **sorted** from the oldest to the most recent one.
### Solutions

```java
/* *
 * Use static array for NewsFeed
 * with constant MAX_SIZE
 * */

public class NewsFeed {

    private Post[] messages;
    private int size;
    public static final int MAX_SIZE = 25;

    public NewsFeed() {
	// preconditions: capacity > 0, capactityIncrement > 0
	messages = new Post[MAX_SIZE];
	size = 0;
    }

    public void add(Post message) {
        if (size == MAX_SIZE) {
            System.out.println("Max size reached");
            return;
        }
        messages[size++] = message;
    }

    public Post get(int index) {
	// precondition: 0 <= index < size
	return messages[index];
    }

    public int size() {
	return size;
    }

	public void sort(){
			int i, j, argMin;
			Post tmp;
			for (i = 0; i < size - 1; i++) {
				argMin = i;
				for (j = i + 1; j < size(); j++) {
					if (messages[j].compareTo(messages[argMin]) < 0) {
						argMin = j;
					}
				}

			tmp = messages[argMin];
			messages[argMin] = messages[i];
			messages[i] = tmp;
		}

	}

	public NewsFeed getPhotoPost(){
		NewsFeed photoFeed = new NewsFeed();

		for(int i=0; i < size; i++){
			if (messages[i] instanceof PhotoPost){
				/*
					Solutions that use the Object's method getClass()
					are acceptable in this case, since there are no subtypes
					of PhotoPost.
					The difference between instanceof and getClass() is that
					instanceof tests whether the object reference
					on the left-hand side is an instance of the type
					on the right-hand side OR some subtype, while
					getClass() tests whether the types are identical.
				*/
				photoFeed.add(messages[i]);
			}
		}
		return photoFeed;
	}

	public NewsFeed plus(NewsFeed other){

		NewsFeed newFeed = new NewsFeed();

		//Adds all of posts in first NewsFeed to new NewsFeed
		for (int i = 0; i < size(); i++){
			newFeed.add(messages[i]);
		}

		//Adds all of posts in second NewsFeed to new NewsFeed
		for (int j = 0; j < other.size(); j++){
			newFeed.add(other.messages[j]);
		}

		newFeed.sort();
		return newFeed;
	}

}

```

## Question 6:
### Bonus
*   Write the implementation of the class **NewsFeed** using a **dynamic** array (array that automatically changes size) to store Post messages, instead of a fixed size array.
*   The constructor has two parameters, the **initial capacity of the array** and **the capacity increment**
*   Each time the array is full, the implementation should **create a new array** larger by the capacity increment.

Implement all the necessary **constructors**. Each attribute must have a **getter** method.

Here is a main program illustrating the use of these classes. Adapt it to make sure that all methods are implemented correctly. **You must not submit this file: NewsFeedMainApplication.java.**

*   [NewsFeedMainApplication.java](https://www.site.uottawa.ca/~gvj/Courses/ITI1121/lectures/labs/lab4_template/NewsFeedMainApplication.java)

### Solutions

```java
/* *
 * Use dynamic array for NewsFeed
 * by using increaseSize() method
 * */

public class NewsFeed {

    private Post[] messages;
    private int capacityIncrement;
    private int size;

    public NewsFeed(int capacity, int capacityIncrement) {
	// preconditions: capacity > 0, capactityIncrement > 0
	messages = new Post[capacity];
	this.capacityIncrement = capacityIncrement;
	size = 0;
    }

    public void add(Post message) {
	if (size == messages.length) {
	    increaseSize();
	}
	messages[size++] = message;
    }

    private void increaseSize() {
	Post[] current = messages;
	messages = new Post[current.length + capacityIncrement];
	System.arraycopy(current, 0, messages, 0, current.length);

    }

    public Post get(int index) {
	// precondition: 0 <= index < size
	return messages[index];
    }

    public int size() {
	return size;
    }

	public void sort(){
			int i, j, argMin;
			Post tmp;
			for (i = 0; i < size - 1; i++) {
				argMin = i;
				for (j = i + 1; j < size(); j++) {
					if (messages[j].compareTo(messages[argMin]) < 0) {
						argMin = j;
					}
				}

			tmp = messages[argMin];
			messages[argMin] = messages[i];
			messages[i] = tmp;
		}

	}

	public NewsFeed getPhotoPost(){
		NewsFeed photoFeed = new NewsFeed(size, size);

		for(int i=0; i < size; i++){
			if (messages[i] instanceof PhotoPost){
			photoFeed.add(messages[i]);
			}
		}
		return photoFeed;
	}

	public NewsFeed plus(NewsFeed other){

		NewsFeed newFeed = new NewsFeed(size + other.size, size + other.size);

		//Adds all of posts in first NewsFeed to new NewsFeed
		for (int i = 0; i < size(); i++){
			newFeed.add(messages[i]);
		}

		//Adds all of posts in second NewsFeed to new NewsFeed
		for (int j = 0; j < other.size(); j++){
			newFeed.add(other.messages[j]);
		}

		newFeed.sort();
		return newFeed;
	}

}

```
