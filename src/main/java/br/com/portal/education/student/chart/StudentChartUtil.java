package br.com.portal.education.student.chart;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.portal.education.entity.Discipline;
import br.com.portal.education.entity.Question;

@Named
@RequestScoped
public class StudentChartUtil {

    private Map<Discipline, Long> mapGroupExamDiscipline = null;

    private Map<Discipline, Long> chartDate = null;

    public void loadMapDiscipline(List<Question> listQuestion, Map<Discipline, Long> mapGroupExamDisciplineStrudent) {

	mapGroupExamDiscipline = new TreeMap<Discipline, Long>(new DisciplineComparator());

	for (Question question : listQuestion) {
	    if (mapGroupExamDiscipline.get(question.getDiscipline()) == null) {
		mapGroupExamDiscipline.put(question.getDiscipline(), 1l);
	    } else {
		Long noteDiscipline = mapGroupExamDiscipline.get(question.getDiscipline());
		noteDiscipline +=1;
		mapGroupExamDiscipline.put(question.getDiscipline(), noteDiscipline);
	    }
	}

	calculeNote(mapGroupExamDisciplineStrudent);
    }

    private void calculeNote(Map<Discipline, Long> mapGroupExamDisciplineStrudent) {
	chartDate = new TreeMap<Discipline, Long>(new DisciplineComparator());

	for (Discipline discipline : mapGroupExamDiscipline.keySet()) {
	    Long noteTotal = mapGroupExamDiscipline.get(discipline);
	    Long noteStudent = mapGroupExamDisciplineStrudent.get(discipline);
	    if(noteStudent == null) noteStudent = 0l;
	    Long getNote = noteStudent * 100 / noteTotal;
	    chartDate.put(discipline, getNote);

	}
    }

    public Map<Discipline, Long> getChartDate() {
	return chartDate;
    }
}
