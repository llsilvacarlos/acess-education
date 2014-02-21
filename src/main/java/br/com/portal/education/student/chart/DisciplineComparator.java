package br.com.portal.education.student.chart;

import java.util.Comparator;

import br.com.portal.education.entity.Discipline;

public class DisciplineComparator implements Comparator<Discipline>{

    @Override
    public int compare(Discipline o1, Discipline o2) {
	return o1.getDescription().compareTo(o2.getDescription());
    }

}
