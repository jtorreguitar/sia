import java.util.Deque;

public class SearchAlgorithmFactory {

    public SearchAlgorithm newBFS(Problem problem) {
        return new CollectionBasedSearch(problem, Deque::getFirst, Deque::offer);
    }

    public SearchAlgorithm newDFS(Problem problem) {
        return new CollectionBasedSearch(problem, Deque::pop, Deque::push);
    }
}
