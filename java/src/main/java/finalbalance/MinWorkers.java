package finalbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MinWorkers {

    public static void main(String[] args) {
        List<List<Integer>> jobs = Arrays.asList(
                Arrays.asList(0, 30),
                Arrays.asList(10, 20),
                Arrays.asList(15, 40)
        );

        System.out.println(minWorkers(jobs));
    }

    public static int minWorkers(List<List<Integer>> jobs) {
        // Write your code here
        var sortedJobs = new ArrayList<>(jobs);
        sortedJobs.sort(Comparator.comparingInt(List::getFirst));

        PriorityQueue<Integer> worker = new PriorityQueue<>();
        for (List<Integer> jobList: sortedJobs) {
            int start = jobList.get(0);
            int end = jobList.get(1);
            if(!worker.isEmpty() && worker.peek() <= start) {
                worker.poll();
            }
            worker.offer(end);
            System.out.println("deque " + worker);
        }

        return worker.size();
    }
}
