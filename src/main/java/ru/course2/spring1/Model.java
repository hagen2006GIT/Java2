package ru.course2.spring1;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Model {
    List<ModelStructure> fLine;
    public Model() {
        this.fLine=new ArrayList<>();
    }
    @Override public String toString() {
        StringBuilder str=new StringBuilder();
        for(ModelStructure mod:this.fLine) {
            str.append(mod.toString()).append("\n");
        }
        return str.toString();
    }
}
class ModelStructure {
    @Getter @Setter private boolean dataOk; // если true, то вносим в БД, иначе только логирование
    @Getter @Setter private String username; // логин
    @Getter @Setter private String fioF; // фамилия
    @Getter @Setter private String fioI; // имя
    @Getter @Setter private String fioO; // отчетство
    @Getter @Setter private Date access_date; //дата входа
    @Getter @Setter private String appl_type; //тип приложения

    public String gerFullString() {
        return username
                +" "+fioF
                +" "+fioI
                +" "+fioO
                +" "+access_date
                +" "+appl_type;
    }

    public ModelStructure(String username
            , String fioF
            , String fioI
            , String fioO
            , Date access_date
            , String appl_type
    ) {
        this.username=username;
        this.fioF=fioF;
        this.fioI=fioI;
        this.fioO=fioO;
        this.access_date=access_date;
        this.appl_type=appl_type;
        this.dataOk=true; // по умолчанию (до всех проверок) признак true. Если какая-то из проверок не проходит,
                        // то она ставит false, чтобы эта запись не добавилась в БД (только логирование в отдельный файл)
    }
    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", FIO='" + fioF + " " + fioI + " " + fioO+ '\'' +
                ", access_date='" + access_date + '\'' +
                ", appl_type='" + appl_type + '\'' +
                '}';
    }
}