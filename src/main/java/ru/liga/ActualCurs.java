package ru.liga;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActualCurs {
    private final List<Curs> cursList;

    public ActualCurs(List<Curs> cursList) {
        this.cursList = actualCursList(cursList);
    }

    public List<Curs> actualCursList() {
        return cursList;
    }

    private List<Curs> actualCursList(List<Curs> cursList) {
        List<Curs> actualCursList = cursList.stream()
                .sorted(Comparator.comparing(Curs::getLocalDate))
                .collect(Collectors.toList());
        for (int i = 1; i < actualCursList.size(); i++) {
            LocalDate day = actualCursList.get(i - 1).getLocalDate().plusDays(1);
            Curs yesterday = actualCursList.get(i - 1).withLocalDate(day);
            if (!yesterday.getLocalDate().isEqual(actualCursList.get(i).getLocalDate())) {
                actualCursList.add(i, yesterday);
            }
        }
        return actualCursList;
    }
}
