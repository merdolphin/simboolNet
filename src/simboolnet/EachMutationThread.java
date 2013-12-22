package simboolnet;

public class EachMutationThread extends Thread{
	
	String mNode;
	
	public EachMutationThread(String mNode){
		this.mNode = mNode;
	};

	public void run(){
		try {
			System.out.println(mNode);
			SimBoolNetMain.forEachMutatedRunAndWrite(mNode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
