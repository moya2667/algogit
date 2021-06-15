import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Student implements Comparable<Student> {
	int id;
	String name;
	double cgpa;

	public Student(int id, String name, double cgpa) {
		this.id = id;
		this.name = name;
		this.cgpa = cgpa;
	}

	public Double getCgpa() {
		return cgpa;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Student o) {
		if (o.cgpa > this.cgpa) {
			return 1;
		}

		if (o.cgpa == this.cgpa) {
			if (o.name.equalsIgnoreCase(this.name)) {
				if (o.id < this.id) {
					return 1;
				}
			}
			if (o.name.compareTo(this.name) < 0) {
				return 1;
			}
		}
		return -1;
	}
}

class Priorities {

	PriorityQueue<Student> pq = new PriorityQueue<>();

	public List<Student> getStudents(List<String> events) {
		for (String event : events) {
			// System.out.println(event);
			if (event.startsWith("SERVED")) {
				pq.poll();
			} else if (event.startsWith("ENTER")) {
				String[] s = event.split(" ");
				Student student = new Student(Integer.parseInt(s[3]), s[1], Double.parseDouble(s[2]));
				pq.add(student);
			}
		}
		List<Student> students = new ArrayList<Student>();
		while (!pq.isEmpty()) {
			students.add(pq.poll());
		}
		return students;
	}
}

public class Solution {
	private final static Scanner scan = new Scanner(System.in);
	private final static Priorities priorities = new Priorities();

	public static void main(String[] args) {
		int totalEvents = Integer.parseInt(scan.nextLine());
		List<String> events = new ArrayList<>();

		while (totalEvents-- != 0) {
			String event = scan.nextLine();
			events.add(event);
		}

		List<Student> students = priorities.getStudents(events);

		if (students.isEmpty()) {
			System.out.println("EMPTY");
		} else {
			for (Student st : students) {
				System.out.println(st.getName());
			}
		}
	}
}