package GestioneChatBot.Service;

import java.util.Objects;

public class Solution {

    String problem;
    String solution;

    public Solution() {
    }

    public Solution(String problem, String solution) {
        this.problem = problem;
        this.solution = solution;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution1 = (Solution) o;
        return solution.equals(solution1.solution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solution);
    }
}
