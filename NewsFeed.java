/* *
 * Use static array for NewsFeed
 * with constant MAX_SIZE
 * */

public class NewsFeed {

  private Post[] messages;
  private int size;
  public static final int MAX_SIZE = 25;

  public NewsFeed() {
	//initializing conditions of the NewsFeed array  
    size = 0;
	messages = new Post [MAX_SIZE];

  }

  public void add(Post message) {
	  
	if (size < MAX_SIZE){
	     messages[size] = message;
		 size++;
	}
	     


    

  }

  public Post get(int index) {
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

    NewsFeed photOnly = new NewsFeed();
    int i;
	for (i = 0; i < size; i++){
		if (messages[i] instanceof PhotoPost){
		    photOnly.add(messages[i]);
		}
	}
    return photOnly;
  }

  public NewsFeed plus(NewsFeed other){
	  
	int i,j;
    NewsFeed combine = new NewsFeed();
	
	for (i=0; i< size; i++){
	    combine.add(this.messages[i]);
	}
	for (j =0; j<other.size; j++){
	    combine.add(other.messages [j]);
	}
    combine.sort();	
	return combine;

  }

}
