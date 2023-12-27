package ru.course2.spring1;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component (value="TEXT") public class DataOutput implements Consumer<Model> {
    @Override public void accept(Model model) {
        DateFormat formatter=new SimpleDateFormat("dd.MM.yyyy");
        System.out.println("TEXT: "+Thread.currentThread().getStackTrace()[1].getClassName()+"."
                +Thread.currentThread().getStackTrace()[1].getMethodName()
                +": ");
        System.out.println(String.format("%1$"+140+"s"," ").replace(' ','-'));
        System.out.printf("%-10s %-20s %-25s %-25s %-25s %-15s %-25s %n","Проверка","Логин","Фамилия","Имя","Отчество"
                ,"Дата входа","Тип приложения");
        System.out.println(String.format("%1$"+140+"s"," ").replace(' ','-'));
        for (ModelStructure md:model.fLine) {
            if(md.getAccess_date()!=null){
                System.out.printf("%-10s %-20s %-25s %-25s %-25s %-15s %-25s %n",md.isDataOk(),md.getUsername(),md.getFioF(),md.getFioI(),md.getFioO()
                        ,formatter.format(md.getAccess_date()),md.getAppl_type());
            } else {
                System.out.printf("%-10s %-20s %-25s %-25s %-25s %n",md.isDataOk(),md.getUsername(),md.getFioF(),md.getFioI(),md.getFioO());
            }
        }
        System.out.println(String.format("%1$"+140+"s"," ").replace(' ','-'));
    }
}
@Component (value="H2") class DataOutputH2 implements Consumer<Model> {
    @Override
    public void accept(Model model) {
// создать объект с подключением к H2
        H2DAO dao=null;
        try {
            dao = new H2DAO("jdbc:h2:~/test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
// запись в базу H2
        try {
            dao.postData(model.fLine,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
// проверим выборку из таблицы users
        try {
            dao.selectUsers();
            dao.selectLogins();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
@Component (value="postgre") class DataOutputPostgre implements Consumer<Model> {
    @Override
    public void accept(Model model) {
// создать объект с подключением к Postgre
        PostgreDAO dao=null;
        List<String> lstURL=new ArrayList<>();
        lstURL.add("jdbc:postgresql://localhost:5432/postgres");
        lstURL.add("postgres");
        lstURL.add("12345");
        try {
            dao = new PostgreDAO(lstURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
// запись в базу H2
        try {
            dao.postData(model.fLine,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
// проверим выборку из таблицы users
        try {
            dao.selectUsers();
            dao.selectLogins();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
