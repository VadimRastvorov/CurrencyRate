package ru.liga;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Rate {
    //количество дней для Среднего арифметического значения
    public Integer lastDays = 7;

    //public метод для вызова нужного метода в зависимости от валюты
    public List<Curs> getRate(String currency, String period) throws ParseException, IOException {
        if(period.equals("tomorrow")){
            return getRateTomorrow(currency);
        }else if(period.equals("week")){
            return getRateWeek(currency);
        }
        return null;
    }
    //Курс валюты на завтра
    private List<Curs> getRateTomorrow(String currency) throws ParseException, IOException {
        List<Curs> rateTomorrow = new ArrayList<>();
        ParseCSV parse = new ParseCSV();
        rateTomorrow.add(new Curs(0,addDays(new Date(),1),getCurs(parse.getCurs(currency).subList(0, lastDays)),currency));
        return rateTomorrow;
    }
    //Курс валюты на 7 дней
    private List<Curs> getRateWeek(String currency) throws ParseException, IOException {
        List<Curs> rateTomorrow = new ArrayList<>();
        ParseCSV parse = new ParseCSV();
        Date date = new Date();
        List<Curs> curs = parse.getCurs(currency).subList(0, lastDays);
        for (int i = 0; i<7;i++) {
            date = addDays(date, 1);
            Curs cur = new Curs(0, date, getCurs(curs), currency);
            rateTomorrow.add(cur);
            curs.add(0,cur);
            curs.remove(curs.size() - 1);
        }
        return rateTomorrow;
    }
    //вернем Среднее арифметическое значение
    private Double getCurs(List<Curs> curs) {
        double cursMath = 0;
        for (Curs cur : curs) {
            cursMath += cur.curs;
            //System.out.println(String.format("%s\t%s\t%s", new SimpleDateFormat("E dd.MM.yyyy").format(cur.date), String.format("%.4f",cur.curs),cur.cdx));
        }
        return cursMath / curs.size();
    }
    //прибавим к дате дни
    private Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
