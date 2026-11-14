package mergewindows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeWindows {

    public static void main(String[] args) {
        List<List<Integer>> windows = Arrays.asList(
                Arrays.asList(8, 9),
                Arrays.asList(1, 4),
                Arrays.asList(3, 6));

        List<List<Integer>> result = mergeWindows(windows);

        System.out.println(result);
    }

    public static List<List<Integer>> mergeWindows(List<List<Integer>> windows) {
        // Write your code here
        windows.sort(Comparator.comparingInt(List::getFirst));
        List<List<Integer>> resultList = new ArrayList<>();
        for(List<Integer> window: windows) {
            if(resultList.isEmpty() || resultList.get(resultList.size() - 1).get(1) < window.get(0)) {
                resultList.add(new ArrayList<>(window));
            } else {
                List<Integer> last = resultList.get(resultList.size() - 1);
                System.out.println(last);

                last.set(1, Math.max(last.get(1), window.get(1)));
            }
        }

        return resultList;
    }

//    public static List<List<Integer>> mergeWindows(List<List<Integer>> windows) {
//        // Write your code here
//        List<List<Integer>> sorted = new ArrayList<>(windows);
//        sorted.sort(Comparator.comparingInt(List::getFirst));
//        List<List<Integer>> resultList = new ArrayList<>();
//        for (List<Integer> current : sorted) {
//            if (resultList.isEmpty() || resultList.getLast().get(1) < current.getFirst()) {
//                resultList.add(new ArrayList<>(current));
//            } else {
//                int maxEnd = Math.max(resultList.getLast().get(1), current.get(1));
//                resultList.getLast().set(1, maxEnd);
//            }
//        }
//        return resultList;
//    }
}
