import java.util.Arrays;

public class GreedyTester {
	public static void main(String[] args) {
		
		//This is the typical kind of input you will be tested with. The format will always be the same
		//Each index represents a single homework. For example, homework zero has weight 23 and deadline t=3.
		int[] weights = new int[] {20, 15, 10, 30, 40,20,35}; 
		int[] deadlines = new int[] {2, 1, 2,3,5,10,4};
		int m = weights.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
		
//		for(int j=0 ; j< schedule.Assignments.size(); j++) {
//			System.out.println(schedule.Assignments.get(j).deadline);
//		}
//		
		//This call organizes the assignments and outputs homeworkPlan
		int[] res = schedule.SelectAssignments();
		System.out.println(Arrays.toString(res));
	}
		
}
